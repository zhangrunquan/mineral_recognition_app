%%
% [AlexLayer,opts]=shouda_alexlayer()
% [AlexLayer_New , optionsTransfer]=FineTune(AlexLayer)
[AlexLayer_New , optionsTransfer]=FineTune2(alexnet())%FineTune是Parallel库里的一个内置函数
%%
% path = 'C:\mineral recongnition\data64\';
% files = dir([path,'*']);
% len=length(files)
% for n = 3:len
%     filename=files(n).name;
%     new_path=strcat(path,filename,'\');
%     traindata=imageDatastore(new_path,'LabelSource','foldernames');
% end
% path = 'C:\mineral recongnition\data64\cassiterite\';
% files = dir([path,'*']);
% len=length(files)
% 
% traindata=imageDatastore('C:\mineral recongnition\data64\cassiterite\','LabelSource','foldernames')
%%
dummy = imageDatastore('C:\mineral recongnition\data64\','IncludeSubfolders',true,'LabelSource','foldernames');
%imresize(dummy,[227 227]);
%%
inputSize = [227 227];
dummy.ReadFcn = @(loc)imresize(imread(loc),inputSize);
%%
[imds_train,imds_test]= splitEachLabel(dummy,0.8,'randomized')
imageSize = [227 227 3];
pixelRange = [-4 4];
imageAugmenter = imageDataAugmenter( ...
    'RandXReflection',true, ...
    'RandXTranslation',pixelRange, ...
    'RandYTranslation',pixelRange);
augmentedImdsTrain = augmentedImageDatastore(imageSize,imds_train, ...
    'DataAugmentation',imageAugmenter);
%%
%load 'cars_meta.mat';
%annotations.class即为标注信息
       % ac=[annotations.class]%annotations from cars_train_annos
       % traindata.Labels=categorical(ac(1:6000))%取前6000个进行标签
%traindata.Labels=categorical([class_names])
%%
unique(imds_train.Labels)%看数量是不是61个
unique(imds_test.Labels)

%%
AlexNet_New=trainNetwork(augmentedImdsTrain,AlexLayer_New,optionsTransfer)
%'useGPU','yes'
%%
%测试
YPredicted = classify(AlexNet_New,imds_test);
accuracy = sum(YPredicted == imds_test.Labels)/numel(imds_test.Labels)


%%
function test(fileRoad,AlexNet_New,class_names)%封装测试
testImage=imread(fileRoad);
testImage_=imresize(testImage,[227 227]);
TypeNum=classify(AlexNet_New,testImage_);
TypeName=class_names(TypeNum);
disp(TypeName);
figure;
imshow(testImage);
end
%%
function [AlexLayer_New , optionsTransfer]=FineTune2(AlexNet)%改最后三层
% AlexNet_reduce = AlexNet(1:end-3);
AlexNet_reduce = AlexNet.Layers(1:end-3);
%add
Last3Layers = [
fullyConnectedLayer(61,'Name','fc8','WeightLearnRateFactor',10, 'BiasLearnRateFactor',20)
softmaxLayer('Name','softmax')
classificationLayer('Name','classification')
];
AlexLayer_New=[AlexNet_reduce
    Last3Layers];

optionsTransfer = trainingOptions('sgdm',...%有动量的随机梯度下降
         'ExecutionEnvironment','multi-gpu', ...
         'MaxEpochs',10,...
         'InitialLearnRate',0.0005,...
         'Verbose',true,'MiniBatchSize', 100);%MiniBatchSize根据显卡内存而定
% numGPUs = 2;
% miniBatchSize = 256*numGPUs;
% initialLearnRate = 1e-1*miniBatchSize/256;
% 
% options = trainingOptions('sgdm', ...
%     'ExecutionEnvironment','multi-gpu', ... % Turn on automatic multi-gpu support.
%     'InitialLearnRate',initialLearnRate, ... % Set the initial learning rate.
%     'MiniBatchSize',miniBatchSize, ... % Set the MiniBatchSize.
%     'Verbose',false, ... % Do not send command line output.
%     'Plots','training-progress', ... % Turn on the training progress plot.
%     'L2Regularization',1e-10, ...
%     'MaxEpochs',60, ...
%     'Shuffle','every-epoch', ...
%     'ValidationData',imdsTest, ...
%     'ValidationFrequency',floor(numel(imdsTrain.Files)/miniBatchSize), ...
%     'LearnRateSchedule','piecewise', ...
%     'LearnRateDropFactor',0.1, ...
%     'LearnRateDropPeriod',50);
end
%%
function [AlexLayer,opts]=shouda_alexlayer()
inputLayer = imageInputLayer([227 227 3],'Name','Input');%指定图像大小227*227*3

middleLayers = [
convolution2dLayer([11 11], 96,'NumChannels',3,'Stride',4,'Name','conv1','Padding',0)
%过滤器的高度和宽度 过滤器数量（连接到同一输入区域的神经元数量 其决定了特征图的数量）
reluLayer('Name','relu1')
crossChannelNormalizationLayer(5,'Name','norm1')
maxPooling2dLayer(3, 'Stride', 2,'Name','pool1','Padding',0)

convolution2dLayer([5 5], 256, 'NumChannels',48,'Padding', 2,'Name','conv2','Stride',1)
reluLayer('Name','relu2')
crossChannelNormalizationLayer(5,'Name','norm2')
maxPooling2dLayer(3, 'Stride',2,'Name','pool2','Padding',0)

convolution2dLayer([3 3], 384, 'NumChannels',256,'Padding', 1,'Name','conv3','Stride',1)
reluLayer('Name','relu3')

convolution2dLayer([3 3], 384,'NumChannels',192, 'Padding', 1,'Name','conv4','Stride',1)
reluLayer('Name','relu4')

convolution2dLayer([3 3], 256, 'NumChannels',192,'Padding', 1,'Name','conv5','Stride',1)
reluLayer('Name','relu5')
maxPooling2dLayer(3, 'Stride',2,'Name','pool5','Padding',0)
];

finalLayers = [
fullyConnectedLayer(4096,'Name','fc6')
reluLayer('Name','relu6')
%caffe中有这一层
dropoutLayer(0.5,'Name','dropout6')

fullyConnectedLayer(4096,'Name','fc7')
reluLayer('Name','relu7')
%caffe中有这一层
dropoutLayer(0.5,'Name','dropout7')

%196种车
fullyConnectedLayer(61,'Name','fc8')
softmaxLayer('Name','softmax')
classificationLayer('Name','classification')
];

AlexLayer=[inputLayer
    middleLayers
    finalLayers];

opts = trainingOptions('sgdm', ...
    'Momentum', 0.9, ...
    'InitialLearnRate', 0.001, ...
    'LearnRateSchedule', 'piecewise', ...
    'MaxEpochs', 500, ...
    'MiniBatchSize', 100, ...
    'Verbose', true);
end