from keras.models import Sequential
from keras.layers import Convolution2D, Activation, MaxPooling2D, Dense,Conv2D,Flatten,Dropout
from PIL import Image

def get_CNN_model(batch_size,num_classes,input_shape):
    model = Sequential()
    model.add(Conv2D(32, (1,1),padding='same',data_format='channels_last',input_shape=input_shape))
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


def get_NN_model(batch_size,num_classes,input_shape):
    model=Sequential()
    model.add(Dense(128,input_shape=input_shape))
    model.add(Flatten())
    model.add(Activation('sigmoid'))
    # model.add(Dense(256))
    # model.add(Activation('sigmoid'))
    model.add(Dense(64))
    model.add(Activation('sigmoid'))
    model.add(Dense(num_classes))
    model.add(Activation('softmax'))
    return model 


# keras cifar10 example
def get_keras_cnn_example(num_classes,input_shape):
    model = Sequential()
    model.add(Conv2D(128, (3, 3), padding='same',
                    input_shape=input_shape))
    model.add(Activation('relu'))
    model.add(Conv2D(64, (3, 3)))
    model.add(Activation('relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    # model.add(Dropout(0.25))


    model.add(Dropout(0.1))

    model.add(Conv2D(64, (3, 3), padding='same'))
    model.add(Activation('relu'))
    model.add(Conv2D(64, (3, 3)))
    model.add(Activation('relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    # model.add(Dropout(0.25))

    model.add(Dropout(0.1))


    model.add(Flatten())
    model.add(Dense(512))
    model.add(Activation('relu'))
    # model.add(Dropout(0.5))


    model.add(Dropout(0.2))


    model.add(Dense(num_classes))
    model.add(Activation('softmax'))
    return model