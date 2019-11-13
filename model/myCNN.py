import numpy as np
import os
from tool import Tools
from keras.models import Sequential
from keras.layers import Convolution2D, Activation, MaxPooling2D, Dense,Conv2D,Flatten,Dropout
from keras.optimizers import Adam
from spp.SpatialPyramidPooling import SpatialPyramidPooling
from keras.utils.np_utils import to_categorical
from keras.preprocessing.image import ImageDataGenerator
import scipy
from scipy.misc import imresize
from PIL import Image
import random

def get_CNN_model(batch_size,num_classes):
    model = Sequential()
    model.add(Conv2D(32, (1,1),padding='same',data_format='channels_last',input_shape=(64,64,3)))
    model.add(Activation('relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Dropout(0.1))
    model.add(Convolution2D(32, 3, 3))
    model.add(Activation('relu'))
    model.add(Dropout(0.1))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Flatten())
    model.add(Dense(128))
    model.add(Dropout(0.1))
    model.add(Activation('sigmoid'))
    model.add(Dense(num_classes))
    model.add(Activation('softmax'))
    return model


def get_NN_model(batch_size,num_classes):
    model=Sequential()
    model.add(Dense(128,input_shape=(64,64,3)))
    model.add(Flatten())
    model.add(Activation('sigmoid'))
    # model.add(Dense(256))
    # model.add(Activation('sigmoid'))
    model.add(Dense(64))
    model.add(Activation('sigmoid'))
    model.add(Dense(num_classes))
    model.add(Activation('softmax'))
    return model 