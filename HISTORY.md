## 0.1 (2014-11-09)

* 增加对`logback`的上下文监听事件；
* 修复如果 `logback.xml` 文件不存在，会出现`NullPoint`问题；
* 修复 `render('/view.ftl')`时，视图文件路径不正确的问题；
* 补充 `Controller` 中对`AjaxMessage`的处理；
* 升级`jFinal`为`1.9`版本；
* 增加`Controller`中对JSON参数转换为`Model`和Rquest中直接封装为`Model`的方法；详见`goja.mvc.Controller#getModelByJson`和`goja.mvc.Controller#getModelByRequest`;
* 增加对`Model`的测试用例;
* 修复一些BUG问题。

## 0.0.10 (2014-10-01)

* 增加类似`PlayFramework`的任务机制；
* 优化基础工具类；
* 调整相关代码，按模块重新规划；
* 对于`Freemarker`视图，自动增加模版继承的指令；
* 增加日志文件的配置，同时去掉对`logback.xml`的必须使用的支持，详见 `goja.Logger`;
* 增加对`Lucene`的支持
