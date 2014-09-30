# Goja

> 感谢：[@JFinal](http://git.oschina.net/jfinal/jfinal) [@JFinal-ext](http://git.oschina.net/zhouleib1412/jfinal-ext)

1. 在 `JFinal`基础上，参考`Play 1.2`配置方式，将大部分的配置整合到一个配置文件，并支持动态启动相关插件等！
2. 需要使用`JDK-1.6` 以及 `Servlet 3.0` 以上版本
3. 通过`javax.servlet.ServletContainerInitializer`(需要`Servlet3.0`以上容器)的方式去掉了`web.xml`的配置


## 一、`JFgen`（推荐）

一个为了方便使用idea和不喜欢`Maven`的脚本工具。可点击[这里](http://www.kuaipan.cn/file/id_8331287366505665.htm)进行下载。

	$ jfgen help
	~
	~ JFinal App gen.
	~ Usage: jfgen cmd application_name [-options]
	~
	~ with,  new      Create a new application
	~        war      Export the application as a standalone WAR archive
	~        help     Show jfgen help
	

## 更多信息

可以点击 [JFinal-app GitHub Wiki](https://github.com/sogyf/jfinal-app/wiki) 查看更详细的信息。

## 示例

[jfinal-app-example](https://github.com/sogyf/jfinal-app-example)
