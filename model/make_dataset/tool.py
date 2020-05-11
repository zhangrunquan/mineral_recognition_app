import os

import numpy as np
from PIL import Image
from tqdm import tqdm

class Tools:
    """提供一般性的常用函数
    """
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

    # @staticmethod
    # def load_data(directories,label_num_mapping,num_classes,test_split=0.1): \
    #     directories = [d for d in os.listdir(data_directory)
    #                    if os.path.isdir(os.path.join(data_directory, d))]
    #
    #     labels = []
    #     images = []
    #     for d in directories:
    #         label_directory = os.path.join(data_directory, d)
    #         file_names = [os.path.join(label_directory, f)
    #                       for f in os.listdir(label_directory)
    #                       if f.endswith(".ppm")]
    #         for f in file_names:
    #             images.append(data.imread(f))
    #             labels.append(int(d))
    #     return images, labels
    #
    #
    #     ROOT_PATH = "F:/Belgian traffic - Deep learning/"
    #     train_data_directory = os.path.join(ROOT_PATH, "TrafficSigns/Training")
    #     test_data_directory = os.path.join(ROOT_PATH, "TrafficSigns/Testing")
    #     pass
