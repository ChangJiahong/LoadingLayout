package com.demo.cjh.loadinglayout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_internal.*

class InternalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal)
        click.setOnClickListener {
            startActivity(Intent(this@InternalActivity, MainActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.loading, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.loading -> {
                loadingLayout.showLoading()
            }

            R.id.error -> {
                loadingLayout.showError()
            }

            R.id.empty -> {
                loadingLayout.showEmpty()
            }

            R.id.content -> {
                loadingLayout.show()
            }

            R.id.define -> {
                loadingLayout.initDefinePage {
                    LayoutInflater.from(this).inflate(R.layout.define_layout, null)
                }
                loadingLayout.showDefinePage()
//                loadingLayout.showDefinePage(R.layout.define_layout,true)
            }
        }


        return super.onOptionsItemSelected(item)
    }
}
