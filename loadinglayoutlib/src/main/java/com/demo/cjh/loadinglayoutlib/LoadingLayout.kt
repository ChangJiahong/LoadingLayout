package com.demo.cjh.loadinglayoutlib

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Created by CJH
 * on 2018/9/8
 */
class LoadingLayout: FrameLayout {
    val TAG = "LoadingLayout"


    companion object {
        val error_page = 0
        val empty_page = 1
        val loading_page = 2
        val define_Page = 3
    }

    private var mContext: Context

    lateinit  var loadingPage: LinearLayout
        private set
    lateinit var errorPage: LinearLayout
        private set
    lateinit var emptyPage: LinearLayout
        private set

    /**
     * 自定义加载页面
     */
    var defineLoadingPage: View? = null


    private var emptyStr: String? = "暂无数据"
    private var errorStr: String? = "加载错误"
    private var loadingStr: String? = "加载中。。。"
    private var errorBtnStr: String? = "重新加载"


    /** 只能外部访问，不能修改 **/
    /**
     * 空图片view
     */
    lateinit  var emptyImage: ImageView
        private set
    /**
     * 错误图片View
     */
    lateinit var errorImage: ImageView
        private set
    /**
     * 加载barView
     */
    lateinit var loadingProgress: ProgressBar
        private set
    /**
     * 重试按钮对象
     */
    lateinit var errorBtn: Button
        private set

    lateinit var loadingText: TextView
        private set

    lateinit var emptyText: TextView
        private set

    lateinit var errorText: TextView
        private set

    var errorImageVisible = true
        private set
    var emptyImageVisible = true
        private set


    var emptyTextColor = 0
    var errorTextColor = 0
    var loadingTextColor = 0
    var errorBtnTextColor = 0

    var emptyTextSize = 15
    var errorTextSize = 15
    var loadingTextSize = 15
    var errorBtnTextSize = 15

    var emptyImageId = R.drawable.error
    var errorImageId = R.drawable.error

    var errorImageWidth: Int = -2
    var errorImageHight: Int = -2
    var emptyImageWidth: Int = -2
    var emptyImageHight: Int = -2





