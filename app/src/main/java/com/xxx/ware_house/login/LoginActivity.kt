package com.xxx.ware_house.login

import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.widget.AppCompatCheckBox
import com.lihang.smartloadview.SmartLoadingView
import com.xxx.ware_house.R
import com.xxx.ware_house.base.BaseActivity
import com.xxx.ware_house.common.SystemCommon
import com.xxx.ware_house.data.BaseResp
import com.xxx.ware_house.data.LoginReq
import com.xxx.ware_house.menu.MenuActivity
import com.xxx.ware_house.net.RetrofitManager
import com.xxx.ware_house.utils.LogUtil
import com.xxx.ware_house.utils.SpUtil
import com.yanzhenjie.permission.Action
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/24
 * DES:
 */
class LoginActivity : BaseActivity() {

    private val mEtLoginUsername by lazy { findViewById<EditText>(R.id.et_login_username) }
    private val mEtLoginPassword by lazy { findViewById<EditText>(R.id.et_login_password) }
    private val mIbPwdShow by lazy { findViewById<Switch>(R.id.ib_pwd_show) }
    private val mCbRememberPassword by lazy { findViewById<AppCompatCheckBox>(R.id.cb_remember_password) }
    private val mSlLogin by lazy { findViewById<SmartLoadingView>(R.id.sl_login) }
    override fun getContentViewId() = R.layout.activity_login

    override fun setListener() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndPermission.with(this)
                .runtime()
                .permission(
                    Permission.WRITE_EXTERNAL_STORAGE,
                    Permission.READ_EXTERNAL_STORAGE
                )
                .onDenied(Action<List<String?>> { data: List<String?>? ->
                    showToast(
                        "没有权限可能会影响app的使用，请手动开启权限"
                    )
                })
                .onGranted(Action<List<String?>> { data: List<String?>? ->

                })
                .start()
        }
        //判断是否获取MANAGE_EXTERNAL_STORAGE权限：
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivity(intent)
            } else {

            }
        }

        mEtLoginUsername.setText(SpUtil.get(SystemCommon.userName,"").toString())
        mEtLoginPassword.setText(SpUtil.get(SystemCommon.password,"").toString())

        mSlLogin.setOnClickListener {
            login()
        }

    }

    private fun login() {

        val userName = mEtLoginUsername.text.toString()
        val password = mEtLoginPassword.text.toString()
        if (userName.isEmpty()) {
            showToast("请输入用户名")
            return
        }
        if (password.isEmpty()) {
            showToast("请输入密码")
            return
        }
        val map: MutableMap<String, Any> = HashMap<String, Any>().apply {
            put("employeeCode", userName)
            put("password", password)
        }
        showLoading("登录中")
        RetrofitManager.instance?.getRetrofitApiService()?.login(LoginReq(userName, password))
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResp<String>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    showToast(e?.message?:"")
                    LogUtil.e(e.message)
                    hideLoading()
                }

                override fun onComplete() {
                    hideLoading()
                }

                override fun onNext(t: BaseResp<String>) {
                    if (t.code == 1) {
                        SpUtil.put(SystemCommon.userName, userName)
                        SpUtil.put(SystemCommon.password, password)
                        SpUtil.put(SystemCommon.token, t.data)
                        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        showToast(t.massge)
                    }
                }

            });


    }

    override fun processLogic() {


    }


}