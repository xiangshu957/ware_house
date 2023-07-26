package com.xxx.ware_house.base

import com.xxx.ware_house.api.RetrofitApiService
import com.xxx.ware_house.net.RetrofitManager
import com.xxx.ware_house.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/24
 * DES:
 */
class BasePresenter<V : BaseView?> {
    /**
     * 为了退出页面，取消请求
     */
    private var compositeDisposable: CompositeDisposable? = null

    /**
     * 绑定的view
     */
    private var mvpView: V? = null

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param mvpView
     */
    fun attachView(mvpView: V) {
        this.mvpView = mvpView
        compositeDisposable = CompositeDisposable()
    }

    /**
     * 解绑view，一般在onDestroy中调用
     */
    fun detachView() {
        mvpView = null
        //退出页面的时候移除网络请求
        removeDisposable()
    }

    private fun removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }
    }

    /**
     * 是否绑定view
     *
     * @return
     */
    fun isViewAttached(): Boolean {
        return mvpView != null
    }

    /**
     * 获取绑定的view
     *
     * @return
     */
    fun getView(): V? {
        return mvpView
    }
    //100026   81

    fun apiService() {
        return
        RetrofitManager.instance?.getRetrofitApiService()
    }

    fun <T> observe(observable: Observable<T>): Observable<T> {
        return observe(observable, true, "")
    }

    fun <T> observe(observable: Observable<T>, showDialog: Boolean): Observable<T> {
        return observe(observable, showDialog, "")
    }

    fun <T> observe(
        observable: Observable<T>,
        showDialog: Boolean,
        messafe: String?
    ): Observable<T> {
        return observable.subscribeOn(Schedulers.io())
            .doOnSubscribe { disposable: Disposable? ->
                if (showDialog) {
                    mvpView?.showLoading(messafe)
                }
            }.doFinally {
                if (showDialog) {
                    mvpView?.hideLoading()
                }
            }.observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                getView()?.bindLifecycle()
            }
    }

    fun <T> observeWithRetry(
        observable: Observable<T>,
        showDialog: Boolean,
        message: String?,
        retryMaxCount: Int
    ): Observable<T> {
        val currentCount = intArrayOf(0)
        return observable.subscribeOn(Schedulers.io())
            .retryWhen { throwableObservable: Observable<Throwable?>? ->
                //如果还没到次数，就延迟5秒发起重连
                LogUtils.i("重连", "当前重连的次数 == " + currentCount[0])
                if (currentCount[0] <= retryMaxCount) {
                    currentCount[0]++
                    return@retryWhen Observable.just<Int>(1)
                        .delay(5000, TimeUnit.MILLISECONDS)
                } else {
                    return@retryWhen Observable.error<Any>(Throwable("重连次数已达最高，请求超时"))
                }
            }
            .doOnSubscribe { disposable: Disposable? ->
                if (showDialog) {
                    mvpView?.showLoading(message)
                }
            }.doFinally {
                if (showDialog) {
                    mvpView?.hideLoading()
                }
            }.observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                getView()?.bindLifecycle()
            }
    }

}
