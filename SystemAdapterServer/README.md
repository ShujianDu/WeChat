### application.conf中systemAdapter.server.routeClasses配置路由class

### 访问规则示例：
##### routeClasses中存在com.yada.system.adapter.route.bcsp.SendSMS配置。
##### 则访问bcsp/SendSMS即可访问到SendSMS类
 
 
### 路由实现在route包下按照访问的系统区分报名。
### 一个路由实现对应一个system.adapter.service中的一个接口。