    /**
     * 当前页面状态 ， 默认为loadingPage
     */
    var state = 2


    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        this.mContext = context
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingLayout)
        emptyStr = a.getString(R.styleable.LoadingLayout_emptyText)?:"暂无数据"
        errorStr = a.getString(R.styleable.LoadingLayout_errorText)?:"加载失败"
        loadingStr = a.getString(R.styleable.LoadingLayout_lodingText)?:"加载中。。。"
        errorBtnStr = a.getString(R.styleable.LoadingLayout_errorBtnText)?:"重新加载"

        state = a.getInt(R.styleable.LoadingLayout_pageType,2)

        emptyTextSize = a.getDimensionPixelSize(R.styleable.LoadingLayout_emptyTextSize,15)
        errorTextSize = a.getDimensionPixelSize(R.styleable.LoadingLayout_errorTextSize,15)
        loadingTextSize = a.getDimensionPixelSize(R.styleable.LoadingLayout_loadingTextSize,15)
        errorBtnTextSize = a.getDimensionPixelSize(R.styleable.LoadingLayout_errorBtnTextSize,15)

        emptyTextColor = a.getColor(R.styleable.LoadingLayout_emptyTextColor, 0xFF000000.toInt())
        errorTextColor = a.getColor(R.styleable.LoadingLayout_errorTextColor,0xFF000000.toInt())
        loadingTextColor = a.getColor(R.styleable.LoadingLayout_loadingTextColor,0xFF000000.toInt())
        errorBtnTextColor = a.getColor(R.styleable.LoadingLayout_errorBtnTextColor,0xFF000000.toInt())

        emptyImageId = a.getResourceId(R.styleable.LoadingLayout_emptyImageSrc,R.drawable.error)
        errorImageId = a.getResourceId(R.styleable.LoadingLayout_errorImageSrc,R.drawable.error)

        emptyImageVisible = a.getBoolean(R.styleable.LoadingLayout_emptyImageVisible,true)
        errorImageVisible = a.getBoolean(R.styleable.LoadingLayout_errorImageVisible,true)

        errorImageWidth = a.getLayoutDimension(R.styleable.LoadingLayout_errorImage_layout_width,-2)
        errorImageHight = a.getLayoutDimension(R.styleable.LoadingLayout_errorImage_layout_height,-2)

        emptyImageWidth = a.getLayoutDimension(R.styleable.LoadingLayout_emptyImage_layout_width,-2)
        emptyImageHight = a.getLayoutDimension(R.styleable.LoadingLayout_emptyImage_layout_height,-2)

        Log.v(TAG,""+state)
        Log.v(TAG, errorImageId.toString())
        Log.v(TAG,"$errorImageWidth")
        a.recycle()


    }

    constructor(context: Context,attributeSet: AttributeSet,defStyleAttr: Int):super(context,attributeSet,defStyleAttr){
        this.mContext = context
    }

    constructor(context: Context,attributeSet: AttributeSet,defStyleAttr: Int = 0,defStyleRes: Int = 0):super(context,attributeSet,defStyleAttr,defStyleRes){
        this.mContext = context
    }





    override fun onFinishInflate() {
        super.onFinishInflate()

        //this.visibility = View.GONE

        init()


    }


    private fun init() {
        val inflater = LayoutInflater.from(context)
        loadingPage = inflater.inflate(R.layout.loading_layout, null) as LinearLayout
        errorPage = inflater.inflate(R.layout.error_layout, null) as LinearLayout
        emptyPage = inflater.inflate(R.layout.empty_layout, null) as LinearLayout


        loadingText = loadingPage.findViewById(R.id.loadingText)
        errorText = errorPage.findViewById(R.id.errorText)
        emptyText = emptyPage.findViewById(R.id.emptyText)
        errorImage = errorPage.findViewById(R.id.errorImage)
        emptyImage = emptyPage.findViewById(R.id.emptyImage)
        errorBtn = errorPage.findViewById(R.id.errorBtn)

        emptyText.apply {
            text = emptyStr
            textSize = emptyTextSize.toFloat()
            setTextColor(emptyTextColor)
        }

        errorText.apply {
            text = errorStr
            textSize = errorTextSize.toFloat()
            setTextColor(errorTextColor)
        }

        loadingText.apply {
            text = loadingStr
            textSize = loadingTextSize.toFloat()
            setTextColor(loadingTextColor)
        }

        errorBtn.apply {
            text = errorBtnStr
            textSize = errorBtnTextSize.toFloat()
            setTextColor(errorBtnTextColor)

        }



        emptyImage.apply {
            visibility = if(emptyImageVisible) View.VISIBLE else View.GONE
            setImageResource(emptyImageId)
            var params = layoutParams
            params.width = emptyImageWidth
            params.height = emptyImageHight
            layoutParams = params
        }


        errorImage.apply {
            visibility = if(errorImageVisible) View.VISIBLE else View.GONE
            setImageResource(errorImageId)
            var params = layoutParams
            params.width = errorImageWidth
            params.height = errorImageHight
            layoutParams = params

        }


        this.addView(loadingPage)
        this.addView(emptyPage)
        this.addView(errorPage)

        // 显示
        show()

    }

    fun setEmptyImageResource(idRes: Int){
        emptyImage.setImageResource(idRes)
        emptyImageId = idRes
    }

    fun setErrorImageResource(idRes: Int){
        errorImage.setImageResource(idRes)
        errorImageId = idRes
    }


    fun setErrorClickListener( onClick:(v: View) -> Unit){
        errorBtn.setOnClickListener {
            onClick(it)
        }
    }

    fun show(){
        when(state){
            empty_page ->{
                defineLoadingPage?.visibility = View.GONE
                errorPage.visibility = View.GONE
                loadingPage.visibility = View.GONE
                emptyPage.visibility = VISIBLE
                Log.v(TAG,"show emptyPage")
            }
            loading_page ->{
                defineLoadingPage?.visibility = View.GONE
                errorPage.visibility = View.GONE
                emptyPage.visibility = View.GONE
                loadingPage.visibility = VISIBLE
                Log.v(TAG,"show loadingPage")
            }
            error_page ->{
                defineLoadingPage?.visibility = View.GONE
                loadingPage.visibility = View.GONE
                emptyPage.visibility = View.GONE
                errorPage.visibility = View.VISIBLE
                Log.v(TAG,"show errorPage")
            }
            define_Page ->{
                loadingPage.visibility = View.GONE
                emptyPage.visibility = View.GONE
                errorPage.visibility = View.GONE
                defineLoadingPage?.visibility = View.VISIBLE
                Log.v(TAG,"show definePage")

            }

        }
    }

    fun show(state: Int){
        this.state = state
        show()
    }

    fun gone(){
        loadingPage.visibility = View.GONE
        emptyPage.visibility = View.GONE
        errorPage.visibility = View.GONE
        defineLoadingPage?.visibility = View.GONE
    }
}