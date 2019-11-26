import argparse
import os
import random
import time

import numpy as np
from PIL import Image
from tqdm import tqdm

from dataset_loader import Loader
from settings import Settings
from tool import Tools


def get_args():
    parser=argparse.ArgumentParser()
    parser.add_argument('input_dir')
    parser.add_argument('output_dir')
    parser.add_argument('sample_mode',default='over_sample',choices=['over_sample','normal'])
    parser.add_argument('label_mode',default='onehot',choices=['onehot','num'])
    return parser.parse_args()
    
class DatasetMaker():
    """将图片数据处理成其它形式保存的数据集
    """
    @staticmethod
    def save_npy(input_dir,output_dir,num_classes=Settings.NUM_CLASSES,sample_mode='over_sample',test_split=0.1,label_mode='onehot'):
        """将数据以npy文件保存"""

        directories=os.listdir(input_dir)
        directories=[os.path.join(input_dir,v)for v in directories]
        tmp=dict(enumerate(directories))
        label_num_mapping={v:k for k,v in tmp.items()}
        Tools.create_save_dir(output_dir)
        if(sample_mode=='over_sample'):
            x_train,y_train,x_test,y_test=Loader.load_pics_over_sample(directories,label_num_mapping,num_classes,test_split=0.1,label_mode=label_mode)
        elif(sample_mode=='normal'):
            x_train,y_train,x_test,y_test=Loader.load_pics(directories,label_num_mapping,num_classes,test_split=0.1,label_mode=label_mode)
        DatasetMaker.save_npy_dataset(x_train,y_train,x_test,y_test,output_dir)

    @staticmethod
    def save_mid_pic(input_dir,output_dir,out_put_size,convert=False):
        """将input_dir中的图片取中间,然后以图片的形式保存"""

        directories=os.listdir(input_dir)
        tmp=dict(enumerate(directories))
        label_num_mapping={v:k for k,v in tmp.items()}
        Tools.create_save_dir(output_dir)
        for directory in directories:
            Tools.create_save_dir(os.path.join(output_dir,directory))
            for img_file_path in tqdm(os.listdir(os.path.join(input_dir,directory)),ncols=10):
                try:
                    img=Image.open(os.path.join(input_dir,directory,img_file_path))
                    if(convert):
                        img=img.convert('RGB')
                except Exception as e:
                    print(str(e))
                    continue
                arr=np.array(img)
                arr=DatasetMaker.get_pic_middle(arr,out_put_size)
                img=Image.fromarray(arr)
                filename=str(time.time())+str(random.randint(1,100))+'.png'
                img.save(os.path.join(output_dir,directory,filename))
                
    @staticmethod
    def save_shuffled_pic(input_dir,output_dir,horizontal_split,vertical_split,
        convert=False,shuffle_times=1):
        """将目标文件夹的图片做shuffle(切碎重组)处理
        
        Args:
            int: horizontal_split 水平切分的份数
            int: vertical_split 竖直切分的份数
            int: shuffle_times 输出几份shuffle过的图片
        """
        directories=os.listdir(input_dir)
        tmp=dict(enumerate(directories))
        label_num_mapping={v:k for k,v in tmp.items()}
        Tools.create_save_dir(output_dir)
        for directory in directories:
            Tools.create_save_dir(os.path.join(output_dir,directory))
            for img_file_path in tqdm(os.listdir(os.path.join(input_dir,directory)),ncols=10):
                try:
                    img=Image.open(os.path.join(input_dir,directory,img_file_path))
                    if(convert):
                        img=img.convert('RGB')
                except Exception as e:
                    print(str(e))
                    continue
                arr=np.array(img)
                for _ in range(shuffle_times):
                    arr=DatasetMaker.get_shuffled_pic(arr,horizontal_split,vertical_split)
                    img=Image.fromarray(arr)
                    filename=str(time.time())+str(random.randint(1,100))+'.png'
                    img.save(os.path.join(output_dir,directory,filename))



    @staticmethod
    def prepare_input_output(input_dir,output_dir):
        """根据输入输出路径,做必要准备工作,返回必要信息
        
        准备工作:创建输出文件夹

        Returns:
            tuple: (directories,label_num_mapping) directories是各种类图片文件夹路径的列表

        """
        directories=os.listdir(input_dir)
        directories=[os.path.join(input_dir,v)for v in directories]
        tmp=dict(enumerate(directories))
        label_num_mapping={v:k for k,v in tmp.items()}
        Tools.create_save_dir(output_dir)
        return directories,label_num_mapping



    @staticmethod
    def save_npy_dataset(x_train,y_train,x_test,y_test,output_dir):
        np.save(os.path.join(output_dir,'x_train.npy'),x_train)
        np.save(os.path.join(output_dir,'y_train.npy'),y_train)
        np.save(os.path.join(output_dir,'x_test.npy'),x_test)
        np.save(os.path.join(output_dir,'y_test.npy'),y_test)

    @staticmethod
    def get_pic_middle(pic,out_put_size):
        """取图片的中间n*n像素作为新图片
        
        Args:
            ndarray: pic 一张图片(3,n,k)
            int: output_size 输出图片大小
        
        Returns:
            ndarray: 新图片
        """
        shape=pic.shape
        middle1,middle2=int(shape[0]/2),int(shape[1]/2)
        r=int(out_put_size/2)
        return pic[middle1-r:middle1+r,middle2-r:middle2+r,:]


    @staticmethod
    def get_shuffled_pic(pic,horizontal_split,vertical_split):
        """将图片切分成小块,然后随机洗切小块的位置构成新图片
        
        Args:
            ndarray: pic 一张图片(宽,高,3)
            int: horizontal_split 水平切分的份数
            int: vertical_split 竖直切分的份数

        Returns:
            ndarray: 新图片
        
        """
        height,width=pic.shape[1],pic.shape[0]
        # 小块的高宽
        piece_h,piece_w=int(height/vertical_split),int(width/horizontal_split)
        piece_lst=[]
        # 从图片上截取小块放入列表
        for h in range(horizontal_split):
            for v in range(vertical_split):
                h_start,h_end=h*piece_w,(h+1)*piece_w
                v_start,v_end=v*piece_h,(v+1)*piece_h
                piece_lst.append(pic[h_start:h_end,v_start:v_end,:]) 
        # 洗切顺序
        random.shuffle(piece_lst)
        # 重构图片,先拼接横条,再竖向拼接
        row_lst=[]
        # 每拼接一横条,消耗horizontal_spilt个piece,共vertical_split个横条
        for start_index in [horizontal_split*i for i in range(vertical_split)]:
            row=piece_lst[start_index]
            for i in range(start_index+1,start_index+horizontal_split):
                row=np.concatenate([row,piece_lst[i]],axis=0)
            row_lst.append(row)
        # 将横条拼接起来
        tmp=row_lst[0]
        for i in range(1,len(row_lst)):
            tmp=np.concatenate([tmp,row_lst[i]],axis=1)
        return tmp

if __name__ == "__main__":
    args=get_args()
    DatasetMaker.save_npy(args.input_dir,args.output_dir,sample_mode=args.sample_mode,label_mode=args.label_mode)
