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
