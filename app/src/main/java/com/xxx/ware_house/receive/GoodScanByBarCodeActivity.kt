package com.xxx.ware_house.receive

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xxx.ware_house.R
import com.xxx.ware_house.base.BaseActivity
import com.xxx.ware_house.common.SystemCommon
import com.xxx.ware_house.data.BarCodeInfo
import com.xxx.ware_house.data.BaseResp
import com.xxx.ware_house.data.Detail
import com.xxx.ware_house.data.Lists
import com.xxx.ware_house.net.RetrofitManager
import com.xxx.ware_house.utils.SpUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/26
 * DES:混扫
 */
class GoodScanByBarCodeActivity : BaseActivity() {

    private val mIvBack by lazy { findViewById<ImageView>(R.id.iv_back) }
    private val mTvTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private val mTvClient by lazy { findViewById<TextView>(R.id.tv_client) }
    private val mTvOddCode by lazy { findViewById<TextView>(R.id.tv_odd_code) }
    private val mRvGoodList by lazy { findViewById<RecyclerView>(R.id.rv_good_list) }
    private val mTvNum by lazy { findViewById<TextView>(R.id.tv_num) }
    private val mTvUpload by lazy { findViewById<TextView>(R.id.tv_upload) }
    override fun getContentViewId() = R.layout.activity_good_scan_by_barcode

    var client: Lists? = null
    var oddCode: String? = null

    override fun setListener() {

        mIvBack.setOnClickListener {
            finish()
        }

        mTvTitle.text = "收货扫描"

        client = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("client", Lists::class.java) as Lists
        } else {
            intent.getSerializableExtra("client") as Lists
        }

        oddCode = intent.getStringExtra("oddCode")

        mTvClient.setText(client?.customerName)
        mTvOddCode.setText(oddCode)

        mRvGoodList.apply {
            layoutManager = LinearLayoutManager(this@GoodScanByBarCodeActivity)
            adapter = myScanGoodAdapter.apply {

            }
        }

        mRvGoodList.apply {
            layoutManager = LinearLayoutManager(this@GoodScanByBarCodeActivity)
            adapter = myScanGoodAdapter.apply {

            }
        }

        mTvUpload.setOnClickListener {
            upload()
        }
    }

    private fun upload() {

        showLoading("正在保存")
        RetrofitManager.instance?.getRetrofitApiService()?.uploadReceive(
            hashMapOf<String, String>().apply {
                put("Authorization", SpUtil.get(SystemCommon.token, "") as String)
            },
            myScanGoodAdapter.data
        )
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResp<Any>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    showToast(e.message ?: "")
                    hideLoading()
                }

                override fun onComplete() {
                    hideLoading()
                }

                override fun onNext(t: BaseResp<Any>) {
                    if (t.code == 1) {
                        myScanGoodAdapter.setNewData(mutableListOf())
                    } else {
                        showToast(t.massge)
                    }
                }

            })

    }

    override fun processLogic() {

    }

    private val myScanGoodAdapter =
        object : BaseQuickAdapter<Detail, BaseViewHolder>(R.layout.item_scan_good_info) {

            override fun convert(helper: BaseViewHolder?, item: Detail?) {
                helper?.getView<TextView>(R.id.tv_box_code)?.text = item?.boxCode
                helper?.getView<TextView>(R.id.tv_good_weight)?.text = item?.weightDetail.toString()
                helper?.getView<TextView>(R.id.tv_good_vol)?.text = item?.volumeDetail.toString()
            }

        }

    @SuppressLint("SetTextI18n")
    override fun scan(barCodeInfo: BarCodeInfo) {
        super.scan(barCodeInfo)
        var isExit = false
        myScanGoodAdapter.data.forEach {
            if (it.boxCode.equals(barCodeInfo.barCode)) {
                isExit = true
            }
        }
        if (isExit) {
            showToast("请勿重复扫描")
            return
        }
        val detail: Detail = Detail().apply {
            weightDetail = barCodeInfo.weight?.toDouble() ?: 0.0
            propertyInfo2 = barCodeInfo.produceDate ?: ""
            boxCode = barCodeInfo.barCode
        }
        myScanGoodAdapter.addData(0, detail)
        mTvNum.setText("箱数：${myScanGoodAdapter.data.size}")
    }

}