package com.demo.cjh.loadinglayoutlib

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.cjh.asdevtools.ex.TooManyChildViewException

/**
 * 内部加载页面
 * @author ChangJiahong
 * @date 2019/8/27
 */
class InternalLoadingLayout : FrameLayout {

    private var mContext: Context

    private val inflater = LayoutInflater.from(context)!!

    /**
     * 加载页面
     */
    // 是否已加载
    private var isLoadingPageInstantiated: Boolean = false
    private val loadingPage: LinearLayout by lazy {
        isLoadingPageInstantiated = true
        inflater.inflate(R.layout.loading_layout, null) as LinearLayout
    }

    /**
     * 错误页
     */
    // 是否已加载
    private var isErrorPageInstantiated: Boolean = false
    private val errorPage: LinearLayout by lazy {
        isErrorPageInstantiated = true
        inflater.inflate(R.layout.error_layout, null) as LinearLayout
    }

    /**
     * 空页面
     */
    // 是否已加载
    private var isEmptyPageInstantiated: Boolean = false
    private val emptyPage: LinearLayout by lazy {
        isEmptyPageInstantiated = true
        inflater.inflate(R.layout.empty_layout, null) as LinearLayout
    }

    /**
     * 自定义页面
     */
    var definePage: View? = null
        private set

    /**
     * 内容页面
     */
    private var contentPage: View? = null

    /**
     * 错误图标
     */
    private lateinit var errorImgV: ImageView

    /**
     * 错误消息提示
     */
    private lateinit var errorMsgV: TextView

    /**
     * 错误重试Button
     */
    private lateinit var errorBtn: Button

    /**
     * emptyPage 图标
     */
    private lateinit var emptyImg: ImageView

    /**
     * emptyPage 消息提示
     */
    private lateinit var emptyMsg: TextView

    private lateinit var loadingProgress: ProgressBar

    private lateinit var loadingMsg: TextView

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        this.mContext = context


    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        this.mContext = context
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(context, attributeSet, defStyleAttr, defStyleRes) {
        this.mContext = context
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        init()

    }

    /**
     * 加载
     */
    private fun init() {
        if (childCount > 1) {
            throw TooManyChildViewException("childCount must be <= 1 ")
        }
        // 内容页面
        if (childCount > 0)
            contentPage = getChildAt(0)


        show()


    }


    /**
     * 设置errorImg ImageView
     */
    fun errorImg(set: (v: ImageView) -> Unit) {
        // 配置前先加载控件
        initErrorP()
        errorImgV.apply(set)
    }

    /**
     * 设置errorMsg TextView
     */
    fun errorMsg(set: (v: TextView) -> Unit) {
        // 配置前先加载控件
        initErrorP()
        errorMsgV.apply(set)
    }

    /**
     * 设置errorBtn Button
     */
    fun errorBtn(set: (v: Button) -> Unit) {
        // 配置前先加载控件
        initErrorP()
        errorBtn.apply(set)
    }

    /**
     * 设置errorBtn ClickListener
     */
    fun setErrorClickListener(onClick: (v: View) -> Unit) {
        errorBtn.setOnClickListener {
            onClick(it)
        }
    }

    /**
     * 设置emptyImg ImageView
     */
    fun emptyImg(set: (v: ImageView) -> Unit) {
        // 配置前先加载控件
        initEmptyP()
        emptyImg.apply(set)
    }

    /**
     * 设置emptyMsg TextView
     */
    fun emptyMsg(set: (v: TextView) -> Unit) {
        // 配置前先加载控件
        initEmptyP()
        emptyMsg.apply(set)
    }

    /**
     * 设置loadingProgress ProgressBar
     */
    fun loadingProgress(set: (v: ProgressBar) -> Unit) {
        // 配置前先加载控件
        initLoadingP()
        loadingProgress.apply(set)
    }

    /**
     * 设置loadingMsg TextView
     */
    fun loadingMsg(set: (v: TextView) -> Unit) {
        // 配置前先加载控件
        initLoadingP()
        loadingMsg.apply(set)
    }

