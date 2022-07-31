package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//实现具体的网络访问功能
object SunnyWeatherNetwork {
    //创建 PlaceService 接口的动态代理
    private val placeService = ServiceCreator.create<PlaceService>()

    //定义一个挂起的查找函数，发起城市搜索数据请求，同时当前携程会被阻塞；直到服务器响应服务之后，
    // await函数会将解析出来的数据模型对象取出并返回，同时恢复当前携程的执行。
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    //定义一个await扩展函数，借助携程技术，简化Retrofit回调的写法
    //T泛型，实现含T泛型的Call类型数据的扩展方法await。返回值类型为泛型T
    private suspend fun <T> Call<T>.await(): T {
        //返回值为函数suspendCoroutine(crossinline block: (Continuation<T>) -> Unit): T.
        // 该函数的作用是将当前携程立即挂起，然后在一个普通线程中执行Lambda表达式，
        return suspendCoroutine { continuation ->
            //直接调用enqueue方法让Retrofit发起网络请求。
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()    //获取网页访问后的主体内容
                    // continuation.resume 该方法让携程恢复执行
                    if (body != null) continuation.resume(body)
                    // continuation.resumeWithException 该方法让携程恢复执行，返回异常信息
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )

                }

                //访问网页后的异常处理，必须复写的代码
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })

        }
    }
}