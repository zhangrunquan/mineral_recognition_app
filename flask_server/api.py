#encoding=utf-8
#import sys
## 使得 sys.getdefaultencoding() 的值为 'utf-8'
#reload(sys)                      # reload 才能调用 setdefaultencoding 方法
#sys.setdefaultencoding('utf-8')  # 设置 'utf-8'
"""定义jsonAPI"""
import base64

from flask import (
    Blueprint, flash, g, redirect, render_template, request, url_for
)

from info import Final
from flask import jsonify


bp = Blueprint('api', __name__)



@bp.route('/api/upload/json',methods = ['POST'])
def upload_json():
    string=request.form.get('content')
    import json
    decoder=json.JSONDecoder()
    print(string)
    dic=decoder.decode(string) # dic是提交的json解析生成的字典
    thelist=Final().get_answer(dic)
    print(thelist)
    if thelist==[]:
        return jsonify({'type':'notexist'})
    else:
        return jsonify({'type':thelist[0]})
    

@bp.route('/api/upload/image',methods = ['POST'])
def upload_image():
    undecoded_filename='undecoded.jpg'
    decoded_filename='decoded.jpg'
    file=request.files['image']
    
    if file:
        file.save(undecoded_filename)

    with open(undecoded_filename,'r') as f:
        string=f.read()
        decoded=base64.b64decode(string)
        with open(decoded_filename,'wb') as f2:
            f2.write(decoded) # f2是正常的jpg文件

    return jsonify({'type':'赤铁矿'}) 
    
