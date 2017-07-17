# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\study_adt_studio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
######################################### 以下是固定的 ############################################
######################################### 压缩 ############################################
#指定代码的压缩级别
-optimizationpasses 5

######################################### 优化 ############################################
 #不优化输入的类文件
-dontoptimize

######################################### 混淆配置 ############################################
#包名不混合大小写；混淆时，不会产生形形色色的类名
-dontusemixedcaseclassnames

#指定 不去忽略非公共的库类；是否混淆第三方jar
-dontskipnonpubliclibraryclasses

#混淆时，不做预校验
-dontpreverify

#混淆时是否记录日志
-verbose

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护给定的可选属性，例如LineNumberTable, LocalVariableTable, SourceFile, Deprecated, Synthetic, Signature, InnerClass
-keepattributes *Annotation*

#忽略警告，避免打包时某些警告出现
-ignorewarning

##记录生成的日志数据,gradle build时在本项目根目录输出##
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
########记录生成的日志数据，gradle build时 在本项目根目录输出-end######

#忽略警告
-dontwarn com.lippi.recorder.utils**

#如果引用了v4或者v7包
-dontwarn android.support.**

######################################### 保持哪些类不被混淆 ############################################
#不混淆某个类 的子类
-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

######################################### 本工程 ############################################

#保持 native 方法不被混淆
#  -keepclasseswithmembernames class * {
#       native <methods>;
#  }

#保持自定义控件类不被混淆
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}

# 保持自定义控件类不被混淆
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}

#保持类成员
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}

#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}

#保持 Parcelable 不被混淆
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}

#保持 Serializable 不被混淆
#-keepnames class * implements java.io.Serializable

#保留一个完整的包
#-keep class com.lippi.recorder.utils.** {
#    *;
#}

#如果不想混淆 keep 掉
#-keep class MyClass;

