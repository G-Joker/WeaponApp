# Gradle插件使用

### 命令
| 命令    | 作用 |
| :------- | ----: |
| gradle install | 安装应用 |
| gradle launch | 启动应用 | 
| gradle unistall | 卸载应用 |
| gradle clearData | 清除应用缓存 |
| gradle devices | 查看连接的设备数量|

### DSL

```
advancedOutput {
    fileOutputName  **
}
```
指定输出的apk的名称，格式为：

```
$customName-$versionCode-$versionName
```




