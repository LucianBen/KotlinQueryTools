package com.ben.kotlindemo.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.ben.kotlindemo.R
import com.ben.kotlindemo.activity.ConstellationDetailActivity
import com.ben.kotlindemo.bean.Constellation
import com.ben.kotlindemo.util.PinyinUtil
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.fragment_constellation.*

class ConstellationFragment : Fragment() {
    lateinit var todayConstellation: String

    companion object {
        @JvmStatic
        fun newInstance(param: String) = ConstellationFragment().apply {
            arguments = Bundle().apply {
                todayConstellation = param
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_constellation, container, false)
        initView(view)
        postConstellationData(PinyinUtil.getPingYin(todayConstellation.slice(0..1)), "0", "0", "0", "0")
        return view
    }

    private fun initView(view: View) {
        if (todayConstellation.isEmpty()) todayConstellation = "白羊座"
        val constellation: TextView = view.findViewById(R.id.constellation)
        val constellationToday: TextView = view.findViewById(R.id.constellation_today)

        constellation.text = todayConstellation
        constellation.setOnClickListener { dialogChoice() }
        constellationToday.text = String.format(getString(R.string.constellation_today), todayConstellation)

        val tomorrowShow = view.findViewById<CheckBox>(R.id.tomorrow_show)
        val tomorrowHide = view.findViewById<CheckBox>(R.id.tomorrow_hide)
        tomorrowShow.setOnCheckedChangeListener { _, _ -> tomorrowHide.isChecked = !tomorrowShow.isChecked }
        tomorrowHide.setOnCheckedChangeListener { _, _ -> tomorrowShow.isChecked = !tomorrowHide.isChecked }

        val weekShow = view.findViewById<CheckBox>(R.id.week_show)
        val weekHide = view.findViewById<CheckBox>(R.id.week_hide)
        weekShow.setOnCheckedChangeListener { _, _ -> weekHide.isChecked = !weekShow.isChecked }
        weekHide.setOnCheckedChangeListener { _, _ -> weekShow.isChecked = !weekHide.isChecked }

        val monthShow = view.findViewById<CheckBox>(R.id.month_show)
        val monthHide = view.findViewById<CheckBox>(R.id.month_hide)
        monthShow.setOnCheckedChangeListener { _, _ -> monthHide.isChecked = !monthShow.isChecked }
        monthHide.setOnCheckedChangeListener { _, _ -> monthShow.isChecked = !monthHide.isChecked }

        val yearShow = view.findViewById<CheckBox>(R.id.year_show)
        val showHide = view.findViewById<CheckBox>(R.id.show_hide)
        yearShow.setOnCheckedChangeListener { _, _ -> showHide.isChecked = !yearShow.isChecked }
        showHide.setOnCheckedChangeListener { _, _ -> yearShow.isChecked = !showHide.isChecked }

        val constellationQueryButton = view.findViewById<Button>(R.id.btn_constellation_query)
        constellationQueryButton.setOnClickListener {
            val intent = Intent(activity, ConstellationDetailActivity::class.java)
            intent.putExtra("name", constellation.text.toString())
                    .putExtra("tomorrow", if (tomorrowShow.isChecked) "1" else "0")
                    .putExtra("week", if (weekShow.isChecked) "1" else "0")
                    .putExtra("month", if (monthShow.isChecked) "1" else "0")
                    .putExtra("year", if (yearShow.isChecked) "1" else "0")
            startActivity(intent)
        }


    }

    private fun dialogChoice() {
        val constellationNames = arrayOf("白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座")

        AlertDialog.Builder(activity, 12).setTitle("请选择您的星座")
                .setSingleChoiceItems(constellationNames, constellationNames.indexOf(constellation.text.toString())) { dialog, which ->
                    dialog.dismiss()
                    constellation.text = constellationNames[which]
                }.create().show()

    }

    private fun postConstellationData(star: String, needTomorrow: String, needWeek: String, needMonth: String, needYear: String) {
        OkGo.post<String>("http://route.showapi.com/872-1").tag(this)
                .params("star", star).params("needTomorrow", needTomorrow).params("needWeek", needWeek)
                .params("needMonth", needMonth).params("needYear", needYear)
                .execute(object : StringCallback() {
                    override fun onSuccess(response: Response<String>?) {
                        val result = Gson().fromJson(response?.body(), Constellation::class.javaObjectType).showapi_res_body
                        if (result.ret_code != "0") return

                        constellation_today_detail.text = result.day.general_txt
                    }

                    override fun onError(response: Response<String>?) {
                        super.onError(response)
                        constellation_today_detail.text = "获取数据失败"
                    }
                })
    }


}
