from PIL import Image
from .dataset_maker import DatasetMaker
import numpy as np

for i in range(3):
    img=Image.open('1571908448.634513990.jpg').convert('RGB')
    new_img_arr=DatasetMaker.get_shuffled_pic(np.array(img),5,5)
    # print(new_img_arr.shape)
    new_img=Image.fromarray(new_img_arr)
    new_img.save('test{}.png'.format(i))