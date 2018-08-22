## LibHttp
`LibSDK` 是一个基于Okhttp3和Gson封装的Http库；主要功能分为：

* get方式、post传输json两种方式 进行缓存
* 支持对象转json，json转对象

技术交流群：[644213963](https://jq.qq.com/?_wv=1027&k=4ETdgdJ)
个人邮箱：[957339173@qq.com](https://jq.qq.com/?_wv=1027&k=4B0yi1n)  
个人博客：[csdn.com/yline](http://blog.csdn.net/u014803950)  

## 依赖
* Gradle：
```compile 'com.yline.lib:LibHttp:2.1.1'```
* Maven:
```
    <dependency>
      <groupId>com.yline.lib</groupId>
      <artifactId>LibHttp</artifactId>
      <version>2.1.1</version>
      <type>pom</type>
    </dependency>
```
* Eclipse 请放弃治疗。

## 权限
```
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
```

## 使用教程

### 使用
调用Util

	private static <T> void get(String httpUrl, Map<String, String> param, OnJsonCallback<T> callback) {
		OkHttpUtils.get(httpUrl, param, "Cache-get", callback);
	}
更多使用请参照 工程中demo

## 版本    
### Version 3.0.0




