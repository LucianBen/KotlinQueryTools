package com.ben.kotlindemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.ben.kotlindemo.R
import kotlinx.android.synthetic.main.activity_oneiromancy_resolve.*

class OneiromancyResolveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oneiromancy_resolve)

        initView()

    }

    private fun initView() {
        toolbar_resolve.title = intent.getStringExtra("title")
        toolbar_resolve.setNavigationOnClickListener { finish() }
        val content = intent.getStringArrayListExtra("content")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, content)
        listview_resolve_content.adapter = arrayAdapter
    }


}
