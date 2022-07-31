package com.sunnyweather.android.logic


import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException


object Repository {

    // 发起网络query查询请求，返回LiveData<Result<LIst<Place>>> 类型数据，并对异常情况进行处理。
    // liveData()函数是lifecycle-livedata-ktx库提供的一个非常强大且好用的功能。它可以自动构建并返回liveData对象。
    // 然后在他的代码块中提供一个挂起函数的上下文。我们可以在liveData代码块中任意的挂起函数。
    // 将线程参数指定为Dispatchers.IO，这样所有的代码将运行在子线程中。主线程中不允许执行耗时长的代码，
    // 在仓库切换线程，可以随意执行耗时长的程序。
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            //获取网络查询数据
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            //如果网络查询后返回状态为“ok”，说明查询成功。placeResponse变量存储的是PlaceResponse 类型数据
            // PlaceResponse(val status:String,val places:List<Place>)，该类将网页返回的数据结构一致，
            // 变量名不一样的，使用注解进行了变更。因而可以直接使用该数据的结构，获取需要的数据。
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)   //使用success方法包装获取的城市数据列表
            } else {
                //使用该方法包装异常信息
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)    //使用该方法，将包装的结果发送出去，该方法类似于LiveData的setValue方法通知数据变化。
        //只不过我们无法直接取得返回值的LiveData对象，lifecycle-livedata-ktx提供了这样的替代方法通知变更 。
    }
}