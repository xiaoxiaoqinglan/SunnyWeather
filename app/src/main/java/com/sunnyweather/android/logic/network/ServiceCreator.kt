package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//创建Retrofit构建器的单例类
object ServiceCreator {
    //定义BASE_URL常量，用于指定Retrofit根路径，
    private const val BASE_URL="http://api.caiyunapp.com"
    private val retrofit =Retrofit.Builder()
        .baseUrl(BASE_URL)  //指定根路径
        .addConverterFactory(GsonConverterFactory.create())  //指定解析数据使用的转换库，以上两个是必须调用的
        .build()   //构建一个Retrofit对象

    //提供一个create 方法，接受一个Class类的参数。以供外部访问创建
    fun <T> create(serviceClass: Class<T>):T =retrofit.create(serviceClass)

    //泛型实例化，inline(内联函数，编译时直接替换到调用他的地方，减少匿名内部内的开销）关键字来修饰方法
    //和 reified （表示泛型需要实列化）关键字来修饰泛型 ，从而实现了泛型的实例化
    //JAVA的泛型功能是通过类似擦除机制来实现的，对于类型的约束只在编译时期存在。Kotlin也是这样，在实际运行时，
    //数据的实际类型已经被擦除，只留有数据。
    // 内联函数将代码直接替换到被待用的地方，因而不存在泛型被擦除的情况，编译后会使用实际的类型替代泛型，
    // 因而意味着可以将泛型数据进行实例化，需要将泛型使用reified 关键字修饰。
    inline fun <reified T> create() :T= create(T::class.java)
}