    fun initDefinePage(definePage: () -> View) {
        if (this.definePage != null) {
            this.removeView(this.definePage)
        }
        this.definePage = definePage()
        this.addView(this.definePage)
    }

    /**
     * 根据[id]显示自定义页面,[isHideContent] 表示显示自定义页面时是否需要隐藏内容
     * @param id 页面id
     * @param isHideContent 表示显示自定义页面时是否需要隐藏内容
     */
    fun showDefinePage(id: Int, isHideContent: Boolean = false) {
        if (this.definePage != null) {
            this.removeView(definePage)
        }

        this.definePage = this.inflater.inflate(id, null)
        addView(definePage)

        this.show(definePage!!, isHideContent)
    }

    /**
     * 显示内容页
     */
    fun show() {
        if (contentPage != null) {
            this.showV(contentPage!!)
        }
    }

    /**
     * 显示错误页面
     * @param isHideContent 表示显示该页面时是否需要隐藏内容
     */
    fun showError(isHideContent: Boolean = false) {
        initErrorP()
        this.show(errorPage, isHideContent)
    }

    /**
     * 加载 errorPage
     */
    private fun initErrorP() {
        if (!isErrorPageInstantiated) {
            // 第一次加载errorPage
            // 添加view
            this.addView(errorPage)
            hideV(errorPage)
            // 初始化组件对象
            errorImgV = errorPage.findViewById(R.id.errorImage)
            errorMsgV = errorPage.findViewById(R.id.errorText)
            errorBtn = errorPage.findViewById(R.id.errorBtn)
        }
    }

    /**
     * 显示空页面
     * @param isHideContent 表示显示该页面时是否需要隐藏内容
     */
    fun showEmpty(isHideContent: Boolean = false) {
        initEmptyP()
        this.show(emptyPage, isHideContent)
    }

    /**
     * 加载 emptyPage
     */
    private fun initEmptyP() {
        if (!isEmptyPageInstantiated) {
            this.addView(emptyPage)
            hideV(emptyPage)
            // 初始化组件对象
            emptyImg = emptyPage.findViewById(R.id.emptyImage)
            emptyMsg = emptyPage.findViewById(R.id.emptyText)
        }
    }

    /**
     * 显示加载页面
     * @param isHideContent 表示显示该页面时是否需要隐藏内容
     */
    fun showLoading(isHideContent: Boolean = false) {
        initLoadingP()
        this.show(loadingPage, isHideContent)
    }

    /**
     * 加载 loadingPage
     */
    private fun initLoadingP() {
        if (!isLoadingPageInstantiated) {
            this.addView(loadingPage)
            hideV(loadingPage)
            // 初始化组件对象
            loadingProgress = loadingPage.findViewById(R.id.loadingProgress)
            loadingMsg = loadingPage.findViewById(R.id.loadingText)
        }
    }

    /**
     * 显示自定义页面
     * @param isHideContent 表示显示该页面时是否需要隐藏内容
     */
    fun showDefinePage(isHideContent: Boolean = false) {
        if (definePage == null) {
            throw NullPointerException("definePage cannot be empty")
        }
        this.show(definePage!!, isHideContent)
    }

    /**
     * 显示页面
     * @param v 显示的页面
     * @param isHideContent 表示显示该页面时是否需要隐藏内容
     */
    private fun show(v: View, isHideContent: Boolean) {
        if (contentPage != null) {
            if (isHideContent) {
                hideV(contentPage!!)
            } else {
                showV(contentPage!!)
            }
        }
        showV(v)
    }

    /**
     * 设置view的显示属性
     * @param v
     */
    private fun showV(v: View) {
        this.hide()
        v.visibility = View.VISIBLE
    }


    /**
     * 隐藏除contentPage的所有Page
     */
    private fun hide() {
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            if (v != contentPage) {
                hideV(v)
            }
        }
    }

    /**
     * 设置view的显示属性
     * @param v View
     */
    private fun hideV(v: View) {
        v.visibility = View.GONE
    }

}