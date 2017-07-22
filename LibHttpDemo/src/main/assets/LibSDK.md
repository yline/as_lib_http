## 代码管理：[git_manager.xmind](https://github.com/yline/as_lib_sdk)
> 就在该工程内，文件名为：git_manager.xmind  
> 它记录了github上源码的结构，便于捋顺代码的关联
如图：
![git_manager](https://github.com/yline/as_lib_sdk/blob/master/xmind_show.jpg)

## LibSDK
`LibSDK` 是一个Android开发基类库，提供一些基础功能；主要功能分为：

* 通过Application，提供帮助类，例如：Toast、Handler、管理Activity功能
* BaseActivity等基类，例如：BaseActivity
* 日志帮助类，例如：LogFileUtil、CrashHandler(将工程异常抛出)
* test基类，并存放一些测试常量
* 常用Util集合

技术交流群：[547839514](https://jq.qq.com/?_wv=1027&k=4B0yi1n)  
个人邮箱：[957339173@qq.com](https://jq.qq.com/?_wv=1027&k=4B0yi1n)  
我的微博：[csdn.com/yline](http://blog.csdn.net/u014803950)  

## 依赖
* Gradle：
```compile 'com.yline.lib:LibSDK:2.0.2'```
* Maven:
```
    <dependency>
       <groupId>com.yline.lib</groupId>
       <artifactId>LibSDK</artifactId>
       <version>2.0.2</version>
       <type>pom</type>
    </dependency>
```
* Eclipse 请放弃治疗。

## 权限
```<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>```

## 使用教程
### 使用前提：初始化
AndroidManifest.xml中：(三选一)

1，直接指定Application `android:name="com.yline.application.BaseApplication"`

2，相应的Application 继承 BaseApplication, `MainApplication extends BaseApplication`

3，相应的Application 实现 `SDKManager.init(this, new SDKConfig());`

`LibSDK`主要功能分为四部分：下面分别举例说明。

### Toast
`SDKManager.toast("我是吐司");` 或者 `BaseApplication.toast("我是吐司");`

### 全局提供Application
`SDKManager.getApplication()` 或者 `BaseApplication.getApplication()`

### Handler
```java
SDKManager.getHandler().postDelayed(new Runnable()
{
	@Override
	public void run()
	{
		// 在UI线程上执行		
	}
}, 1000);
```

### 日志
* 默认本地保存路径：`Android/data/包名/`
* 日志过滤`xxx-`
* 仅打印到屏幕`LogUtil.v("content");`
* 打印到屏幕并且本地记录下`LogFileUtil.v("content");`

例如：

    07-17 16:25:40.139 27445-27445/com.hokol V/xxx->SDKManager$1.handleMessage(L:68):: LibSDK -> this time = 1500279940148,this thread = 1
    07-17 16:27:40.149 27445-27445/com.hokol V/xxx->SDKManager$1.handleMessage(L:68):: LibSDK -> this time = 1500280060156,this thread = 1
    07-17 16:29:40.159 27445-27445/com.hokol V/xxx->SDKManager$1.handleMessage(L:68):: LibSDK -> this time = 1500280180164,this thread = 1

### 常用基类
仅仅提供打印日志功能；具体可以查看
[BaseActivity.java](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/base/BaseActivity.java "BaseActivity")

### 测试基类
* TestConstant 产生常量，例如字符队列、批量的url
* BaseTestActivity  可以直接通过 `addButton("点击", new Onclick())`方式添加button
* BaseTestFragment  可以直接通过 `addButton("点击", new Onclick())`方式添加button

### 常用工具类包
1. [AppUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/AppUtil.java) 获取程序名称、版本信息
2. [FileSizeUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/FileSizeUtil.java) 获取文件、文件夹大小
3. [FileUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/FileUtil.java) 文件操作帮助类
4. [IOUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/IOUtil.java) IO流帮助类
5. [KeyBoardUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/KeyBoardUtil.java) 键盘帮助类
6. [LogUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/LogUtil.java) 打印日志类
7. [PermissionUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/PermissionUtil.java) 权限帮助类
8. [SDCardUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/SDCardUtil.java) SDCard帮助类
9. [SPUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/SPUtil.java) SP读、存的帮助类
10. [TimeConvertUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/TimeConvertUtil.java) 时间转换类
11. [TimeStampUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/TimeStampUtil.java) 时间戳、时间相互转换
12. [UIResizeUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/UIResizeUtil.java) 对View设置成固定大小
13. [UIScreenUtil](https://github.com/yline/as_lib_sdk/blob/master/_LibSDK/LibSDK/src/main/java/com/yline/utils/UIScreenUtil.java) 单位转换、获取屏幕信息

## 混淆
`LibSDK`是完全可以混淆的，只是打印的日志相对应的定位内容也会被混淆掉

## 版本
###Version 2.0.2
> 个人使用已经较为稳定的版本  
> 提供常用工具类、控件基类、测试基类、入口基类以及异常日志处理机制    
> 本地记录日志功能




