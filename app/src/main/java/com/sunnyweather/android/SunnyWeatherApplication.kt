package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

//由于ViewModel 层开始不再持有Activity的引用，经常会出现缺“context”
//需要在AndroidManifest.xml 中的<application>中的 android：name=“.SunnyWeatherApplication”,
//然后就能在程序任何地方调用SunnyWeatherApplication.context 来获取Context对象
 class SunnyWeatherApplication : Application() {
    //伴生函数，设置包内全局唯一变量，context为SunnyWeatherApplication 类中公共变量，只要是继承该类的都能
    //调用该变量获取窗口的applicationContext
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        //彩云天气的开发者平台提供的令牌值赋值给变量，方便调取
        const val TOKEN ="RE42849PydcroLhg"
    }

     override fun onCreate() {
         super.onCreate()
         //将当前 Context 赋值给变量 context, 使得在任何窗口都能获取context
         context=applicationContext
     }
}