"""预处理图片,制作npy形式数据集"""
import os
import random

import numpy as np
from PIL import Image
from tqdm import tqdm
from settings import Settings


def load_pic_in_directory(directory):
    """
    Returns
        list of PIL Image
    """
    return [Image.open(os.path.join(directory, img)) for img in os.listdir(directory)]


def split(data, first_part_ratio):
    cut = int(len(data) * first_part_ratio)
    return data[:cut],data[cut:]

def over_sample(arr, length):
    '''将数组扩充到指定长度,重复已有的内容
    Args:
        arr: ndarray
        length: 目标长度
    '''
    arr = np.asarray(arr)
    len_arr = len(arr)
    n = int(length / len_arr - 1)
    tmp = arr
    if len(arr.shape)==1:
        for i in range(n):
            tmp = np.hstack([tmp, arr])
        left = length - len(tmp)
        return np.hstack([tmp,arr[:left]])
    else:
        for i in range(n):
            tmp = np.vstack([tmp, arr])
        left = length - len(tmp)
        return np.vstack([tmp, arr[:left, ...]])
