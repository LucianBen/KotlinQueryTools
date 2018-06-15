package com.ben.kotlindemo.bean

data class Calendar(val showapi_res_error: String,
                    val showapi_res_code: String,
                    val showapi_res_body: CalendarResult)

data class CalendarResult(val t3: String,//寅时 3-5
                  val t21: String,//亥时 21-23
                  val t1: String,//丑时 1-3
                  val t23: String,//子时 23-1
                  val nongli: String,
                  val jieqi24: String,//24节气
                  val nayin: String,//纳音
                  val tszf: String,//胎神吉方
                  val ret_code: String,//0为成功，其他失败
                  val shengxiao: String,
                  val xingzuo: String,
                  val ganzhi: String,
                  val t11: String,//午时 11-13
                  val t13: String,//未时 13-15
                  val sheng12: String,//十二神
                  val t15: String,//申时 15-17
                  val pzbj: String,//彭祖百忌
                  val t17: String,//酉时 17-19
                  val t19: String,//戌时 19-21
                  val zhiri: String,//值日
                  val gongli: String,
                  val jsyq: String,//吉神宜趋
                  val jieri: String,
                  val ji: String,
                  val jrhh: String,//今日合害
                  val t5: String,//卯时 5-7
                  val t7: String,//辰时 7-9
                  val t9: String,//巳时 9-11
                  val chongsha: String,
                  val yi: String)



