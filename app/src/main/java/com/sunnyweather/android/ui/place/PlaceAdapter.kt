package com.sunnyweather.android.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place

/***
 * 为fragment 窗口中的 和获取的数据建立适配器
 *
 */
class PlaceAdapter(private val fragment:Fragment,private val placeList:List<Place>):RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    /***
     * Kotlin 使用 inner class 关键字定义内部类
     *Kotlin 内部类与嵌套类的区别是：
     *1.内部类会带有一个外部类的对象的引用，嵌套类则没有
     *2.内部类需要使用 inner class 定义，而嵌套类则使用 class 定义
     *Kotlin 内部类会带有一个对外部类的对象的引用，所以内部类可以访问外部类成员属性和成员函数
     *Kotlin 内部类使用 'this@[外部类名] '持有外部类对象的引用
     */
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        //获取当前View 的TextView实列
        val placeName:TextView=view.findViewById(R.id.placeName)
        val placeAddress:TextView=view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /***
         * inflate 的第一个参数是传递要转换的xml布局过来
         * 第二个参数对应的就是 FragmentInflateActivity 布局中的 FrameLayout
         */
        val view=LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val place=placeList[position]
        holder.placeName.text=place.name
        holder.placeAddress.text=place.address
    }

    override fun getItemCount()=placeList.size
}