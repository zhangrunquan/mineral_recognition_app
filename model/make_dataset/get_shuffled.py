import argparse
import os
import pickle
import random
import time

import numpy as np
from PIL import Image
from tqdm import tqdm

from make_dataset import dataset_maker
from tool import Tools
from .dataset_maker import DatasetMaker
from settings import Settings


def get_args():
    parser = argparse.ArgumentParser()
    parser.add_argument('input_dir')
    parser.add_argument('output_dir')
    parser.add_argument('horizontal_split', type=int, help='横向切几块')
    parser.add_argument('vertical_split', type=int, help='纵向切几块')
    parser.add_argument('convert', type=int, default=0, help='是否要转换图片通道数')
    parser.add_argument('shuffle_times', type=int, default=1, help='输出几张随机拼接的图片')
    return parser.parse_args()


def main():
    args = get_args()

    if args.convert:
        print('convert used')
    Tools.create_save_dir(args.output_dir)

    directories = os.listdir(args.input_dir)
    tmp = dict(enumerate(directories))

    label_num_mapping = {v: k for k, v in tmp.items()}
    with open(os.path.join(args.output_dir, 'label_lst'), 'wb') as f:
        pickle.dump(directories, f)

    test_split = 0.1
    t = dataset_maker.make_picture_shuffled_dataset(args.input_dir, label_num_mapping, Settings.NUM_CLASSES, test_split,
                                                    args.horizontal_split, args.vertical_split,
                                                    over_sample=int(Settings.MAX_PIC_NUM * (1 - test_split)),
                                                    label_mode='num',
                                                    shuffle_times=args.shuffle_times)
    # t = dataset_maker.make_picture_shuffled_dataset(args.input_dir, label_num_mapping, 5, test_split,
    #                                                 args.horizontal_split, args.vertical_split,
    #                                                 over_sample=int(Settings.MAX_PIC_NUM * (1 - test_split)),
    #                                                 label_mode='num',
    #                                                 shuffle_times=args.shuffle_times)
    l = [np.array(data) for data in t]
    l1 = [print(arr.shape) for arr in l]
    DatasetMaker.save_npy_dataset(*l, output_dir=args.output_dir)


if __name__ == "__main__":
    main()
