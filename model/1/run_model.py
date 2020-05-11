import matplotlib.image as mpimg
import numpy as np
import os
from tool import Tools
from keras.models import Sequential
from keras.layers import Convolution2D, Activation, MaxPooling2D, Dense,Conv2D,Flatten,Dropout
from keras.optimizers import Adam
from keras.utils.np_utils import to_categorical
from keras.preprocessing.image import ImageDataGenerator
import scipy
from scipy.misc import imresize
from PIL import Image
import random
from resnet import ResNet50

#参数
batch_size = 5
num_channels = 3
num_classes = 61
epochs=2

# 读取数据
input_dir='./data'
directories=os.listdir(input_dir)
# 建立标签到数字的映射
tmp=dict(enumerate(directories))
label_num_mapping={v:k for k,v in tmp.items()}
x_train,y_train,x_test,y_test=Tools.load_data(input_dir,directories,label_num_mapping,num_classes,test_split=0.1)

print(x_train.shape)
print(y_train.shape)


# 图片预处理工具
datagen = ImageDataGenerator(
    featurewise_center=True,
    featurewise_std_normalization=True,
    rotation_range=20,
    width_shift_range=0.2,
    height_shift_range=0.2,
    dtype='uint8',
    preprocessing_function=lambda image:image.astype(np.uint8),
    horizontal_flip=True
)
datagen.fit(x_train)


# 训练
for batch_size in [5]:
    for lr in [0.01]:
        print('lr={0},batchsize={1}'.format(lr,batch_size))

        model=ResNet50(input_shape=(32,32,3),classes=num_classes)


        model.compile(loss='categorical_crossentropy', optimizer=Adam(lr=lr),metrics=['acc'])
        print(datagen.flow(x_train, y_train, batch_size=batch_size))
        # datagen.flow(x_train, y_train, batch_size=batch_size)
        model.fit_generator(datagen.flow(x_train, y_train, batch_size=batch_size),
                    steps_per_epoch=len(x_train) / batch_size, epochs=epochs,validation_data=(x_test,y_test))
        model.save('model'+str(batch_size)+'_'+str(lr)+'.h5')
        model.save_weights('modelweight'+str(batch_size)+'_'+str(lr)+'.h5')



    

