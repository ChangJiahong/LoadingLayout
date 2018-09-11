# LoadingLayout
LoadingLayout TestDemo
## 可以快速、简单的切换加载中、错误、空、或者自定义界面的控件
# 安卓中一个好用的空白页，错误页，加载页切换插件

最近开发中为了良好的用户体验，需要加入空白页、错误页、加载页等页面，给APP一个缓冲时间。单独开发总是需要创建几个layout，秉着降低代码冗余的态度，自己写了一个小工具，只要添加一个依赖就可以简单的使用，实现随时随地的切换页面。

放效果图：

![GIF](https://cjh.pythong.top/2018/09/10/LoadingLayout/GIF.gif)

## 使用方法
![](https://jitpack.io/v/ChangJiahong/LoadingLayout.svg)(https://jitpack.io/#ChangJiahong/LoadingLayout)
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

### 3. xml代码中可以引用：

```
<com.demo.cjh.loadinglayoutlib.LoadingLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:pageType="loading_page">
</com.demo.cjh.loadinglayoutlib.LoadingLayout>
```

其中包括直接定义的属性有：

- pageType : 枚举值（loading_page，error_page，empty_page） 页面类型
- errorImage_layout_height   ： 错误页图片的高
- errorImage_layout_width  ：错误页图片的宽
- errorImageSrc ： 错误页图片id
- errorImageVisible ：错误页图片是否显示
- errorText ：错误页提示字
- errorTextSize ： 错误页提示字大小
- errorTextColor ： 错误页提示字颜色
- errorBtnText ：错误页按钮提示字
- errorBtnTextColor ：错误页按钮字颜色
- errorBtnTextSize ： 错误按钮提示字大小
---
- emptyImage_layout_width ：空页图片的宽
- emptyImage_layout_height ：空页图片的高
- emptyImageSrc ：空页图片id
- emptyImageVisible ：空页图片是否显示
- emptyText ：空页提示字
- emptyTextColor ：空页提示字颜色
- emptyTextSize ：空页提示字大小
---
- lodingText ：加载页提示字
- loadingTextColor ：加载页提示字颜色
- loadingTextSize ：加载页提示字大小
---

#### 在代码中可以直接设置相关对象：

```
// 切换页面
loadingProgress.state = LoadingLayout.error_page
loadingProgress.show()

// 加载自定义页面
loadingProgress.state = LoadingLayout.define_Page
loadingProgress.setDefinePage(R.layout.define_layout)
loadingProgress.show()

// 设置页面gravity
loadingProgress.loadingPage.gravity = Gravity.BOTTOM
```
#### demo的xml

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <Button
            android:id="@+id/btn1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="加载" />
        <Button
            android:id="@+id/btn2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="错误" />
        <Button
            android:id="@+id/btn3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="空页面" />
        <Button
            android:id="@+id/btn5"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="自定义"/>
        <Button
            android:id="@+id/btn4"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="全不显示"/>
    </LinearLayout>

    <com.demo.cjh.loadinglayoutlib.LoadingLayout
        android:id="@+id/loadingProgress"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >

    </com.demo.cjh.loadinglayoutlib.LoadingLayout>

</RelativeLayout>
```
#### kt代码

```
class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)

        loadingProgress.setErrorClickListener {
            Toast.makeText(this,"重试了",Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.btn1 ->{
                loadingProgress.state = LoadingLayout.loading_page
                loadingProgress.show()
            }
            R.id.btn2 ->{
                loadingProgress.state = LoadingLayout.error_page
                loadingProgress.show()
            }
            R.id.btn3 ->{
                loadingProgress.state = LoadingLayout.empty_page
                loadingProgress.show()
            }
            R.id.btn4 ->{
                loadingProgress.gone()
            }
            R.id.btn5 ->{
                loadingProgress.state = LoadingLayout.define_Page
                loadingProgress.setDefinePage(R.layout.define_layout)
                loadingProgress.show()
                //loadingProgress.show()
            }
        }
    }

}

```

