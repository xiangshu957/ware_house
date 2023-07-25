package com.xxx.ware_house.menu

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.xxx.ware_house.R
import com.xxx.ware_house.base.BaseActivity

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/25
 * DES:
 */
class MenuActivity : BaseActivity() {

    private val mIvBack by lazy { findViewById<ImageView>(R.id.iv_back) }
    private val mTvTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private val mLlReceiveScan by lazy { findViewById<LinearLayout>(R.id.ll_receive_scan) }
    private val mLlOnShelfScan by lazy { findViewById<LinearLayout>(R.id.ll_on_shelf_scan) }
    private val mLlLocationQuery by lazy { findViewById<LinearLayout>(R.id.ll_location_query) }
    private val mLlReceiveReceipt by lazy { findViewById<LinearLayout>(R.id.ll_receive_receipt) }
    private val mLlPickingScan by lazy { findViewById<LinearLayout>(R.id.ll_picking_scan) }

    override fun getContentViewId() = R.layout.acyivity_menu

    override fun setListener() {

        mIvBack.setOnClickListener {
            finish()
        }

        mTvTitle.text = "主菜单"


        //收货扫描
        mLlReceiveScan.setOnClickListener {

        }

        //收货校验
        mLlReceiveReceipt.setOnClickListener {

        }

        //上架扫描
        mLlOnShelfScan.setOnClickListener {

        }

        //拣货扫描
        mLlPickingScan.setOnClickListener {

        }

        //库存查询
        mLlLocationQuery.setOnClickListener {

        }
    }

    override fun processLogic() {

    }

    override fun registerReceiver() {

    }

    override fun unRegisterReceiver() {

    }
}