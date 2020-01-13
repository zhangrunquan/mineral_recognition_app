"""定义jsonAPI"""
import base64

from flask import (
    Blueprint, flash, g, redirect, render_template, request, url_for
)


from flask import jsonify


bp = Blueprint('api', __name__)



@bp.route('/api/upload/json',methods = ['POST'])
def upload_json():
    string=request.form.get('content')
    import json
    decoder=json.JSONDecoder()
    dic=decoder.decode(string) # dic是提交的json解析生成的字典
    return jsonify({'type':'crystal'})
    

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

    return jsonify({'type':'crystal'}) 
    
