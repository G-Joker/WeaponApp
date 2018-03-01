# WeaponApp

### 联系我们
**开发人员**：*WeaponZhi，xiaweizi，hiyangyue，foxlee*

AI极客研修站-作者WeaponZhi的公众号欢迎关注~
![image.png](http://upload-images.jianshu.io/upload_images/3363394-a9f2619313e5a8e5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

[qqgroupsvg]: https://img.shields.io/badge/QQ群-559169318-ff73a3.svg
[qq1group]: https://jq.qq.com/?_wv=1027&k=5WqyE5X
[![QQ1Group][qqgroupsvg]][qq1group]

>**一个极致的App**。学习项目，帮助你提升独自开发 `Android` 业务层的能力。基于该开发框架，可以快速构建应用，同时你可以当做一个工具 App，以供你平时开发参考。

整包打包方式：

```
gradlew cleanLib
gradlew buildLib -q -Dbundle.arch=x86
gradlew cleanBundle
gradlew buildBundle -q -Dbundle.arch=x86
```
其中 -q 代表整洁模式，只看到插件的打包结果信息。

-Dbundle.arch 中，如果是模拟器调试，则是`x86`，如果是真机调试则是`armeabi`

**如遇真机启动奔溃**

请先检查`Logcat`下的日志，并定位到对应的错误。若无明显的错误日志，或者是`PushReceiver ClassNotFoundException`的错误，请先确定真机的`CPU`的类型，并根据类型进行相应的打包。

通过
```
adb shell
cat /proc/cpuinfo  
```
的方式获取`CPU`类型，比如红米 note 3：
```
Processor	: AArch64 Processor rev 2 (aarch64)
```
对应的打包则更换为
```
#so版， 区分CPU架构，一般模拟器使用 x86，真机使用 armabi armeabi-v7a arm64-v8a
gradlew buildLib -q -Dbundle.arch=arm64-v8a
gradlew buildBundle -q -Dbundle.arch=arm64-v8a
```

脚本打包方式：

```
buildAll        全量打包
buildBundle     只打包Bundle，Lib没改变
buildAll q d    相当于加-q 和 -Dbundle.arch=x86
buildBundle q d 相当于加-q 和 -Dbundle.arch=x86
```

打包如果出现异常，可以先输入 gradlew cleanBuild，再重新执行一遍打包流程
详细打包指令参数请参考`Small`文档:[Small文档](http://code.wequick.net/Small/cn/compile-options)

### 开发方法：**测试驱动开发**

它要求在编写某个功能的代码之前先编写测试代码，然后只编写使测试通过的功能代码，通过测试来推动整个开发的进行。这有助于编写简洁可用和高质量的代码，并加速开发过程。

**测试驱动开发会反向帮助程序员编写设计性良好的代码，为了保证代码的可测性，要求代码中的类和方法必须严格遵循单一性原则。**

同时，通过已经封装好的 MVVM 框架，可以只通过**单元测试** ViewModel 层方法，构造空的 View 和 Model 参数，从而测试整个页面逻辑。

### 开发框架：**MVVM**

通过高度封装的 MVVM 框架，配合`databinding`进行高效开发，通过该框架，可以快速的进行业务逻辑的开发，对 databinding 底层技术进行隐藏，即使程序员对 MVVM 和 Databinding 理解不深，按照框架流程也可以顺畅开发。

并且通过该框架，可以轻松的进行单元测试，MVVM 的具体开发流程和单元测试操作，将在后续开发过程中补充。

### 技术栈

我们将在项目中集成、封装和二次开发现在 Android 领域使用最多，最专业，最热门的技术，并实用在项目中，目前计划引入的技术有(加粗字体为二次开发或者完全自主开发库)：

- [x] DataBinding+MVVM开发
- [x] **组件化开发**
- [x] Small 插件化框架
- [x] **Databinding**
- [x] **自建服务器**
- [x] **自定义 Gradle 插件**
- [x] **集成QQ、微信、微博官方分享**
- [x] **全平台推送服务**
- [x] **友盟统计**
- [x] OkHttp + Retrofit + RxJava
- [x] **通过 RecyclerView 拓展的刷新列表库**
- [ ] **控件组件化**
- [ ] **单元测试**
- [x] **组件化集成调试**
- [x] **即时通讯**
- [x] 视频服务
- [x] Material Design
- [x] Glide

>注：首页数据样式参考于慕课网组件化视频 [https://coding.imooc.com/learn/list/84.html](https://coding.imooc.com/learn/list/84.html) 使用项目MVVM重新实现
>感谢 qndroid 老师

### 提交规范

* **[func] do sth**：功能开发
* **[fixbug] #xx# do sth**：Bug 修改，#号内为 issue 编号
* **[docs] do sth**：文档
* **[chore] do sth**：build相关的修改
* **[test] do sth**：添加测试代码
* **[optimize] do sth**: 优化部分代码
* **[style] do sth**: 格式化上的格式化、删除空白行等，无关功能

### 集成调试方法
在 gradle.properties 中 DEBUG 为 true 则打开集成调试环境，需要哪几个模块联调，就把下面的对应模块置为 true。

比如我要联调 mine 和 message 模块，就把`DEBUG`、`DEVELOP_MINE`和`DEVELOP_MESSAGE`置为true，然后重新同步一下Gradle即可

DEBUG 置为 false 的时候为整包编译或者可以理解为Release模式，将默认添加所有模块，忽略下面模块的单独配置布尔值。

### 单元测试设计思路与使用方法
待补充

### 待补充
>未完待续，目前正迭代中...



