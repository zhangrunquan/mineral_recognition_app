from .dataset_maker import DatasetMaker
import argparse

def get_args():
    parser=argparse.ArgumentParser()
    parser.add_argument('input_dir')
    parser.add_argument('output_dir')
    parser.add_argument('pic_size',type=int,help='取中间多大')
    parser.add_argument('convert',type=int,default=0,help='是否要转换图片通道数')
    return parser.parse_args()

if __name__ == "__main__":
    args=get_args()
    if(args.convert):
        print('convert used')
    DatasetMaker.save_mid_pic(args.input_dir,args.output_dir,args.pic_size
    ,convert=args.convert)