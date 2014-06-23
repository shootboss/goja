# coding=utf-8
import os
import shutil
import errno
import sys
import re

from jinja2 import Environment, FileSystemLoader


__author__ = 'yfyang'


def storage_file(storage_path, content, file_name):
    output = open(storage_path + os.sep + file_name, 'wb')
    output.write(content)
    output.close()


def copy_directory(source_dir, dest_dir):
    try:
        shutil.copytree(source_dir, dest_dir)
    except OSError as exc:  # python >2.5
        if exc.errno == errno.ENOTDIR:
            shutil.copy(source_dir, dest_dir)
        else:
            raise


def read_conf(app_dir):
    """
        读取项目配置信息，也就是 application.conf文件
    :param app_dir: 项目所在目录
    """
    # 1. get project name with application.conf
    app_config_file = os.path.join(app_dir, 'src', 'main', 'resources', 'application.conf')

    if not os.path.isfile(app_config_file):
        print '%s does not seem an effective jfinal application.' % app_dir
        sys.exit()

    config = {}

    app_config = file(app_config_file, 'rU')
    for prop_line in app_config:
        prop_def = prop_line.strip()
        if len(prop_def) == 0:
            continue
        if prop_def[0] in ('!', '#'):
            continue
        punctuation = [prop_def.find(c) for c in ':= '] + [len(prop_def)]
        found = min([pos for pos in punctuation if pos != -1])
        prop_name = prop_def[:found].rstrip()
        prop_val = prop_def[found:].lstrip(":= ").rstrip()
        config[prop_name] = prop_val

    return config


def underline_to_camel(s_name):
    """
        下划线命名格式驼峰命名格式
    """
    return re.sub(r'(?!^)_([a-zA-Z])', lambda m: m.group(1).upper(), s_name)


def render_config_file(template_name, kwargs, gen_path):
    env = Environment(
        # loader是加载器类，用来加载模板文件。
        loader=FileSystemLoader(os.path.join(gen_path, 'templates')),
        auto_reload=True,  # 自动重载，调试用
        # 还有许多参数，例如缓存大小，详细见jinja2文档
    )
    # 创建一个template对象。
    template = env.get_template(template_name)
    # 进行渲染，返回HTML字符串。
    return template.render(kwargs)
