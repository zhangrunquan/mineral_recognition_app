import os
class Tools():
    @staticmethod
    def get_search_url(keyword):
        return 'https://www.mindat.org/photoscroll.php?frm_id=pscroll&cform_is_valid=1&searchbox={keyword}&submit_pscroll=Search'.format(keyword=keyword)
    @staticmethod
    def create_save_dir(path):
        if(not os.path.exists(path)):
            os.makedirs(path)
    @staticmethod
    def removeDir(dirPath):
        if not os.path.isdir(dirPath):
            return
        files = os.listdir(dirPath)
        for file in files:
            filePath = os.path.join(dirPath,file)
            if os.path.isfile(filePath):
                os.remove(filePath)
            elif os.path.isdir(filePath):
                    Tools.removeDir(filePath)
        os.rmdir(dirPath)
    @staticmethod
    def read_list(filepath): 
        with open(filepath,'r',encoding='utf8') as f:
            s=f.read()
            return s.split('\n')


class MetaInfo():
    initial_img_num=50 # 搜索后页面上的图片数
    increase_num=15 #每次下拉到底新增图片数
    save_dir='./photos/' #输出文件夹
    label_file_path='./label.txt' #关键词文件
    log_file_path='./log.txt'