package com.ben.kotlindemo.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ben.kotlindemo.R
import kotlinx.android.synthetic.main.dialog_calendar.*

class CalendarDialog(context: Context?, closeListener: CalendarDialog.OnCloseListener,
                     nongliString: String, ganzhiString: String, jieqiString: String, jieriString: String,
                     shengxiaoString: String, xingzuoString: String)
    : Dialog(context) {
    var nongli = nongliString
    var ganzhi = ganzhiString
    var jieqi = jieqiString
    var jieri = jieriString
    var shengxiao = shengxiaoString
    var xingzuo = xingzuoString
    private var listener: OnCloseListener? = closeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_calendar)
        setCanceledOnTouchOutside(true)
        initView()
    }

    private fun initView() {
        nongliXml.text = nongli.replace(" ", "\n")
        ganzhiXml.text = ganzhi
        jieqiXml.text = jieqi.replace(" ", "\n")
        jieriXml.text = jieri
        shengxiaoXml.text = String.format(context.getString(R.string.shengxiao_detail), shengxiao)
        xingzuoXml.text = String.format(context.getString(R.string.xingzuo_detail), xingzuo)
        btn_close.setOnClickListener {
            listener?.onClick(this, false)
            this.dismiss()
        }
    }

    interface OnCloseListener {

        fun onClick(dialog: Dialog, confirm: Boolean)
    }
}