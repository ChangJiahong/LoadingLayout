package com.demo.cjh.loadinglayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.demo.cjh.loadinglayoutlib.LoadingLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)

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
        }
    }

}
