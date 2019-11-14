import os
import matplotlib.image as mpimg
import numpy as np

from keras.models import Sequential
from keras.layers import Convolution2D, Activation, MaxPooling2D, Dense,Conv2D,Flatten,Dropout
from keras.optimizers import Adam
# from spp.SpatialPyramidPooling import SpatialPyramidPooling
from keras.utils.np_utils import to_categorical
from keras.preprocessing.image import ImageDataGenerator
import scipy
from scipy.misc import imresize
from PIL import Image
import random

class Tools():
    @staticmethod
    def load_data(directories,label_num_mapping,num_classes,test_split):
        x_train,y_train,x_test,y_test=[],[],[],[]
        for directory in directories:
            tmp_data=[ mpimg.imread(directory+'/'+img) for img in os.listdir(directory) ]
            cut=int(len(tmp_data)*test_split)
            x_train=x_train+tmp_data[cut:]
            x_test=x_test+tmp_data[:cut]
            num_label=label_num_mapping[directory]
            y_train=y_train+[to_categorical(num_label,num_classes) for _ in range(len(tmp_data)-cut)]
            y_test=y_test+[to_categorical(num_label,num_classes) for _ in range(cut)]
        x_train,y_train=np.asarray(x_train),np.asarray(y_train)
        x_test,y_test=np.asarray(x_test),np.asarray(y_test)
        return x_train,y_train,x_test,y_test
    @staticmethod
    def get_search_url(keyword):
        return 'https://www.mindat.org/photoscroll.php?frm_id=pscroll&cform_is_valid=1&searchbox={keyword}&submit_pscroll=Search'.format(keyword=keyword)
    @staticmethod
    def create_save_dir(path):
        if(not os.path.exists(path)):
            os.makedirs(path)
    @staticmethod
    def removeDir(dirPath):
        if not os.path.isdir(dirPath):
            return
        files = os.listdir(dirPath)
        for file in files:
            filePath = os.path.join(dirPath,file)
            if os.path.isfile(filePath):
                os.remove(filePath)
            elif os.path.isdir(filePath):
                    Tools.removeDir(filePath)
        os.rmdir(dirPath)
    @staticmethod
    def read_list(filepath): 
        with open(filepath,'r',encoding='utf8') as f:
            s=f.read()
            return s.split('\n')
    @staticmethod
    def delete_file_if_exist(filepath): 
        if os.path.isfile(filepath):
                os.remove(filepath)

class MetaInfo():
    initial_img_num=50 # 搜索后页面上的图片数
    increase_num=15 #每次下拉到底新增图片数
    save_dir='./photos/' #输出文件夹
    label_file_path='./label.txt' #关键词文件
    log_file_path='./log.txt' #记录获取到各种图片链接数的log

class LinkListCrawlerMeta():
    log_file_path='./link_list_crawler_log'
    save_dir='./link_list/'

