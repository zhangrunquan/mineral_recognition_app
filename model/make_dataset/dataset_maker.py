import argparse
import os
from dataset_loader import Loader
from tool import Tools
import numpy as np
from settings import Settings

def get_args():
    parser=argparse.ArgumentParser()
    parser.add_argument('input_dir')
    parser.add_argument('output_dir')
    parser.add_argument('sample_mode',default='over_sample',choices=['over_sample','normal'])
    parser.add_argument('label_mode',default='onehot',choices=['onehot','num'])
    return parser.parse_args()
    
class DatasetMaker():
    """将图片数据处理成其它形式保存的数据集
    """
    @staticmethod
    def save_npy(input_dir,output_dir,num_classes=Settings.NUM_CLASSES,sample_mode='over_sample',test_split=0.1,label_mode='onehot'):
        """将数据以npy文件保存
        """

        directories=os.listdir(input_dir)
        directories=[os.path.join(input_dir,v)for v in directories]
        tmp=dict(enumerate(directories))
        label_num_mapping={v:k for k,v in tmp.items()}
        Tools.create_save_dir(output_dir)
        if(sample_mode=='over_sample'):
            x_train,y_train,x_test,y_test=Loader.load_pics_over_sample(directories,label_num_mapping,num_classes,test_split=0.1,label_mode=label_mode)
        elif(sample_mode=='normal'):
            x_train,y_train,x_test,y_test=Loader.load_pics(directories,label_num_mapping,num_classes,test_split=0.1,label_mode=label_mode)
        np.save(os.path.join(output_dir,'x_train.npy'),x_train)
        np.save(os.path.join(output_dir,'y_train.npy'),y_train)
        np.save(os.path.join(output_dir,'x_test.npy'),x_test)
        np.save(os.path.join(output_dir,'y_test.npy'),y_test)

if __name__ == "__main__":
    args=get_args()
    DatasetMaker.save_npy(args.input_dir,args.output_dir,sample_mode=args.sample_mode,label_mode=args.label_mode)