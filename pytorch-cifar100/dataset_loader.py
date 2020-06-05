import os
import random

import numpy as np
# from PIL import Image
# from tqdm import tqdm
# from settings import Settings


class Loader():
    ''' 将图片以np数组的形式加载
        @param direcotories: 图片文件夹的列表
        @param label_num_mapping 形如{正长石:1}的字典
        @param num_classes 种类数
        @param test_split 切分验证集的比例
        @param label_mode 'onehot' 'num'
        return 所有图片构成的训练集和测试集
    '''

    # @staticmethod
    # def load_pics(directories, label_num_mapping, num_classes, test_split, label_mode='onehot'):
    #     x_train, y_train, x_test, y_test = [], [], [], []
    #     for directory in tqdm(directories, ncols=10):
    #         tmp_train, tmp_test = Loader.load_pic(directory, test_split)
    #         num_label = label_num_mapping[directory]
    #         x_train += list(tmp_train)
    #         x_test += list(tmp_test)
    #         if (label_mode == 'onehot'):
    #             y_train += list(Loader.get_one_hot(np.full(len(tmp_train), num_label), num_classes))
    #             y_test += list(Loader.get_one_hot(np.full(len(tmp_test), num_label), num_classes))
    #         elif (label_mode == 'num'):
    #             y_train += list(np.full(len(tmp_train), num_label))
    #             y_test += list(np.full(len(tmp_test), num_label))
    #     x_train, y_train = np.asarray(x_train), np.asarray(y_train)
    #     x_test, y_test = np.asarray(x_test), np.asarray(y_test)
    #     return x_train, y_train, x_test, y_test

    # @staticmethod
    # def load_pics_2(input_dir, label_num_mapping, num_classes, label_mode='onehot'):
    #     """这个版本不将各标签提前合并,不提前split,增加了灵活性"""
    #     x, y = [], []
    #     from make_dataset import tools
    #     directories = os.listdir(input_dir)
    #     # directories = [os.path.join(input_dir, d) for d in directories]
    #     for directory in tqdm(directories, ncols=10):
    #         tmp_x = tools.load_pic_in_directory(os.path.join(input_dir,directory))
    #         num_label = label_num_mapping[directory]
    #         x.append(list(tmp_x))
    #         if label_mode == 'onehot':
    #             y.append(list(Loader.get_one_hot(np.full(len(tmp_x), num_label), num_classes)))
    #         elif label_mode == 'num':
    #             y.append(list(np.full(len(tmp_x), num_label)))
    #     return x, y

    # @staticmethod
    # def load_pic(directory, test_split, shuffle=False):
    #     """ 将文件夹下图片以np数组的形式加载
    #     Args:
    #         directory: 图片文件夹
    #         test_split: 切分验证集的比例
    #     Returns:
    #         图片构成的训练集和测试集
    #     """
    #     data = [np.array(Image.open(directory + '/' + img)) for img in os.listdir(directory)]
    #     if shuffle:
    #         random.shuffle(data)
    #     cut = int(len(data) * test_split)
    #     return data[cut:], data[:cut]

    # @staticmethod
    # def get_one_hot(targets, nb_classes):
    #     '''将targets 1darray转化为对应的onehot编码的数组
    #     @param nb_classes:种类数(onehot向量长度)
    #     '''
    #     res = np.eye(nb_classes)[np.array(targets).reshape(-1)]
    #     if (nb_classes < 128):
    #         res = np.asarray(res, dtype=np.int8)
    #     return res.reshape(list(targets.shape) + [nb_classes])

    # @staticmethod
    # def load_pics_over_sample(directories, label_num_mapping, num_classes, test_split, least_num=Settings.MAX_PIC_NUM,
    #                           label_mode='onehot'):
    #     '''加载图片,进行过采样,将各种类图片数补足至least_num
    #     '''
    #     x_train, y_train, x_test, y_test = [], [], [], []
    #     for directory in tqdm(directories, ncols=10):
    #         tmp_train, tmp_test = Loader.load_pic(directory, test_split)
    #         tmp_train = Loader.over_sample(tmp_train, int(least_num * (1 - test_split)))
    #         tmp_test = Loader.over_sample(tmp_test, int(least_num * test_split))
    #         x_train += list(tmp_train)
    #         x_test += list(tmp_test)
    #         num_label = label_num_mapping[directory]
    #         if (label_mode == 'onehot'):
    #             y_train += list(Loader.get_one_hot(np.full(len(tmp_train), num_label), num_classes))
    #             y_test += list(Loader.get_one_hot(np.full(len(tmp_test), num_label), num_classes))
    #         elif (label_mode == 'num'):
    #             y_train += list(np.full(len(tmp_train), num_label))
    #             y_test += list(np.full(len(tmp_test), num_label))
    #     x_train, y_train = np.asarray(x_train), np.asarray(y_train)
    #     x_test, y_test = np.asarray(x_test), np.asarray(y_test)
    #     return x_train, y_train, x_test, y_test

    # '''将数组扩充到指定长度,重复已有的内容
    #     @param arr: ndarray
    #     @param length: 目标长度
    # '''

    # @staticmethod
    # def over_sample(arr, length):
    #     arr = np.asarray(arr)
    #     len_arr = len(arr)
    #     n = int(length / len_arr - 1)
    #     tmp = arr
    #     for i in range(n):
    #         tmp = np.vstack([tmp, arr])
    #     left = length - len(tmp)
    #     tmp = np.vstack([tmp, arr[:left, ...]])
    #     return tmp

    @staticmethod
    def load_npy_data(input_dir):
        """从文件夹中的npy文件加载数据集
        """
        x_train = np.load(os.path.join(input_dir, 'x_train.npy'), allow_pickle=True)
        x_test = np.load(os.path.join(input_dir, 'x_test.npy'), allow_pickle=True)
        y_train = np.load(os.path.join(input_dir, 'y_train.npy'), allow_pickle=True)
        y_test = np.load(os.path.join(input_dir, 'y_test.npy'), allow_pickle=True)
        return x_train, y_train, x_test, y_test
