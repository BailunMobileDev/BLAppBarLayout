# BLAppBarLayout  
这个控件主要是实现了可以回弹的效果和下拉控价放大的behavior.效果如下:  
![gif](gifhome_368x656_18s.gif)
## 依赖引入
在项目的 build.gradle 文件中加入以下仓库
<pre>
allprojects {
	repositories {
		...
			maven { url 'https://jitpack.io' }
	}
}
</pre>  
在模块中加入以下依赖  
<pre>
dependencies {
    implementation'com.github.BailunMobileDev:BLAppBarLayout:1.0'
}
</pre>  
## 使用方式  
回弹只需要使用布局文件中加入相关控件和behavior就可以.详情见demo