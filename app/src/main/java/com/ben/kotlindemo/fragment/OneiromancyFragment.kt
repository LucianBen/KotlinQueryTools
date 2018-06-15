package com.ben.kotlindemo.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ben.kotlindemo.R
import com.ben.kotlindemo.activity.OneiromancyResolveActivity
import com.ben.kotlindemo.bean.Oneiromancy
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.fragment_oneiromancy.*
import kotlinx.android.synthetic.main.fragment_oneiromancy.view.*

class OneiromancyFragment : Fragment() {

    val data = arrayListOf<String>()
    val dataList = arrayListOf<List<String>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_oneiromancy, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        view.oneiromancy_search.setOnClickListener {
            if (oneiromancy_search_keyword.text.toString() != "")
                postOneiromancy(oneiromancy_search_keyword.text.toString())
        }
        view.listview_oneiromancy.setOnItemClickListener { _, _, position, _ ->
            val title = data[position]
            val content = ArrayList<String>(dataList[position])
            val intent = Intent(activity, OneiromancyResolveActivity::class.java)
            intent.putExtra("title", title).putStringArrayListExtra("content", content)
            startActivity(intent)
        }
    }

    private fun postOneiromancy(keyValue: String) {
        OkGo.post<String>("http://route.showapi.com/1601-2").tag(this)
                .params("keyWords", keyValue).execute(object : StringCallback() {
                    override fun onSuccess(response: Response<String>?) {
                        data.clear()
                        dataList.clear()
                        val result = Gson().fromJson(response?.body(), Oneiromancy::class.javaObjectType).showapi_res_body
                        if (result.ret_code != "0") return
                        if (result.contentlist.isEmpty()) {
                            Toast.makeText(activity, "查询无结果", Toast.LENGTH_SHORT).show()
                            return
                        }

                        for (item in result.contentlist.indices) {
                            data.add(result.contentlist[item].name)
                            dataList.add(result.contentlist[item].detailList)
                        }
                        val adapter = ArrayAdapter<String>(activity,
                                R.layout.listview_oneiromancy_title, R.id.lv_oneiromancy_title, data)
                        listview_oneiromancy.adapter = adapter
                    }

                })
    }


}
