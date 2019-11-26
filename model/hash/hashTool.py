"""图片hash相关的工具函数集"""

from tool import Tools

import imagehash
import os
import heapq
from PIL import Image

from tqdm import tqdm

class HashTool:
    """生成图片hash相关的工具函数集"""

    @staticmethod
    def get_file_hash(path):
        """获取图片文件的hash值"""
        return imagehash.phash(Image.open(path))

    @staticmethod
    def get_files_hash(directory):
        """获取文件夹下所有图片的hash值
        
        Returns:
            list: 元组(ImageHash,full_path)构成的列表

        """
        
        file_names=os.listdir(directory)
        l=[]
        for file_name in tqdm(file_names,ncols=10):
            full_path=os.path.join(directory,file_name)
            hashv=HashTool.get_file_hash(full_path)
            l.append((hashv,full_path))
        return l

    @staticmethod
    def n_smallest(tuple_list,target_hash,n):
        """从元组的列表中找出hash与目标差值最小的n张图片,元组的第一项应为该图片hash值
        
        Returns:
            list: 找到tuple构成的列表
        """
        return heapq.nsmallest(n,tuple_list,key=lambda t:t[0]-target_hash)


class HashEngine:
    """使用图片hash进行判断的函数集"""

    @staticmethod
    def mean_distance(tuple_list,target_hash):
        """计算target_hash与tuple_list中所有hash的平均hamming距离
        
        Args:
            list: tuple_list (ImageHash,...)元组构成的列表,元组第一个元素必须为ImageHash

        Returns:
            int : target_hash与tuple_list中所有hash的平均hamming距离
        """
        
        total_dist=0
        for t in tuple_list:
            dist=t[0]-target_hash
            total_dist+=dist
        return total_dist/len(tuple_list)

    