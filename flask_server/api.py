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
    dic=decoder.decode(string)
    return jsonify({'type':dic['content']}) # 返回上传的内容
    

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
            f2.write(decoded)

    return jsonify({'type':'test return'}) # 返回上传的内容
    
