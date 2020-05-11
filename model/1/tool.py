import os

import numpy as np
from PIL import Image
from tqdm import tqdm
import matplotlib.image as mpimg
from keras.utils.np_utils import to_categorical


class Tools:
    """提供一般性的常用函数
    """
    @staticmethod
    def load_data(input_dir,directories,label_num_mapping,num_classes,test_split):
        x_train,y_train,x_test,y_test=[],[],[],[]
        for directory in tqdm(directories,ncols=10):
            dir_path=os.path.join(input_dir,directory)
            tmp_data=[ mpimg.imread(os.path.join(dir_path,img)) for img in os.listdir(dir_path) ]
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
    def delete_file_if_exist(filepath): 
        if os.path.isfile(filepath):
            os.remove(filepath)
