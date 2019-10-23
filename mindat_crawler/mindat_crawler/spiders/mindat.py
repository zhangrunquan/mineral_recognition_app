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
        logf=open(MetaInfo.log_file_path,'w',encoding='utf-8')
        keyword_list=Tools.read_list(MetaInfo.label_file_path)
        # keyword_list=['蛭石']
        self.logger.info('keywords')
        self.logger.info(keyword_list)
        for kw in keyword_list: # 创建各标签的文件夹
            Tools.create_save_dir(MetaInfo.save_dir+kw)

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
                time.sleep(5)# 等待加载
                selector=driver.find_elements_by_css_selector('#photoscroll >a')
                newlen=len(selector)
                if(newlen%100==0):
                    self.logger.info('len:'+str(newlen))
                if(not newlen>lastlen):# 没加载新图片
                    break
                lastlen=newlen
                
            selectors = driver.find_elements_by_css_selector(".psimage") 
            srcs=[x.get_attribute('href') for x in selectors]
            self.logger.info('total img links: {n}'.format(n=len(srcs)))
            logf.write(kw+','+str(lastlen)+'\n') # 记录各种标签图片数
            logf.flush()
            meta={'keyword':kw}
            for s in srcs:
                yield scrapy.Request(url=s, callback=self.parse_img_html,meta=meta)

        #清理
        logf.close()
                
    # 搜索页面上点击图片后返回的html
    def parse_img_html(self,response):
        src=response.css('#mainphoto::attr(src)').get() # 返回一个php的相对路径
        self.logger.info("jpg src")
        self.logger.info(src)
        yield scrapy.Request(url='https://www.mindat.org/'+src,callback=self.parse_img,meta=response.meta)

    # 处理返回的图片文件
    def parse_img(self,response): # todo 可能需要从链接中获取图片格式
        filename=str(time.time())+str(random.randint(1,100))+'.jpg'
        full_path=MetaInfo.save_dir+response.meta['keyword']+'/'+filename
        with open(full_path,'wb') as f:
            f.write(response.body)
        
        

