import matplotlib.image as mpimg
import numpy as np
import os
from tool import Tools
from keras.models import Sequential
from keras.layers import Convolution2D, Activation, MaxPooling2D, Dense,Conv2D,Flatten,Dropout
from keras.optimizers import Adam
# from spp.SpatialPyramidPooling import SpatialPyramidPooling
from keras.utils.np_utils import to_categorical
from keras.preprocessing.image import ImageDataGenerator
import scipy
from scipy.misc import imresize
from PIL import Image
import random
from resnet import ResNet50
from myCNN import get_CNN_model,get_NN_model

#参数
batch_size = 128
num_channels = 3
num_classes = 61
epochs=50

# 读取数据
output_dir='data/'
remove={'a.py','.vscode','tool.py','data','__pycache__','make_dataset.py','test_spp.py','test_spp2.py','test_spp3.py','resize_img.py','test_spp4.py',
'resnet.py','myCNN.py','run_model.py'}
directories=set(os.listdir())-remove
# 建立标签到数字的映射
tmp=dict(enumerate(directories))
label_num_mapping={v:k for k,v in tmp.items()}
Tools.create_save_dir(output_dir)
x_train,y_train,x_test,y_test=Tools.load_data(directories,label_num_mapping,num_classes,test_split=0.1)

# 图片预处理工具
datagen = ImageDataGenerator(
    featurewise_center=True,
    featurewise_std_normalization=True,
    rotation_range=20,
    width_shift_range=0.2,
    height_shift_range=0.2,
    horizontal_flip=True)
datagen.fit(x_train)


# 训练
for batch_size in [128,256]:
    for lr in [0.001,0.0005,0.01]:
        print('lr={0},batchsize={1}'.format(lr,batch_size))

        model=ResNet50(input_shape=(64,64,3),classes=num_classes)
        # model=get_CNN_model(batch_size,num_classes)
        # model=get_NN_model(batch_size,num_classes)

        model.compile(loss='categorical_crossentropy', optimizer=Adam(lr=lr),metrics=['acc'])
        
        model.fit_generator(datagen.flow(x_train, y_train, batch_size=batch_size),
                    steps_per_epoch=len(x_train) / batch_size, epochs=epochs,validation_data=(x_test,y_test))




    

