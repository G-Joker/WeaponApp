## 服务器

#### 使用方法
* 1.请安装Intellig，用Intellig打开Server项目
* 2.配置好jdk目录
* 3.安装mysql，并将src/main/resources/application.properties文件下的数据库改为您配置的数据库
* 4.tools目录下有个data.sql文件，里面是sql语句，可以用navicat一键导入数据库结构
* 5.修改根目录下的gradle.properties文件，设置开发还是发版，

当开发模式时，直接点击运行即可在浏览器输入http://localhost:8080/home/taobaolist 即可访问

当发版模式时，在Terminal中输入gradlew assemble打包，在build/libs/下生成Server-0.0.1-SNAPSHOT.war ,

将这个war包拷贝到tomcat的webapps下，运行tomcat，即可在浏览器输入http://localhost:8080/home/taobaolist 即可访问

#### 目录结构

**doc目录**  一些文档说明

**tools目录** 一些工具

**src目录**

* controller 
控制器，类名用注解@RestController，里面每个方法用@RequestMapping，一个方法代表一个请求

* mapper 
数据库映射，每个方法代表一种数据库操作

* model 
实体类

* request
服务器发起http请求，用RestTemplate，这个相当于retrofit

* timer
定时任务，方法用@Scheduled代表定时

* util
工具类

* BaseApplication和ServletInitializer
启动类

* resources
资源目录

* application.properties
配置文件，目前主要配置数据库

#### 程序执行顺序
作为Android程序员，我以Android的思维讲解
* 1.BaseApplication为程序入口，相当于Application
* 2.controller目录下以@RestController注解的类会自动启动，不需要手动调用，它不断监听@RequestMapping注解的方法，当客户端有请求时，即会调用,相当于Socket
* 3.mapper目录下@Select类似注解的方法会执行数据库操作，需要手动调用，完全跟sql语句一致，相当于sqlite
* 4.timer目录下以@Scheduled注解的方法会自动定时执行,不需要手动调用，相当于timer
* 5.request目录下RestTemplateController是个单例网络请求工具，需要手动调用，可以让服务器发起http请求，相当于Retrofit



