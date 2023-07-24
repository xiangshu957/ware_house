package com.xxx.ware_house.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/24
 * DES:
 */
abstract class BaseActivity<P : IPresenter> : AppCompatActivity(), IView, IActivity {

    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindLayout())

        inject()
        initView()
        initData()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showDialog() {
    }

    override fun hideDialog() {
    }

}
