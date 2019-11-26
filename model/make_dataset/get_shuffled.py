from .dataset_maker import DatasetMaker
import argparse

def get_args():
    parser=argparse.ArgumentParser()
    parser.add_argument('input_dir')
    parser.add_argument('output_dir')
    parser.add_argument('horizontal_split',type=int,help='横向切几块')
    parser.add_argument('vertical_split',type=int,help='纵向切几块')
    parser.add_argument('convert',type=int,default=0,help='是否要转换图片通道数')
    parser.add_argument('shuffle_times',type=int,default=1,help='输出几张随机拼接的图片')
    return parser.parse_args()

if __name__ == "__main__":
    args=get_args()
    if(args.convert):
        print('convert used')
    DatasetMaker.save_shuffled_pic(args.input_dir,args.output_dir,args.horizontal_split
    ,args.vertical_split,args.convert,args.shuffle_times)