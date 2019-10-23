# -*- coding: utf-8 -*-
import scrapy
import time
import random
# third-party packages
from selenium import webdriver
from selenium.common.exceptions import (ElementClickInterceptedException,
                                        StaleElementReferenceException,
                                        TimeoutException)
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

# mypackages
from .tool import Tools,MetaInfo

# 搜索页面上的小图有权限问题,(在浏览器输入src,大概率403)

class MindatSpider(scrapy.Spider):
    name = 'mindat'
    allowed_domains = ['mindat.org']
    start_urls = ['http://mindat.org/']
    # custom_settings = {'LOG_LEVEL': 'INFO'}
    def parse(self, response):
        # preparation
        Tools.removeDir(MetaInfo.save_dir)
        Tools.create_save_dir(MetaInfo.save_dir)

        keyword_list=['正长石']
        # 创建webdriver
        option = webdriver.ChromeOptions()
        option.add_argument('--headless')
        driver = webdriver.Chrome(options=option)
        # driver=webdriver.
        for kw in keyword_list:
            url=Tools.get_search_url(kw)
            driver.get(url)
            num=MetaInfo.initial_img_num # 一开始搜索页的图片数量
            lastlen=0
            while(1): #todo 需要结束条件
                js = "document.documentElement.scrollTop=100000"
                num=num+MetaInfo.increase_num # 执行下拉后,页面上预期的图片数
                driver.execute_script(js)
                # 结束条件方法1
                # try: # 等待预期的最后一个图片元素加载完成,最大20s
                #     element = WebDriverWait(driver, 20).until(EC.presence_of_element_located((By.CSS_SELECTOR,
                #                                                                       '#photoscroll:nth-child({n})'.format(n=num)))) 
                # except TimeoutException as e:# 超时说明已经下拉到底
                #     # self.logger(e)
                #     self.logger.info('scroll at bottom. keyword: {kw}'.format(kw=kw))
                #     break

                # 结束条件方法2
                time.sleep(5)
                selector=driver.find_elements_by_css_selector('#photoscroll >a')
                length=len(selector)
                if(length%100==0):
                    self.logger.info('len:'+str(length))
                if(not length>lastlen):# 没加载新图片
                    break

                lastlen=length
                


            # srcs=driver.find_elements_by_css_selector()
            # 图片元素中src属性(搜索页面上展现的小图元素的src)
            # selectors = driver.find_elements_by_css_selector(".psimage >img") 
            # srcs=['mindat.org/'+x.get_attribute('src') for x in selectors]
            
            selectors = driver.find_elements_by_css_selector(".psimage") 
            srcs=[x.get_attribute('href') for x in selectors]
            # self.logger.info('srcs')
            # self.logger.info(srcs)
            self.logger.info('total img links: {n}'.format(n=len(srcs)))
            for s in srcs:
                yield scrapy.Request(url=s, callback=self.parse_img_html)
                
    # 搜索页面上点击图片后返回的html
    def parse_img_html(self,response):
        src=response.css('#mainphoto::attr(src)').get() # 返回一个php的相对路径
        self.logger.info("jpg src")
        self.logger.info(src)
        yield scrapy.Request(url='https://www.mindat.org/'+src,callback=self.parse_img)

    # 处理返回的图片文件
    def parse_img(self,response): # todo 可能需要从链接中获取图片格式
        filename=str(time.time())+str(random.randint(1,100))+'.jpg'
        full_path=MetaInfo.save_dir+filename
        with open(full_path,'wb') as f:
            f.write(response.body)
        
        

