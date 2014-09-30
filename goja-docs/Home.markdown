# JFinal-App 
> 感谢：[@JFinal](http://git.oschina.net/jfinal/jfinal) [@JFinal-ext](http://git.oschina.net/zhouleib1412/jfinal-ext)

1. 在 `JFinal`基础上，参考`Play 1.2`配置方式，将大部分的配置整合到一个配置文件，并支持动态启动相关插件等；
2. 需要使用`JDK-1.7` 以及 支持`Servlet 3.0`以上版本的Web容器；
3. 通过`javax.servlet.ServletContainerInitializer`(需要`Servlet3.0`以上容器)的方式去掉了`web.xml`的配置；
4. 通过`jfgen`工具支持那些不使用`Maven`的开发人员；
5. 集成常用的工具包比如`Google Guava`等，并使用常用函数的示例。

## JFGen

因为，我们这边存在部分团队不使用`Maven`的项目，故此通过开发这个脚本工具创建`jfgen`，学习`playframework`的方式开发这个脚本工具，用于创建`jfinal-app`的项目，并默认创建`idea`的项目文件。

 1. 安装
 	
 	- 安装`python`，并配置`python`的环境变量；
 	- 点击 [jfgen.7z](http://www.kuaipan.cn/file/id_8331287366505665.htm) 下载
 		
 		> 历史版本:
 		>
 		> [jfgen_0.2](http://www.kuaipan.cn/file/id_8331287366505665.htm) 
 		>
 		>> 集成Ant 进行打包 		
 		>
 		> [jfgen_0.1](http://www.kuaipan.cn/file/id_8331287366505666.htm)

 	- 解压到某个路径，比如 `/opt/develop/jfgen`
 	- 将`/opt/develop/jfgen`配置到`PATH`中
 	- 执行`jfgen help`

		~
		~ JFinal App gen.
		~ Usage: jfgen cmd application_name [-options]
		~
		~ with,  new      Create a new application
		~        war      Export the application as a standalone WAR archive
		~        help     Show jfgen help
		~

 2. 创建一个项目
 	> 示例：创建一个名称为 example的项目
 	
 	- 使用终端/cmd窗口，进入项目空间文件夹
 		
 		`cd ~/Workthing/`
 		
 		> `Workthing`是我的项目空间目录
 	
 	- 执行 `jfgen example`
 		
			$ jfgen new example
			example project create Suceess! 

 3. 打包（先说了）
 	
		$ cd example
		$ pwd
		/Users/yfyang/Workthing/example
		$ jfgen war
		$ tree out
		out
		├── ant
		│   └── classes
		│       ├── app
		│       │   └── controllers
		│       │       └── IndexController.class
		│       ├── application.conf
		│       ├── ehcache.xml
		│       ├── logback.xml
		│       └── sqlcnf
		└── dist
		    └── 0.1
		        └── example.war

	在`out/dist`目录下可找到已经打好的war包
 
 4. 启动 `IntelliJ IDEA ` 或者直接双击 `Workthing/example`目录下的 `example.ipr`
 5. 选择 `Open Project`，选择`Workthing/example`目录下的 `example.ipr`
  (可选)
  
  	打开后如下图所示：
  	
  ![image](http://sogyf.github.io/images/jfinal/example_layout.png)
  
 6. 输入`Ctrl`+`Alt`+ `D`(运行配置)(Window下`Alt`+`Shift`+`F9`),如下所示：
  
  	![image](http://sogyf.github.io/images/jfinal/Run_Config.png)
 
 7. 如果`Tomcat`为配置好，那么这个地方会出现错误，只要配置好Tomcat配置好即可。如下所示：
 
 	![image](http://sogyf.github.io/images/jfinal/Tomcat_Config.png) 

 8. 如果`Tomcat`配置好的话，直接`Run`，启动后，访问[http://localhost:8080/example](http://localhost:8080/example),如下所示：
  
  ![image](http://sogyf.github.io/images/jfinal/Tomcat_Access.png)

## JFinal-App 应用

[JFinal](http://git.oschina.net/jfinal/jfinal)是基于`Java` 语言的极速`WEB + ORM`开发框架。更多介绍可参考[这里](http://www.oschina.net/p/jfinal)。

1. `Controller`介绍
	在`app/controllers`包下，建立以`{Path}Controller`为后缀的`Java`类,同时集成`BasicController`即可，当然集成`JFinal`的`Controller`也可以。`JFinal-App`以`{Path}`转成小写为系统访问的路径为默认的路由方式
	
	比如`IndexController`对应的访问为`http://localhost:8080/example/index`访问到。具体默认`Controller`的路由方式参考`JFinal`的方式。
	
	`Jfinal-app`在`Jfinal`的基础上，增加了`renderXml`的请求`XML`格式的方法。
	
2. `Model`
3. `Interceptor`
	
	1. 全局拦截器
		
		1. 新建Java类，
		2. 实现`com.jfinal.aop.Interceptor`
		2. 在类增加注解`com.jfinal.sog.interceptor.autoscan.AppInterceptor`
		
		框架会自动扫描注解为`com.jfinal.sog.interceptor.autoscan.AppInterceptor`的拦截器为全局拦截器.
		
	2. 单个拦截器的使用
		
		1. 新建Java类,比如`ConditionInceptor.java`，
		2. 实现`com.jfinal.aop.Interceptor`
		3. 在需要拦截的方法或者类上增加 `@Before({TopicConditionInceptor.class})`
		
4. `*-sql.xml` SQL管理说明

	如果`application.conf`配置了`db.sqlinxml`且启动了数据库`db.url`的话，那么表示启用了XML管理SQL的功能。
	
	约定文件名必须为`-sql.xml`结尾的配置文件。如上面的`model-sql.xml`.
	
	内容示例：
			
			<sqlGroup name="topic">
				<sql id="categorys">
        			select id, name, description
        			from rt_category
    			</sql>
    			<sql id="updateOnline">
        			UPDATE rm_member SET online=?,this_login_time=?,this_login_ip=? WHERE id=?
    			</sql>
			</sqlGroup>
	
	在代码中获取 `UPDATE rm_member SET online=?,this_login_time=?,this_login_ip=? WHERE id=?`可通过如下代码：
	
			SqlKit.sql("topic.updateOnline")
	
	约定为：`sqlGroup@name.sql@id`


## Cache 使用
	
1. `ehcache.xml` 配置说明
		
	![image](http://sogyf.github.io/images/framework/Ehcache.png)
		
2. `Model`的中`Cache`使用
		
	- 在`ehcache.xml`中增加缓存配置：
			
		    <cache name="cacheName"
             maxElementsInMemory="100"
             eternal="false"
             timeToIdleSeconds="1800"
             timeToLiveSeconds="1800"
             overflowToDisk="true"
             />
       
	- 在`Model`中，使用类似如下代码:
		
			 public List<HeartRule> findAllRule() {
        	  return findByCache("cacheName", "cacheName",
                "select id,comment_max,style_name from rt_hotrule order by comment_max");
            }
		
3. `CacheKit`的使用
		
	CacheKit手动方式管理和获取Cache，当然也需要和在Model中配置缓存一样，需要在`ehcache.xml`中配置缓存配置
	
		 List<HeartRule> tops = CacheKit.get("cacheNameP");
		

## Job 使用

1. 在`app.jobs`目录下新建以及集成`org.quartz.Job`的Java类，比如`TestJob`
2. 在Java类上增加注解`com.jfinal.ext.plugin.quartz.On`注解，系统会自动进行扫描（当然在`application.conf`中配置了`job=true`）
3. `com.jfinal.ext.plugin.quartz.On`说明：
	- `name` - Job名称，默认为`job`
	- `value` - Job的`Cron`表达式，[这里有个GUI程序](https://github.com/wjw465150/CronExpBuilder)，可生成cron表达式；
	- `enabled` - 是否启动这个JOb，默认是`true`

## MongoDB的使用

## Redis使用

## 常用工具使用

1. [AppFuncTest.java](https://github.com/sogyf/jfinal-app/blob/master/jfinal-app/src/test/java/com/jfinal/sog/kit/cst/AppFuncTest.java)
2. [FnKitTest.java](https://github.com/sogyf/jfinal-app/blob/master/jfinal-app/src/test/java/com/jfinal/sog/kit/FnKitTest.java)

3. 

## 异步任务

## 授权登录工具介绍
