package com.ben.kotlindemo.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ben.kotlindemo.R
import com.ben.kotlindemo.bean.Calendar
import com.ben.kotlindemo.event.SubtitleEvent
import com.ben.kotlindemo.util.CalendarDialog
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.layout_calendar_fragment_1.*
import org.greenrobot.eventbus.EventBus
import java.util.Calendar as DateCalendar

class CalendarFragment : Fragment(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    private lateinit var adapter: ViewPagerItemAdapter
    lateinit var date: String
    private var hourArray = arrayOf("")
    lateinit var nongliString: String
    lateinit var ganzhiString: String
    lateinit var jieqiString: String
    lateinit var jieriString: String
    lateinit var shengxiaoString: String
    lateinit var xingzuoString: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        initView(view)
        postCalendar(date)
        return view
    }

    private fun initView(view: View) {
        val calendar = DateCalendar.getInstance()
        val year = calendar.get(DateCalendar.YEAR)
        val month = String.format("%02d", calendar.get(DateCalendar.MONTH) + 1)
        val day = calendar.get(DateCalendar.DAY_OF_MONTH)
        date = year.toString() + month + day

        adapter = ViewPagerItemAdapter(ViewPagerItems.with(activity)
                .add("丑时", R.layout.fragment_blank)
                .add("寅时", R.layout.fragment_blank)
                .add("卯时", R.layout.fragment_blank)
                .add("辰时", R.layout.fragment_blank)
                .add("巳时", R.layout.fragment_blank)
                .add("午时", R.layout.fragment_blank)
                .add("未时", R.layout.fragment_blank)
                .add("申时", R.layout.fragment_blank)
                .add("酉时", R.layout.fragment_blank)
                .add("戌时", R.layout.fragment_blank)
                .add("亥时", R.layout.fragment_blank)
                .add("子时", R.layout.fragment_blank)
                .create())

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = adapter
        val viewpagertab: SmartTabLayout = view.findViewById(R.id.viewpagertab)
        viewpagertab.setViewPager(viewPager)

        viewpagertab.setOnPageChangeListener(this)
        if (viewpagertab.isSelected) {
            onPageSelected(viewPager.currentItem)
        }

        val clickMore: TextView = view.findViewById(R.id.click_more)
        clickMore.setOnClickListener {
            if (nongliString.isEmpty()) {
                return@setOnClickListener
            }
            CalendarDialog(context,
                    object : CalendarDialog.OnCloseListener {
                        override fun onClick(dialog: Dialog, confirm: Boolean) {
                            if (confirm) dialog.dismiss()
                        }
                    },
                    nongliString, ganzhiString, jieqiString, jieriString,
                    shengxiaoString, xingzuoString).show()
        }
    }

    override fun onPageSelected(position: Int) {
        val page = adapter.getPage(position)
        val text: TextView = page.findViewById(R.id.text)

        if (hourArray[0] != "") text.text = hourArray[position]
    }

    private fun postCalendar(date: String) {
        OkGo.post<String>("http://route.showapi.com/856-1").tag(this)
                .params("date", date).execute(object : StringCallback() {
                    override fun onSuccess(response: Response<String>?) {

                        val result = Gson().fromJson(response?.body(), Calendar::class.javaObjectType).showapi_res_body
                        EventBus.getDefault().post(SubtitleEvent(result.gongli, result.xingzuo))

                        nongliString = result.nongli
                        ganzhiString = result.ganzhi
                        jieqiString = result.jieqi24
                        jieriString = result.jieri
                        shengxiaoString = result.shengxiao
                        xingzuoString = result.xingzuo

                        yi.text = result.yi
                        ji.text = result.ji

                        nayin.text = String.format(getString(R.string.nayin_detail), result.nayin)
                        shen12.text = String.format(getString(R.string.shen12_detail), result.sheng12)
                        zhiri.text = String.format(getString(R.string.zhiri_detail), result.zhiri)
                        chongsha.text = String.format(getString(R.string.chongsha_detail), result.chongsha)
                        taishen.text = String.format(getString(R.string.taishen_detail), result.tszf)
                        jishen.text = String.format(getString(R.string.jishen_detail), result.jsyq)
                        pengzu.text = String.format(getString(R.string.pengzhu_detail), result.pzbj)
                        hehai.text = String.format(getString(R.string.hehai_detail), result.jrhh)

                        text.text = result.t1
                        hourArray = arrayOf(result.t1, result.t3, result.t5, result.t7, result.t9, result.t11,
                                result.t13, result.t15, result.t17, result.t19, result.t21, result.t23)

                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy()
        OkGo.getInstance().cancelAll()

    }


}
