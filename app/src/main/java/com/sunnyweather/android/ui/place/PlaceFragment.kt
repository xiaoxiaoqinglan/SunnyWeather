package com.sunnyweather.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.MainActivity
import com.sunnyweather.android.R
import kotlinx.android.synthetic.main.fragment_place.*



class PlaceFragment :Fragment(){
val viewModel by lazy{ ViewModelProvider(this).get(PlaceViewModel::class.java)}
    private lateinit var adapter:PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
        //加载 fragment_place 布局 的标准写法
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //绑定适配器相关代码
        val layoutManager=LinearLayoutManager(activity)  //设定线性管理器
        recyclerView.layoutManager=layoutManager
        adapter= PlaceAdapter(this,viewModel.placeList)    //设置适配器
        recyclerView.adapter=adapter         //绑定适配器

        //设置文本改变事件
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {     //内容不为空时，进行数据搜索
                viewModel.searchPlaces(content)
            } else {   // 内容为空时，隐藏数据显示界面recyclerView，显示bgImageView美观用的界面。
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        //对LIVEDATA数据进行观察，如果有数据变化，着激发该事件。
        viewModel.placeLiveData.observe(this, Observer{ result ->
            val places = result.getOrNull()         //获取返回的数据，如数据不为空，则添加数据。
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()         //通知适配器数据改变
            } else {
                Toast.makeText(
                    activity,
                    "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                            result.exceptionOrNull()?.printStackTrace()
            }
        })


    }


}



