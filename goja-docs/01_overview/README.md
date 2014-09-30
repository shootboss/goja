# 框架概览

核心：[jfinal](http://www.jfinal.com)

> JFinal 是基于 Java 语言的极速 WEB + ORM 框架，其核心设计目标是开发迅速、代码量少、学习简单、功能强大、轻量级、易扩展、Restful。

`japp`核心就是`jFinal`，只是在`jfinal`的基础上，为常见的插件、组件以及相关代码和组织习惯而设定的一个WEB框架。

`Jfinal`非常优秀，很好的插件体系，良好、简单的代码设计、MVC轻巧的构思、Java中应该是最方面的`ActiveRecord`数据库操作等等优点，但是由于之前我对`PlayFramework`的使用经历，感觉`jfinal`对于其配置方式，采用了比较宽松自由的方式来处理，只需要继承`JfinalConfig`类，设定路由、控制器、插件等；

在这一点，我由于受`PlayFramework`影响较深，感觉对于过多的暴露出配置方法和项目，对于团队开发的沟通起来，比较费时费力，如果参考`PlayFramework`的配置方法，只需要将配置项说明沟通好，就一切ok了！（当然，是否真的如此，还需要看团队吧）。

由于这样的原因，就用了`japp`，为啥叫japp呢？ `japp＝Jfinal Application`，其含义一目了然了，就是在Jfinal的基础上，为应用本身提供一个脚手架的基础架构。

