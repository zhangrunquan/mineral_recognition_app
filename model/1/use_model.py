#encoding=utf-8
from keras.models import load_model
from keras.layers import Dense
from keras.preprocessing import image
from keras.applications.resnet50 import ResNet50
from keras.applications.inception_v3 import preprocess_input
import numpy as np
model = load_model('model5_0.01.h5')
model.load_weights('modelweight5_0.01.h5')
# image_path='chensha.jpg'
image_path='cassiterite.jpg'
# print(model.predict_classes('辰砂.png'))

# 加载图像
img = image.load_img(image_path, target_size=(32, 32))

# 图像预处理
x = image.img_to_array(img)
x = np.expand_dims(x, axis=0)
x = preprocess_input(x)

# 对图像进行分类
predict = model.predict(x)
print(predict)
predict=np.argmax(predict,axis=1)
print(predict)