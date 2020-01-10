""""""
import re


class Field:
    def satisfy(self, value):
        raise NotImplementedError()


class StringField(Field):
    def __init__(self, value):
        self.value = str(value)

    def satisfy(self, value):
        return self.value == str(value)


class NumricField(Field):
    def __init__(self, value):
        self.value = str(value)
        self.l = re.split('~|-', self.value)

        self.length = len(self.l)
        if self.length not in {1, 2}:
            raise ValueError('invalid numric field:{}'.format(l))

    def satisfy(self, value):
        if self.length == 1:
            return self.value == value
        if self.length == 2:
            return float(self.l[0]) <= float(value) <= float(self.l[1])


class Source:
    def __next__(self):
        """return a dict like rule"""
        raise NotImplementedError


class PandasSource(Source):
    def __init__(self, filepath):
        import pandas as pd
        self.df = pd.read_csv(filepath)
        self.length = len(self.df)
        self.ind = -1

    def __next__(self):
        self.ind += 1
        if self.ind < self.length:
            return self.df.iloc[self.ind]
        else:
            raise StopIteration

    def __iter__(self):
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


if __name__ == "__main__":
    import pandas as pd

    filepath = '矿物物理属性.csv'

    df = pd.read_csv(filepath)
    inpt = df.iloc[0]
    inpt['硬度'] = '2.5'
    inpt['比重'] = 2.8

    accepted = []

    numric = {'硬度', '比重'}
    desc = {'颜色', '形态', '条痕', '光泽', '透明度', '解理', '断口', '晶面条纹', '其它', }
    record_checker = RecordChecker('矿物名称', NumricField=numric, StringField=desc)

    for rule in PandasSource(filepath):
        if record_checker.score(rule, target=inpt) >= 11:
            accepted.append(rule['矿物名称'])

    print(accepted)
