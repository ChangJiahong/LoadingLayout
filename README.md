# LoadingLayout

[![](https://jitpack.io/v/ChangJiahong/LoadingLayout.svg)](https://jitpack.io/#ChangJiahong/LoadingLayout)

## 可以快速、简单的切换加载中、错误、空、或者自定义界面的控件
## 安卓中一个好用的空白页，错误页，加载页切换插件

最近开发中为了良好的用户体验，需要加入空白页、错误页、加载页等页面，给APP一个缓冲时间。单独开发总是需要创建几个layout，秉着降低代码冗余的态度，自己写了一个小工具，只要添加一个依赖就可以简单的使用，实现随时随地的切换页面。

<img src="https://cjh.pythong.top/2018/09/10/LoadingLayout/GIF.gif" width = "40%"  alt="图片名称" align=left/>

## 使用方法


### 1. 将其添加到你的项目的build.gradle中：

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### 2. 在APP build.gradle中添加依赖

```
dependencies {
		 implementation 'com.github.ChangJiahong:LoadingLayout:v0.1.2'
	}
```



### v0.2.0版

在v0.1.2基础上增加了InternalLoadingLayout类。该类可以管理内容页面的显示和创建，不需要手动控制内容页面的显示，使用时只要将InternalLoadingLayout控件作为根布局即可。

#### 示例

##### 布局

```xml
<com.demo.cjh.loadinglayoutlib.InternalLoadingLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/loadingLayout"
    tools:context=".MainActivity">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:gravity="center"
        android:padding="20dp">
        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="输入"/>
        <Button
            android:id="@+id/click"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Click"/>
    </LinearLayout>
</com.demo.cjh.loadinglayoutlib.InternalLoadingLayout>
```

##### 切换页面

```kotlin
// 显示加载页面
loadingLayout.showLoading()
// 显示错误页
loadingLayout.showError()
// 显示数据空页面
loadingLayout.showEmpty()
// 显示内容页面
loadingLayout.show()
// 显示自定义页
loadingLayout.showDefinePage(R.layout.define_layout,true)
// 调用此方法时，需先指定自定义页面
loadingLayout.showDefinePage()
// 指定自定义页面
loadingLayout.definePage = View
```

以上方法包括一个 **isHideContent** 属性，默认为false。

如果是false表示显示该页面时不隐藏内容页面

##### 修改页面图标

```kotlin
// 设置emptyImg ImageView
loadingLayout.emptyImg{}
// 设置emptyMsg TextView
loadingLayout.emptyMsg{}
// 设置loadingProgress ProgressBar
loadingLayout.loadingProgress{}
// 设置loadingMsg TextView
loadingLayout.loadingMsg{}
// 设置errorBtn ClickListener
loadingLayout.setErrorClickListener{}
// 设置errorBtn Button
loadingLayout.errorBtn{}
// 设置errorMsg TextView
loadingLayout.errorMsg{}
// 设置errorImg ImageView
loadingLayout.errorImg{}
```

### [v0.1.2版说明](doc/v0.1.2.md)

