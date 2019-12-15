"""处理不同源爬虫爬到相同图片的工具"""

import hashlib
import argparse
import os

def get_args():
    parser=argparse.ArgumentParser()
    parser.add_argument("-i1",help="input folder1")
    parser.add_argument("-i2",help="input folder2")
    return parser.parse_args()

def get_md5(filepath):
    """获取文件的md5"""
    m=hashlib.md5()
    with open(filepath,"rb") as f:
        s=f.read()
        m.update(s)
    return m.hexdigest()

class duplicate_detector:
    """检测,处理重复图片"""

    @staticmethod
    def duplicate_num_in_two_folder(folder1,folder2):
        """打印folder1和folder2的重复图片数,占两文件夹的比例
        
        Args:
            folder1: string

        """
        l1=os.listdir(folder1)
        md5set1={get_md5(os.path.join(folder1,filename)) for filename in l1}
        l2=os.listdir(folder2)
        md5set2={get_md5(os.path.join(folder2,filename)) for filename in l2}
        dup_n=len(md5set1&md5set2)
        f1_n,f2_n=len(l1),len(l2)
        print("folder1 path: "+folder1)
        print("folder2 path: "+folder2)
        print("folder1 num:{f1_n},folder2 num:{f2_n},shared:{sh_n},rate1:{r1},rate2:{r2}"   \
        .format(f1_n=f1_n,f2_n=f2_n,sh_n=dup_n,r1=dup_n/f1_n,r2=dup_n/f2_n))


if __name__ == "__main__":
    args=get_args()
    duplicate_detector.duplicate_num_in_two_folder(args.i1,args.i2)