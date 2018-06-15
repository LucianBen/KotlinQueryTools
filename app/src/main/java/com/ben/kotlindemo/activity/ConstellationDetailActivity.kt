package com.ben.kotlindemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ben.kotlindemo.R
import com.ben.kotlindemo.adapter.GroupAdapter
import com.ben.kotlindemo.bean.Child
import com.ben.kotlindemo.bean.Constellation
import com.ben.kotlindemo.bean.ConstellationResult
import com.ben.kotlindemo.bean.Group
import com.ben.kotlindemo.util.PinyinUtil
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_constellation_detail.*

class ConstellationDetailActivity : AppCompatActivity() {

    lateinit var name: String
    lateinit var tomorrow: String
    lateinit var week: String
    lateinit var month: String
    lateinit var year: String
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation_detail)

        initView()
        postConstellationData(PinyinUtil.getFullSpell(name.slice(0..1)), tomorrow, week, month, year)
    }

    private fun initView() {
        name = intent.getStringExtra("name")
        tomorrow = intent.getStringExtra("tomorrow")
        week = intent.getStringExtra("week")
        month = intent.getStringExtra("month")
        year = intent.getStringExtra("year")

        toolbar_constellation.title = name
        toolbar_constellation.setNavigationOnClickListener { finish() }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun postConstellationData(star: String, needTomorrow: String, needWeek: String, needMonth: String, needYear: String) {
        OkGo.post<String>("http://route.showapi.com/872-1").tag(this)
                .params("star", star).params("needTomorrow", needTomorrow).params("needWeek", needWeek)
                .params("needMonth", needMonth).params("needYear", needYear)
                .execute(object : StringCallback() {
                    override fun onSuccess(response: Response<String>?) {
                        val result = Gson().fromJson(response?.body(), Constellation::class.javaObjectType).showapi_res_body
                        if (result.ret_code != "0") return

                        dealDate(result)

                    }
                })
    }

    private fun dealDate(result: ConstellationResult) {
        val list = arrayListOf<Group>()
        //今日数据
        val childList = arrayListOf<Child>()
        childList.add(Child("综合指数", result.day.summary_star, "1"))
        childList.add(Child("爱情指数", result.day.love_star, "1"))
        childList.add(Child("财富指数", result.day.money_star, "1"))
        childList.add(Child("工作指数", result.day.work_star, "1"))
        childList.add(Child("贵人星座", result.day.grxz, "0"))
        childList.add(Child("幸运数字", result.day.lucky_num, "0"))
        childList.add(Child("吉时    ", result.day.lucky_time, "0"))
        childList.add(Child("吉利方位", result.day.lucky_direction, "0"))
        childList.add(Child("今日提醒", result.day.day_notice, "0"))
        childList.add(Child("运势简评", result.day.general_txt, "0"))
        childList.add(Child("爱情运势", result.day.love_txt, "0"))
        childList.add(Child("工作运势", result.day.work_txt, "0"))
        childList.add(Child("财富运势", result.day.money_txt, "0"))
        childList.add(Child("吉色    ", result.day.lucky_color, "0"))
        list.add(Group("今日运势", childList))

        //明日数据
        if (tomorrow == "1") {
            val tomorrowChildList = arrayListOf<Child>()
            tomorrowChildList.add(Child("综合指数", result.tomorrow.summary_star, "1"))
            tomorrowChildList.add(Child("爱情指数", result.tomorrow.love_star, "1"))
            tomorrowChildList.add(Child("财富指数", result.tomorrow.money_star, "1"))
            tomorrowChildList.add(Child("工作指数", result.tomorrow.work_star, "1"))
            tomorrowChildList.add(Child("贵人星座", result.tomorrow.grxz, "0"))
            tomorrowChildList.add(Child("幸运数字", result.tomorrow.lucky_num, "0"))
            tomorrowChildList.add(Child("吉时    ", result.tomorrow.lucky_time, "0"))
            tomorrowChildList.add(Child("吉利方位", result.tomorrow.lucky_direction, "0"))
            tomorrowChildList.add(Child("今日提醒", result.tomorrow.day_notice, "0"))
            tomorrowChildList.add(Child("运势简评", result.tomorrow.general_txt, "0"))
            tomorrowChildList.add(Child("爱情运势", result.tomorrow.love_txt, "0"))
            tomorrowChildList.add(Child("工作运势", result.tomorrow.work_txt, "0"))
            tomorrowChildList.add(Child("财富运势", result.tomorrow.money_txt, "0"))
            tomorrowChildList.add(Child("吉色    ", result.tomorrow.lucky_color, "0"))
            list.add(Group("明日运势", tomorrowChildList))
        }

        //本周数据
        if (week == "1") {
            val weekChildList = arrayListOf<Child>()
            weekChildList.add(Child("综合指数", result.week.summary_star, "1"))
            weekChildList.add(Child("爱情指数", result.week.love_star, "1"))
            weekChildList.add(Child("财富指数", result.week.money_star, "1"))
            weekChildList.add(Child("工作指数", result.week.work_star, "1"))
            weekChildList.add(Child("贵人星座", result.week.grxz, "0"))
            weekChildList.add(Child("幸运数字", result.week.lucky_num, "0"))
            weekChildList.add(Child("幸运日期", result.week.lucky_day, "0"))
            weekChildList.add(Child("吉利方位", result.week.lucky_direction, "0"))
            weekChildList.add(Child("本周提醒", result.week.week_notice, "0"))
            weekChildList.add(Child("运势简评", result.week.general_txt, "0"))
            weekChildList.add(Child("爱情运势", result.week.love_txt, "0"))
            weekChildList.add(Child("工作运势", result.week.work_txt, "0"))
            weekChildList.add(Child("财富运势", result.week.money_txt, "0"))
            weekChildList.add(Child("吉色    ", result.week.lucky_color, "0"))
            list.add(Group("本周运势", weekChildList))
        }

        //本月数据
        if (month == "1") {
            val monthChildList = arrayListOf<Child>()
            monthChildList.add(Child("综合指数", result.month.summary_star, "1"))
            monthChildList.add(Child("爱情指数", result.month.love_star, "1"))
            monthChildList.add(Child("财富指数", result.month.money_star, "1"))
            monthChildList.add(Child("工作指数", result.month.work_star, "1"))
            monthChildList.add(Child("贵人星座", result.month.grxz, "0"))
            monthChildList.add(Child("缘份星座", result.month.yfxz, "0"))
            monthChildList.add(Child("小人星座", result.month.xrxz, "0"))
            monthChildList.add(Child("吉利方位", result.month.lucky_direction, "0"))
            monthChildList.add(Child("本月优势", result.month.month_advantage, "0"))
            monthChildList.add(Child("本月弱势", result.month.month_weakness, "0"))
            monthChildList.add(Child("运势简评", result.month.general_txt, "0"))
            monthChildList.add(Child("爱情运势", result.month.love_txt, "0"))
            monthChildList.add(Child("工作运势", result.month.work_txt, "0"))
            monthChildList.add(Child("财富运势", result.month.money_txt, "0"))
            list.add(Group("本月运势", monthChildList))
        }

        //今年数据
        if (year == "1") {
            val yearChildList = arrayListOf<Child>()
            yearChildList.add(Child("综合指数", result.year.general_txt, "0"))
            yearChildList.add(Child("爱情指数", result.year.love_txt, "0"))
            yearChildList.add(Child("财富指数", result.year.money_txt, "0"))
            yearChildList.add(Child("工作指数", result.year.work_txt, "0"))
            yearChildList.add(Child("运势简评", result.year.oneword, "0"))
            yearChildList.add(Child("运势概述", result.year.general_txt, "0"))
            yearChildList.add(Child("爱情运势", result.year.love_txt, "0"))
            yearChildList.add(Child("工作运势", result.year.work_txt, "0"))
            yearChildList.add(Child("财富运势", result.year.money_txt, "0"))
            yearChildList.add(Child("健康运势", result.year.health_txt, "0"))
            list.add(Group("本年运势", yearChildList))
        }

        val groupAdapter = GroupAdapter(this@ConstellationDetailActivity, list)
        recyclerView.adapter = groupAdapter

    }

}
