package com.ben.kotlindemo.bean

data class Oneiromancy(val showapi_res_error: String,
                       val showapi_res_code: String,
                       val showapi_res_body: OneiromancyResult)

data class OneiromancyResult(val allPages: String,
                             val ret_code: String,
                             val contentlist: List<ContentList>,
                             val currentPage: String,
                             val allNum: String,
                             val maxResult: String)

data class ContentList(val name:String,
                       val detailList:List<String>)