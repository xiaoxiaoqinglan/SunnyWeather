package com.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place


//视图与数据的连接层。外部通过 searchPlaces 方法设置searchLiveData的值，switchMap观察到该值变化，
// 则执行Repository.searchPlaces方法，
class PlaceViewModel: ViewModel() {
    //MutableLiveData 为可变的LiveData数据，只有三种方法getValue，setValue，postValue（非主线赋值使用）
    //隐藏变量，设置为私有，通过方法进行赋值
    private val searchLiveData= MutableLiveData<String>()

    //placeList对于Activity界面上显示的城市数据进行缓存
    val placeList=ArrayList<Place>()
    //观察searchLiveData 数据是否有变化，如果变化，则更新数据
    val placeLiveData= Transformations.switchMap(searchLiveData){ query->
        Repository.searchPlaces(query)
    }
    //提供给外部赋值方法
    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
}