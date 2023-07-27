package com.xxx.ware_house.receive

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
import com.xxx.ware_house.utils.GsonUtils
import com.xxx.ware_house.utils.SpUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/27
 * DES:
 */
class InputGoodInfoActivity() : BaseActivity() {

    var client: Lists? = null
    var good: Detail? = null
    var oddCode: String? = null

    private val mIvBack by lazy { findViewById<ImageView>(R.id.iv_back) }
    private val mTvTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private val mTvClient by lazy { findViewById<TextView>(R.id.tv_client) }
    private val mTvOddCode by lazy { findViewById<TextView>(R.id.tv_odd_code) }
    private val mTvGoodCode by lazy { findViewById<TextView>(R.id.tv_good_code) }
    private val mRvGoodList by lazy { findViewById<RecyclerView>(R.id.rv_good_list) }
    private val mTvNum by lazy { findViewById<TextView>(R.id.tv_num) }
    private val mTvAdd by lazy { findViewById<TextView>(R.id.tv_add) }
    private val mTvUpload by lazy { findViewById<TextView>(R.id.tv_upload) }

    override fun getContentViewId() = R.layout.activity_input_good

    override fun setListener() {
        mIvBack.setOnClickListener {
            finish()
        }

        mTvTitle.text = "收货扫描"

        mTvUpload.setOnClickListener {
            upload()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun upload() {

        showLoading("正在保存")
        RetrofitManager.instance?.getRetrofitApiService()?.uploadReceive(
            hashMapOf<String, String>().apply {
                put("Authorization", SpUtil.get(SystemCommon.token, "") as String)
            },
            RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                GsonUtils.ser(myScanGoodAdapter.data)
            )
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

        mTvAdd.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflate = LayoutInflater.from(this).inflate(R.layout.dialog_input, null, false)
            val etNum = inflate.findViewById<EditText>(R.id.et_num)
            val etWeight = inflate.findViewById<EditText>(R.id.et_weight)
            val etVol = inflate.findViewById<EditText>(R.id.et_vol)
            val etProduceTime = inflate.findViewById<EditText>(R.id.et_produce_time)
            val etExpirationTime = inflate.findViewById<EditText>(R.id.et_expiration_time)
            builder.setView(inflate)
            val create = builder.create()
            inflate.findViewById<TextView>(R.id.tv_confirm).setOnClickListener {
                if (etNum.text.toString() == "") {
                    return@setOnClickListener
                }
                if (etWeight.text.toString() == "") {
                    return@setOnClickListener
                }
                if (etVol.text.toString() == "") {
                    return@setOnClickListener
                }
                if (etProduceTime.text.toString() == "") {
                    return@setOnClickListener
                }
                if (etExpirationTime.text.toString() == "") {
                    return@setOnClickListener
                }
                scan(
                    BarCodeInfo(
                        num = etNum.text.toString(),
                        weight = etWeight.text.toString(),
                        vol = etVol.text.toString(),
                        produceDate = etProduceTime.text.toString(),
                        expirationTime = etExpirationTime.toString()
                    )
                )
                create.dismiss()
            }
            inflate.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
                create.dismiss()
            }

            create.show()
        }
    }

    override fun processLogic() {

        client = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("client", Lists::class.java) as Lists
        } else {
            intent.getSerializableExtra("client") as Lists
        }

        good = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("good", Detail::class.java)
        } else {
            intent.getSerializableExtra("good") as Detail
        }

        oddCode = intent.getStringExtra("oddCode")

        mTvClient.text = client?.customerName
        mTvOddCode.text = oddCode
        mTvGoodCode.text = good?.goodsID.toString()

        mRvGoodList.apply {
            layoutManager = LinearLayoutManager(this@InputGoodInfoActivity)
            adapter = myScanGoodAdapter.apply {

            }
        }

    }

    private val myScanGoodAdapter =
        object : BaseQuickAdapter<Detail, BaseViewHolder>(R.layout.item_input_good_info) {

            override fun convert(helper: BaseViewHolder?, item: Detail?) {
                helper?.getView<TextView>(R.id.tv_num)?.text = item?.quantity1.toString()
                helper?.getView<TextView>(R.id.tv_weight)?.text = item?.weightDetail.toString()
                helper?.getView<TextView>(R.id.tv_vol)?.text = item?.volumeDetail.toString()
            }

        }


    @SuppressLint("SetTextI18n")
    override fun scan(barCodeInfo: BarCodeInfo) {
        super.scan(barCodeInfo)

        val detail: Detail = GsonUtils.deser(GsonUtils.ser(good), Detail::class.java) as Detail
        detail.apply {
            quantity1 = barCodeInfo.num?.toDouble() ?: 0.0
            weightDetail = barCodeInfo.weight?.toDouble() ?: 0.0
            volumeDetail = barCodeInfo.vol?.toDouble() ?: 0.0
            propertyInfo2 = barCodeInfo.produceDate ?: ""
            boxCode = barCodeInfo.barCode
            propertyInfo3 = barCodeInfo.expirationTime ?: " "
        }
        myScanGoodAdapter.addData(0, detail)
        var weightSum = 0.0
        var volSum = 0.0
        myScanGoodAdapter.data.forEach {
            weightSum += it.weightDetail ?: 0.0
            volSum += it.volumeDetail ?: 0.0
        }
        mTvNum.text = "箱数：${myScanGoodAdapter.data.size}  重量：${weightSum}  体积：${volSum}"
    }

}