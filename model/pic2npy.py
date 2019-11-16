from tool import Tools
import os
import numpy as np
import argparse

def get_args():
    parser=argparse.ArgumentParser()
    parser.add_argument('input_dir')
    return parser.parse_args()

args=get_args

num_classes = 61


input_dir='E:/data/双创/data32.32/'
output_dir='output/'
remove={'a.py','.vscode','tool.py','data','__pycache__','make_dataset.py','test_spp.py','test_spp2.py','test_spp3.py','resize_img.py','test_spp4.py',
'resnet.py','myCNN.py','run_model.py'}
directories=set(os.listdir(input_dir))-remove
directories={ input_dir+v for v in directories}
# 建立标签到数字的映射
tmp=dict(enumerate(directories))
label_num_mapping={v:k for k,v in tmp.items()}
Tools.create_save_dir(output_dir)
x_train,y_train,x_test,y_test=Tools.load_data(directories,label_num_mapping,num_classes,test_split=0.1)
np.save(output_dir+'x_train.npy',x_train)
np.save(output_dir+'y_train.npy',y_train)
np.save(output_dir+'x_test.npy',x_test)
np.save(output_dir+'y_test.npy',y_test)


