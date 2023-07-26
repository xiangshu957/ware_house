package com.xxx.ware_house.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragmentActivity
import com.xxx.ware_house.common.SystemCommon
import com.xxx.ware_house.data.BarCodeInfo
import com.xxx.ware_house.dialog.LoadingDialog
import com.xxx.ware_house.utils.LogUtil

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/24
 * DES:
 */
abstract class BaseActivity : RxFragmentActivity(), BaseView {

    private var immersionBar: ImmersionBar? = null

    //获取当前activity的布局文件
    abstract fun getContentViewId(): Int

    //设置监听事件
    abstract fun setListener()

    //处理业务逻辑
    abstract fun processLogic()

    private var mScanBroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {

            LogUtil.e(Gson().toJson(p1))
//            String scanData = intent.getStringExtra("scanData").trim();
            val scanData = p1?.getStringExtra("scannerdata")!!.trim { it <= ' ' }
            scan(BarCodeInfo(barCode = scanData))
        }

    }

    open fun scan(barCodeInfo: BarCodeInfo) {
        showToast(barCodeInfo.barCode?:"")
    }

    //注册广播
     fun registerReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction(SystemCommon.ACTION)
        registerReceiver(mScanBroadcastReceiver, intentFilter)
     }

    //注销广播
     fun unRegisterReceiver(){
        unregisterReceiver(mScanBroadcastReceiver)
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())

        immersionBar = ImmersionBar.with(this)
        immersionBar?.transparentStatusBar()
        immersionBar?.init()

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
        LoadingDialog.getInstance().show(this, msg)
    }

    override fun hideLoading() {
        LoadingDialog.getInstance().dismiss()
    }

    fun showToast(msg: String) {
        Toast.makeText(this, "${msg}", Toast.LENGTH_SHORT).show()
    }
}
