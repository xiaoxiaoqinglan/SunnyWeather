package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

//定义数据模型
data class PlaceResponse(val status:String,val places:List<Place>)
//@SerializedName 这个注解是为了解决json数据解析时，数据名称不一致的问题，解析后用新变量名
data class Place (val name:String,val location:Location,@SerializedName("formatted_address") val address:String)
data class Location(val lng:String,val lat:String)