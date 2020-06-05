""" train and test dataset

author baiyu
"""
import os
import sys
import pickle

# from skimage import io
# import matplotlib.pyplot as plt
import numpy 
import torch
from torch.utils.data import Dataset
from dataset_loader import Loader

class CIFAR100Train(Dataset):
    """cifar100 test dataset, derived from
    torch.utils.data.DataSet
    """

    def __init__(self, input_dir, transform=None):
        #if transform is given, we transoform data using
        self.x_train,self. y_train,_, _=Loader.load_npy_data(input_dir)
        self.transform = transform
        
    def __len__(self):
        return len(self.y_train)

    def __getitem__(self, index):
        label=self.y_train[index]
        image=self.x_train[index]
        if self.transform:
            image = self.transform(image)
        return label, image

class CIFAR100Test(Dataset):
    """cifar100 test dataset, derived from
    torch.utils.data.DataSet
    """

    def __init__(self, input_dir, transform=None):
        #if transform is given, we transoform data using
        self.x_test,self. y_test,_, _=Loader.load_npy_data(input_dir)
        self.transform = transform
        
    def __len__(self):
        return len(self.y_test)

    def __getitem__(self, index):
        label=self.y_test[index]
        image=self.x_test[index]
        if self.transform:
            image = self.transform(image)
        return label, image

