package com.xxx.ware_house.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragmentActivity

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/24
 * DES:
 */
abstract class BaseActivity : RxFragmentActivity(), BaseView {

    //获取当前activity的布局文件
    abstract fun getContentViewId(): Int

    //设置监听事件
    abstract fun setListener()

    //处理业务逻辑
    abstract fun processLogic()

    //注册广播
    abstract fun registerReceiver()

    //注销广播
    abstract fun unRegisterReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())



        setListener()
        processLogic()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver()
    }

    override fun onPause() {
        super.onPause()
        unRegisterReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun transfer(clazz: Class<*>?) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

    fun transfer(clazz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, clazz)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }

    fun getStringByUi(view: View?): String {
        if (view is EditText) {
            return view.text.toString().trim { it <= ' ' }
        } else if (view is TextView) {
            return view.text.toString().trim { it <= ' ' }
        }
        return ""
    }

    override fun bindLifecycle(): LifecycleTransformer<*> {
        return bindToLifecycle<Any>()
    }

    override fun showLoading(msg: String?) {

    }

    override fun hideLoading() {

    }
}
