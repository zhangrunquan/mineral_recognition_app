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
from .tool import Tools,MetaInfo,LinkListCrawlerMeta


# 仅增量的爬取图片的链接,存入文件
class MindatLinkListSpider(scrapy.Spider):
    name = 'mindat_link_list'
    allowed_domains = ['mindat.org']
    start_urls = ['http://mindat.org/']

    custom_settings = {'LOG_LEVEL': 'INFO'}
    def parse(self, response):
        # preparation
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

        for kw in keyword_list:
            url=Tools.get_search_url(kw)
            while(1):
                try:
                    driver.get(url)
                    break
                except TimeoutException as e:
                    self.logger.warning("get search page of {kw} : timeout".format(kw=kw))
                    continue
            num=MetaInfo.initial_img_num # 一开始搜索页的图片数量
            lastlen=0
            retry_num=0  # 重试三次没加载新图片才认为到底
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
                    self.logger.info("keyword: {kw}no new image ,retry num {n}".format(n=retry_num,kw=kw))
                    retry_num+=1
                    if(retry_num==20):
                        break
                    else:
                        continue
                retry_num=0 # 成功加载新图片,重试次数置0
                lastlen=newlen
                
            selectors = driver.find_elements_by_css_selector(".psimage") 
            srcs={x.get_attribute('href') for x in selectors}
            # 读取旧链接集合
            # with open(LinkListCrawlerMeta.save_dir+'link_list_{kw}.txt'.format{kw=kw},"rw",) asf:

            # 取并集,写入文件

            # 增量情况写入log

            
            logf.write(kw+','+str(lastlen)+'\n') # 记录各种标签图片数
            logf.flush()
            meta={'keyword':kw}
           

        #清理
        logf.close()
        driver.quit()
        
