package com.xxx.ware_house.login

import android.content.Intent
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.widget.AppCompatCheckBox
import com.lihang.smartloadview.SmartLoadingView
import com.xxx.ware_house.R
import com.xxx.ware_house.base.BaseActivity
import com.xxx.ware_house.menu.MenuActivity

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

        mSlLogin.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

    }

    override fun processLogic() {
    }

    override fun registerReceiver() {
    }

    override fun unRegisterReceiver() {
    }

}