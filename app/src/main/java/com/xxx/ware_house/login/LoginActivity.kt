package com.xxx.ware_house.login

import com.xxx.ware_house.R
import com.xxx.ware_house.base.BaseActivity

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/24
 * DES:
 */
class LoginActivity : BaseActivity<LoginPresenter>() {

    override fun bindLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        mPresenter = LoginPresenter()
    }

    override fun initData() {

    }

    override fun inject() {

    }
}