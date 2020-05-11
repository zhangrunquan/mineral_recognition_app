# -*- coding: utf-8 -*-
import re

#import sys
## 使得 sys.getdefaultencoding() 的值为 'utf-8'
#reload(sys)                      # reload 才能调用 setdefaultencoding 方法
#sys.setdefaultencoding('utf-8')  # 设置 'utf-8'



class Field:
    def satisfy(self, value):
        raise NotImplementedError()

class StringField(Field):
    def __init__(self, value):
        self.value = str(value)
        self.l = re.split('、', self.value)

    def satisfy(self, value):
        if (self.l[0] in ['请选择','nan','无','',' ']) or (str(value) in ['请选择','nan','无','',' ']):#若没有数据或没有输入
            return True
        else:
            if value[0]=='[':
                self.v=eval(value)
            else:
                self.v = re.split('、', str(value))
            if len(self.v)==1:
                return self.v[0] in self.l#原字符串==输入
            else:
                return str(value)==self.value


class NumricField(Field):
    def __init__(self, value):
        self.value = str(value)
        self.l = re.split('~|-', self.value)

        self.length = len(self.l)
        if self.length not in {1, 2}:
            raise ValueError('invalid numric field:{}'.format(self.l))

    def satisfy(self, value):#输入是否符合条件
#         print(self.l[0],str(value))
#         print(float(self.l[0]),float(value),float(self.l[1]))
        thel=re.split('~|-', value)
        thelength = len(thel)
        if thelength not in {1, 2}:
            raise ValueError('invalid numric field:{}'.format(thel))
        if (self.l[0] in ['请选择','nan','无','',' ']) or (str(value) in ['请选择','nan','无','',' ']):#若没有数据或没有输入
#             print('aaaa')
            return True
        if self.length == 1:
            if thelength==1:
                return self.value == value
            else:
                return float(thel[0]) <= float(self.value) <= float(thel[1])
        if self.length == 2:
            
#             print(float(self.l[0]) <= float(value) <= float(self.l[1]))
            if thelength==1:
                return float(self.l[0]) <= float(value) <= float(self.l[1])
            else:
                return (float(thel[0]) >= float(self.l[0])) and (float(thel[1]) <= float(self.l[1]))


class Source:
    def __next__(self):
        """return a dict like rule"""
        raise NotImplementedError


class PandasSource(Source):
    def __init__(self, filepath):
        import pandas as pd
        self.df = pd.read_excel(filepath)
        self.length = len(self.df)#一共有几条数据
        self.ind = -1

    def __next__(self):
        self.ind += 1
        if self.ind < self.length:
            return self.df.iloc[self.ind]#执行__next__获取下一横条数据
        else:
            raise StopIteration

    def __iter__(self):#不知道什么意思
        return self


class RecordChecker:
    """check how much is a rule satisfied"""

    def __init__(self, result_field_name, **kwargs):
        """

        :param kwargs: arg names should be name of subclass of `Field` ,
                values should be sets of strings, which are record keys ,
                e.g. : RecordChecker('矿物名称',StringField=set('条纹','解理'))
        """
        self.result_field_name = result_field_name

        for k, v in kwargs.items():
            if not isinstance(v, set):
                raise ValueError('arg is not a set:{}'.format(k))

        self.fields = kwargs
        print(kwargs)#{'NumricField': {'比重', '硬度'}, 'StringField': {'光泽', '形态', '断口', '晶面条纹', '解理', '颜色', '条痕', '透明度'}}
        print(result_field_name)#矿物名称

    def score(self, record, target):
        """return a score indicating how much a rule is statisfied

        :param record: a rule, dict like
        :param target: expert system's input

        """
        score = 0  # how many field is satisfied

        for cls_name, key_set in self.fields.items():
            # 获取field对应的类
            cls = globals()[cls_name]
            for k in key_set:
                field = cls(record[k])
                if field.satisfy(target[k]):
                    score += 1
        return score

class Final:
    def __init__(self):
        import pandas as pd
        self.filepath = '矿物物理属性(3).xlsx'
        self.df = pd.read_excel(self.filepath)

    def initinpt(self,raw):#dict raw
        #{"fromToMap":{},"multiValueMap":{},"singleValueMap":{"弹性或挠性":"请选择","磁性":"请选择","滑腻感":"请选择","形态":
        #"","发光性":"请选择","比重":"2.7-2.8","条痕":"","染手":"请选择","透明度":"请选择","硬度":"2.5"}}
        numric = {'硬度', '比重'}
        desc = {'颜色', '形态', '条痕', '光泽', '透明度', '解理', '断口', '弹性或挠性','滑腻感','发光性','磁性' }
        inpt = self.df.iloc[0]
        inpt['硬度'] = ""
        inpt['比重'] = ""
        for i in desc:
            inpt[i]=""
        for big_category,_ in raw.items():
            for k,v in raw[big_category].items():
                inpt[k]=str(v)
        return inpt
    def get_answer(self,raw):
        numric = {'硬度', '比重'}
        desc = {'颜色', '形态', '条痕', '光泽', '透明度', '解理', '断口', '弹性或挠性','滑腻感','发光性','磁性' }
        accepted = []
        record_checker = RecordChecker('矿物名称', NumricField=numric, StringField=desc)
        init=self.initinpt(raw)
        for rule in PandasSource(self.filepath):
            if record_checker.score(rule, target=init) >=len(numric)+len(desc):
                accepted.append(rule['矿物名称'])

        return(accepted)
