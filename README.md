# YX_Shop
## 悦享校园2.0
> 项目由原有的SSM框架升级为SpringBoot框架并重构代码,代码遵循阿里巴巴编码规约,引入全局异常处理机制和全局响应,集成Hibernate Validator对参数
> 进行校验,使用smart-doc实现多元化接口文档管理。
## 项目目录结构
```text
src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── o2o
│   │   │           └── shop
│   │   │               ├── api                             // api接口/controller层
│   │   │               │   └── v1                          // 版本号
│   │   │               ├── bo                              // 业务逻辑对象封装
│   │   │               ├── config                          // 配置文件相关
│   │   │               ├── constant                        // 全局常量配置
│   │   │               ├── dto                             // 数据传输对象，用于前端参数映射
│   │   │               ├── exception                       // 全局异常处理类
│   │   │               ├── interceptor                     // 拦截器处理类
│   │   │               ├── mapper                          // 数据库接口
│   │   │               ├── model                           // 数据库实体类
│   │   │               ├── service                         // 业务逻辑接口层及其实现类
│   │   │               │   └── impl
│   │   │               ├── util                            // 工具类
│   │   │               ├── vo                              // 全局数据返回对象
│   │   │               ├── util                            // 工具类
│   │   │               ├── validator                       // 校验器,分组校验配置
│   │   │               └── WineApplication.java            // 项目主启动类
│   │   └── resources                                       // 资源文件
│   │       ├── application-dev.yml                         // dev 开发环境
│   │       ├── application-prod.yml                        // prod 生产环境
│   │       ├── application-test.yml                        // test 测试环境
│   │       ├── application.yml                             // 主配置文件,根据active配置加载不同的环境,默认dev
│   │       ├── config
│   │       ├── DataSource                                  // 用于测试的数据库文件
│   │       ├── static
│   │       │   └── doc                                     // 接口文档输出目录               
│   │       ├── mapper                                      // 自定义mapper文件
│   │       └── templates
│   └── test                                                // 单元测试相关
├── .gitignore
└── pom.xml

```
### 技术架构
![image](https://camo.githubusercontent.com/6843ad76ab2a16da86ae262c6e973e843edbaf3a978346783675a53553b96b3b/68747470733a2f2f696d616765732e67697465652e636f6d2f75706c6f6164732f696d616765732f323032312f303830352f3039313131315f37383634353834335f353535383733302e706e67)

### 项目启动

> 下载代码到本地IDE后,确保本地JDK版本为8,依次打开src/main/java/com/o2o/shop下的shopApplication类，点击main函数行左侧run按钮即可启动，
> 默认加载resources文件下的application-dev.yml配置文件,端口为9094,如需加载其它文件,请在application.yml自行修改。
> 项目中使用了Redis作为缓存,会在加载部分信息时先验证Redis是否正常运行再进行读取,若未配置Redis则默认查询数据库。
> 在application.yml中可配置执行redis定时检查的时间周期,其结果作为某些使用Redis方法的前置校验条件,定时任务使用Quartz实现,
> 使用内存存储方式,非持久化存储。

### 简单示例

> src/main/java/com/o2o/shop/api/v1目录下的HelloController提供了一个简单的示例,使用Restful注解,即分别对应Get,Post,Put,Delete
> 除Get方法外， 其余三种接口访问请使用诸如Postman的工具进行测试。
> UserController 提供了一个基本的CRUD示例，可将src/main/resources/DataSource/user.sql文件导入本地数据库中进行调试。

### 日志输出

> 在添加@sl4j的类中可使用log方式打印日志，错误信息使用log.error,普通信息使用log.info。
> 项目中将对不同的日志划分处理，默认生成目录为src/logs文件夹。

### 参数校验

> 项目已集成参数校验,可在需要校验的字段上加上对应注解来确定参数合法性,错误的信息将被异常处理器捕获并返回。

### 全局异常

> src/main/java/com/o2o/shop/exception目录下具有全局异常处理器,当需要抛出异常时,请使用throw抛出BusinessException对象，相关的
> 异常参数位于ExceptionCodeEnum中,请尽量使用已定义错误码返回异常信息。

### 全局响应

> src/main/java/com/o2o/shop/vo目录下的ResultDataVO用于统一返回处理结果,其格式如下。
> 现已支持返回自定义的格式数据，并且无需显式调用success方法进行数据的返回。

```json
{
    "status": true,
    "code": 0,
    "message": "操作成功",
    "data": "Hello"
}
```
### 接口文档

> src/main/resources/static/doc目录下已经提供了一份html格式的接口文档，点击api.html在浏览器中访问呢即可。若需要其它格式的文档,请在IDEA
> 右侧的Maven选项卡中一次选择shop->Plugins->smart-doc,再更具需求进行双击需要生成的接口文档格式的选项即可。若需要接入Torna管理文档,请修改
> src/main/resources目录下的smart-doc.json文件进行相应的配置。
