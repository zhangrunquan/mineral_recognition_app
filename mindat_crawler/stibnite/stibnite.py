import time,timeit,re
import requests
import requests.cookies
#from lxml import etree
##python3.7的lxml没有etree了。。我刚更新
import lxml.html
etree = lxml.html.etree
import os, json
import mozinfo
import math,random

kv = {'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36'}
def get_page(req,url):
    try:
        response=req.get(url)
        if response.status_code == 200:
            return response.text
    except requests.ConnectionError:
        return None

def get_and_save(req,previous_url,text,page):

    print('正在爬取[{}]'.format(page))
    tree = etree.HTML(text)
    urls=tree.xpath('//img/@src')
    urls=['https://zh.mindat.org' + i for i in urls if i != 'images/childm.png']
    for url in urls:
        print(url)
        nowkv = kv
        nowkv.update({'Referer': previous_url})
        save2file(req,url,nowkv)#urls是第page页的所有图片队友的网页

def save2file(req,url,headers):
    response = req.get(url, headers=headers)
    code_img = response.content
    path = url.split('/')[-1]
    with open(path, 'wb') as fn:  # wb代表二进制文件
        fn.write(code_img)


# with open(path, 'wb') as f:
#     f.write(r.content)
def main():
    req = requests.Session()
    for i in range(1,197):
        url = 'https://zh.mindat.org/gallery.php?cform_is_valid=1&frm_id=pager&\
        min=3782&loc=&u=&potd=&pco=&d=&showtype=1&phototype=1&checkall=0&filtcountry=0&loctxt=&keyw\
        ords=&orderxby=0&submit_pager=Filter+Search&photoclass=1&cf_pager_page=\
    ' + str(i)

        try:
            response = req.get(url)
            response.encoding = response.apparent_encoding
            if response.status_code == 200:
                get_and_save(req,url, response.text,i)
        except requests.ConnectionError:
            print('Connection Error')

        # link_list=list(get_and_save(driver1,urls))
        # print(link_list)
    # switch=input('用你的请输入1 用我的请输入2')

    # if not(user_info):
    #     for i in user_info:





# 测试时间
def count_spend_time(func):
    start_time = time.perf_counter()
    func()
    end_time = time.perf_counter()
    time_dif = (end_time - start_time)
    second = time_dif % 60
    minute = (time_dif // 60) % 60
    hour = (time_dif // 60) // 60
    print('spend ' + str(hour) + 'hours,' + str(minute) + 'minutes,' + str(second) + 'seconds')


if __name__ == '__main__':
    t1=timeit.Timer("main()",'from __main__ import main')
    print(t1.timeit(1))