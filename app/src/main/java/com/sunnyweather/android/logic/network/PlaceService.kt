package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//定义访问彩云天气城市搜索API的Retrofit接口
interface PlaceService {
    // 当调用searchPlaces时，Retrofit会自动发起一条GET请求，去访问 @GET注解中配置的地址，
    // 其中搜索城市数据的API中只有query这个参数需动态指定，我们使用 @Query注解的方式实现。
    // 另外两个参数是不变的，因而使用固定写法。
    // 返回值被申明成Call<PlaceResponse>，就会将服务器返回的JSON数据自动解析成PlaceResponse数据。
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponse>
}