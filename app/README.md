# App实现简单说明

## 1.专家系统部分
### 主要任务
#### 界面方面
主要涉及单选框(checkbox),下拉条(spinner),输入框(EditText),文字(textView)四种元素,并需要将四种元素嵌入到一个Recycler中,Recycler作为容器元素,使得内容在屏幕内放不下时可以上下滑动.
#### 逻辑方面
维护界面元素的点击,选中等事件的处理.维护一个数据结构来存储选中的属性信息,并能导出为json准备发往后端.

### 完成情况
#### 已完成部分
EsActivity目前是一个可运行的示例(log中有展示性的输出),所有种类的View都实现了至少一个,数据处理逻辑已完成,
#### 待完成 20/1/11
* 添加设计中的所有属性(颜色,形态...)
* 调整样式
* 提交按钮,页面跳转逻辑等

### 实现介绍
界面实现在EsActivity中,逻辑依靠的核心类为EsAdapter和ViewData及其子类,以及EsContent类
#### EsAdapter类
Recycler做为容器View,其中子元素的加载和显示需要通过Adapter来指定,EsAdapter中定义了一个子元素需要渲染时需要如何处理
#### ViewData类
封装了显示一个View需要的所有信息,例如,一个CheckBoxData包括了:显示的信息(片状)),所属字段(形态)等,构建EsAdapter时需要传递ViewData的List,Adapter需要渲染子元素时,从list中取得ViewData,根据其提供的信息渲染某种View
#### EsContent类
维护页面上所有的信息,元素在点击等事件中调用其add(),remove()方法,可导出适于用Gson生成Json的对象

## 2.矿物信息管理
### 主要任务
前端需要显示矿物的信息,图片等,需要将这些集成到app的资源中并能方便的加载

### 完成情况
#### 已完成部分
实现了一个简单示例(MineInfoActivity),目前可以将资源组织到json中,并方便的加载
#### 待完成 20/1/12
* 将全部资源整理成json,图片放入相应文件夹
* 自定义key到MineInfo的映射

### 实现介绍
核心类为MineInfo和MineInfoManager
#### Mineinfo类
封装了矿物的信息,json中的内容会被加载到该类的同名字段中,数组类型可以正确映射到List中.
#### MineInfoManager
调用其getMineInfo(key)方法,即返回对应的MineInfo对象,key与MineInfo的对应关系可以修改load函数来自定义
#### 资源组织
示例json和图片位于assets文件夹下,已给出显示图片的示例

## 3.网络请求
### 主要任务
向后端发送图片和json

### 完成情况
#### 已完成部分
实现了示例(MainAcitivity),示例包括从相机或文件系统获得图片,并将图片显示,并上传文件到服务器,完成了测试用flask服务器

### 实现介绍
主要依赖Retrofit,无需关注实现,仿照例子使用即可


## 4.显示图片
#### 已完成部分
实现了示例(MainActivity),参照示例即可

