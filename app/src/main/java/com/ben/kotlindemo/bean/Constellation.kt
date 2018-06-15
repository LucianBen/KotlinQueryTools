package com.ben.kotlindemo.bean

data class Constellation(val showapi_res_error: String,
                         val showapi_res_code: String,
                         val showapi_res_body: ConstellationResult)

data class ConstellationResult(val ret_code: String,//0为成功，其他失败
                               val day: Day,//本日运势数据对象
                               val tomorrow: Tomorrow,//明日运势数据对象
                               val week: Week,//本周运势数据对象
                               val month: Month,//本月运势数据对象
                               val year: Year)//本年运势数据对象

data class Day(val love_txt: String,//爱情运势
               val work_txt: String,//工作运势
               val work_star: String,//工作指数，最高5分
               val money_star: String,//财富指数，最高5分
               val lucky_color: String,//吉色
               val lucky_time: String,//吉时
               val love_star: String,//爱情指数，最高5分
               val lucky_direction: String,//吉利方位
               val summary_star: String,//综合指数，最高5分
               val time: String,//时间
               val money_txt: String,//财富运势
               val general_txt: String,//运势简评
               val grxz: String,//贵人星座
               val lucky_num: String,//幸运数字
               val day_notice: String)//今日提醒

data class Tomorrow(val love_txt: String,
                    val work_txt: String,
                    val work_star: String,
                    val money_star: String,
                    val lucky_color: String,
                    val lucky_time: String,
                    val love_star: String,
                    val lucky_direction: String,
                    val summary_star: String,
                    val time: String,
                    val money_txt: String,
                    val general_txt: String,
                    val grxz: String,
                    val lucky_num: String,
                    val day_notice: String)

data class Week(val love_txt: String,
                val work_txt: String,
                val work_star: String,
                val money_star: String,
                val lucky_color: String,
                val lucky_day: String,
                val love_star: String,
                val lucky_direction: String,
                val summary_star: String,
                val time: String,
                val money_txt: String,
                val general_txt: String,
                val grxz: String,
                val lucky_num: String,
                val week_notice: String)//本周提醒

data class Month(val summary_star: String,
                 val love_star: String,
                 val work_star: String,
                 val money_star: String,
                 val yfxz: String,//缘份星座
                 val grxz: String,//贵人星座
                 val xrxz: String,//小人星座
                 val lucky_direction: String,
                 val month_advantage: String,
                 val month_weakness: String,//本月弱势
                 val general_txt: String,
                 val love_txt: String,
                 val work_txt: String,
                 val money_txt: String)

data class Year(val love_txt: String,
                val work_txt: String,
                val money_txt: String,
                val health_txt: String,//健康运势
                val general_txt: String,//运势概述
                val oneword: String,//一句话简评
                val work_index: String,//
                val money_index: String,
                val love_index: String,
                val general_index: String)