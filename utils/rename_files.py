"""重命名图片集中的图片文件"""

import os
import argparse
from tqdm import tqdm

def rename_files_in_dir(directory,suffix='jpg'):
    l=os.listdir(directory)
    # name_set=
    count=0
    for file in tqdm(l,ncols=10):
        os.rename(os.path.join(directory,file),os.path.join(directory,str(count)+"."+suffix))
        count+=1

def rename_dataset_dir(dataset_dir):
    """假定文件组织如下
    dataset_dir
        | class1
        | class2
    classn文件夹下包含该类别所有图片
    
    """

    for c in tqdm(os.listdir(dataset_dir),ncols=10):
        rename_files_in_dir(os.path.join(dataset_dir,c))

def get_args():
    parser=argparse.ArgumentParser()
    parser.add_argument('-i',help='dataset dir')
    return parser.parse_args()

if __name__ == "__main__":
    args=get_args()
    rename_dataset_dir(args.i)