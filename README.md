## LibHttp
`LibSDK` 是一个基于Okhttp3和Gson封装的Http库；主要功能分为：

* get方式、post传输json两种方式 进行缓存
* 支持对象转json，json转对象
* 支持handler处理
* 以上都支持自定义

技术交流群：[644213963](https://jq.qq.com/?_wv=1027&k=4B0yi1n)  
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
### 使用前提：初始化
    XHttpConfig.getInstance().init(IApplication.this);

### 使用
设置Util

	public static void doPostDefault(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/news";
		new YlineHttp().doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
	}
调用Util

	XHttpUtil.doPostDefault(new WNewsMultiplexBean(0, 10), new XHttpAdapter<VNewsMultiplexBean>()
	{
		@Override
		public void onSuccess(VNewsMultiplexBean vNewsMultiplexBean)
		{
		}
	});
更多使用请参照 工程中demo

## 混淆
`LibHttp`和`OkHttp3`相同

    -dontwarn com.squareup.okhttp3.**
    -keep class com.squareup.okhttp3.** { *;}
    -dontwarn okio.**

## 版本    
### Version 2.1.1
> 个人使用已经较为稳定的版本  
> 实现对象与json相互转换    
> 实现get和post传输json 缓存   
> 实现handler处理子线程事件 




