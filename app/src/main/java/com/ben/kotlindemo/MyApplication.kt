package com.ben.kotlindemo

import android.app.Application
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.lzy.okgo.model.HttpParams
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import java.util.logging.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initOkGo()
    }

    fun initOkGo() {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor("OkGo")

        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        loggingInterceptor.setColorLevel(Level.INFO)
        builder.addInterceptor(loggingInterceptor)
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)

        val param = HttpParams()
        param.put("showapi_appid", "66958")
        param.put("showapi_sign", "26770371fed6403dae49fe1dfc79cb64")
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(3)
                .addCommonParams(param)
    }
}
