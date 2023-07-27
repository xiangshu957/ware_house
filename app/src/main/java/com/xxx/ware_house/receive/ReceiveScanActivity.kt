package com.xxx.ware_house.receive

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xxx.ware_house.R
import com.xxx.ware_house.base.BaseActivity
import com.xxx.ware_house.common.SystemCommon
import com.xxx.ware_house.data.BaseResp
import com.xxx.ware_house.data.CustomResp
import com.xxx.ware_house.data.CustomerReq
import com.xxx.ware_house.data.Detail
import com.xxx.ware_house.data.GoodsDetailResp
import com.xxx.ware_house.data.Lists
import com.xxx.ware_house.data.OddCodeReq
import com.xxx.ware_house.data.OddCodeResp
import com.xxx.ware_house.net.RetrofitManager
import com.xxx.ware_house.utils.SpUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/25
 * DES:
 */
class ReceiveScanActivity : BaseActivity() {

    private var client: Lists? = null
    private var myClientAdapter: ArrayAdapter<Lists>? = null
    private var myOddCodeAdapter: ArrayAdapter<String>? = null

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

        mTvTitle.text = "收货扫描"

        //客户选择器
        myClientAdapter = object : ArrayAdapter<Lists>(
            this@ReceiveScanActivity,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            arrayListOf()
        ) {

        }
        mAcsClient.adapter = myClientAdapter
        mAcsClient
        //入库单选择器
        myOddCodeAdapter = object : ArrayAdapter<String>(
            this@ReceiveScanActivity,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            arrayListOf()
        ) {

        }
        mAcsOddCode.adapter = myOddCodeAdapter

        mAcsClient.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    return
                }
                if (myClientAdapter?.isEmpty == true) {
                    showToast("请选择客户")
                    return
                }
                client = myClientAdapter?.getItem(p2)
                if (client == null) {
                    return
                }
                showLoading("正在获取入库单")
                RetrofitManager.instance?.getRetrofitApiService()?.getOddCodeList(
                    hashMapOf<String, String>().apply {
                        put("Authorization", SpUtil.get(SystemCommon.token, "") as String)
                    },
                    OddCodeReq(clientID = client?.id.toString())
                )
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<BaseResp<OddCodeResp>> {
                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onError(e: Throwable) {
                            hideLoading()
                        }

                        override fun onComplete() {
                            hideLoading()
                        }

                        override fun onNext(t: BaseResp<OddCodeResp>) {

                            if (t.code == 1) {
                                t.data.lists?.add(0, "请选择入库单号")
                                myOddCodeAdapter?.addAll(arrayListOf())
                                myOddCodeAdapter?.addAll(t.data.lists ?: arrayListOf())
                                myOddCodeAdapter?.notifyDataSetChanged()
                            } else {
                                showToast(t.massge)
                            }
                        }

                    })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        //单号选择监听
        mAcsOddCode.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    return
                }
                if (myOddCodeAdapter?.getItem(p2).isNullOrEmpty()) {
                    showToast("请选择入库单号")
                    return
                }
                showLoading("正在获取明细")
                RetrofitManager.instance?.getRetrofitApiService()?.getGoodsDetail(
                    hashMapOf<String, String>().apply {
                        put("Authorization", SpUtil.get(SystemCommon.token, "") as String)
                    },
                    myOddCodeAdapter?.getItem(p2) ?: ""
                )
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Observer<BaseResp<GoodsDetailResp>> {
                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onError(e: Throwable) {
                            hideLoading()
                        }

                        override fun onComplete() {
                            hideLoading()
                        }

                        override fun onNext(t: BaseResp<GoodsDetailResp>) {

                            if (t.code == 1) {
                                myGoodAdapter.setNewData(t.data.lists)
                            } else {
                                showToast(t.massge)
                            }
                        }

                    })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        //明细扫描&混扫
        mRgScan.check(R.id.rb_detail_scan)
        mRbScan.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                if (client == null) {
                   mRgScan.check(R.id.rb_detail_scan)
                    showToast("请选择客户")
                    return@setOnCheckedChangeListener
                }
                if (mAcsOddCode.selectedItem.toString()
                        .isEmpty() || mAcsOddCode.selectedItemPosition == 0
                ) {
                    mRgScan.check(R.id.rb_detail_scan)
                    showToast("请选择入库单号")
                    return@setOnCheckedChangeListener
                }
                val intent = Intent(this@ReceiveScanActivity, GoodScanByBarCodeActivity::class.java)
                intent.putExtra("client", client)
                intent.putExtra("oddCode", mAcsOddCode.selectedItem as String)
                startActivity(intent)
            }
        }

        //货物列表
        mRvGoodList.apply {
            layoutManager = LinearLayoutManager(this@ReceiveScanActivity)
            adapter = myGoodAdapter.apply {
                setOnItemClickListener { adapter, view, position ->
                    val intent = if (mRgScan.checkedRadioButtonId == R.id.rb_detail_scan) {
                        Intent(this@ReceiveScanActivity, GoodScanActivity::class.java)
                    } else if (mRgScan.checkedRadioButtonId == R.id.rb_input) {
                        Intent(this@ReceiveScanActivity, InputGoodInfoActivity::class.java)
                    } else {
                        Intent(this@ReceiveScanActivity, GoodScanByBarCodeActivity::class.java)
                    }
                    intent.putExtra("client", client)
                    intent.putExtra("oddCode", mAcsOddCode.selectedItem as String)
                    intent.putExtra("good", myGoodAdapter.getItem(position) as Detail)
                    startActivity(intent)
                }
            }
        }
    }

    override fun processLogic() {

        showLoading("正在获取客户")
        RetrofitManager.instance?.getRetrofitApiService()?.getCustomer(
            hashMapOf<String, String>().apply {
                put("Authorization", SpUtil.get(SystemCommon.token, "") as String)
            },
            CustomerReq(current_page = 1, page_size = 10000, sysType = 2)
        )
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResp<CustomResp>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    hideLoading()
                }

                override fun onComplete() {
                    hideLoading()
                }

                override fun onNext(t: BaseResp<CustomResp>) {
                    if (t.code == 1) {
                        t.data.lists?.add(0, Lists(customerName = "请选择客户"))
                        myClientAdapter?.addAll(t.data.lists ?: mutableListOf())
                        myClientAdapter?.notifyDataSetChanged()
                    } else {
                        showToast(t.massge)
                    }
                }

            });

    }

    private val myGoodAdapter =
        object : BaseQuickAdapter<Detail, BaseViewHolder>(R.layout.item_good_info) {

            override fun convert(helper: BaseViewHolder?, item: Detail?) {

                helper?.getView<TextView>(R.id.tv_good_code)?.text = item?.goodsID.toString()
                helper?.getView<TextView>(R.id.tv_good_num)?.text = item?.orderQuantity.toString()
                helper?.getView<TextView>(R.id.tv_good_vol)?.text = item?.volumeDetail.toString()
                helper?.getView<TextView>(R.id.tv_good_batch)?.text = "-"

            }

        }

}