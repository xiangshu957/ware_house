package com.xxx.ware_house

import android.widget.ArrayAdapter
import android.widget.ImageView
import com.xxx.ware_house.base.BaseActivity
import androidx.appcompat.widget.AppCompatSpinner
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/25
 * DES:
 */
class ReceiveScanActivity : BaseActivity() {

    private val mIvBack by lazy { findViewById<ImageView>(R.id.iv_back) }
    private val mTvTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private val mAcsClient by lazy { findViewById<AppCompatSpinner>(R.id.acs_client) }
    private val mAcsOddCode by lazy { findViewById<AppCompatSpinner>(R.id.acs_odd_code) }
    private val mRgScan by lazy { findViewById<RadioGroup>(R.id.rg_scan) }
    private val mRbDetailScan by lazy { findViewById<RadioButton>(R.id.rb_detail_scan) }
    private val mRbScan by lazy { findViewById<RadioButton>(R.id.rb_scan) }
    private val mRvGoodList by lazy { findViewById<RecyclerView>(R.id.rv_good_list) }
    override fun getContentViewId() = R.layout.activity_receive_scan

    override fun setListener() {

        mIvBack.setOnClickListener {
            finish()
        }

        mTvTitle.text = "主菜单"

        //客户选择器
        mAcsClient.adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            arrayOf("张三","李四")
        )
        //入库单选择器
        mAcsOddCode.adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            arrayOf("张三","李四")
        )
    }

    override fun processLogic() {

    }

    override fun registerReceiver() {

    }

    override fun unRegisterReceiver() {

    }
}