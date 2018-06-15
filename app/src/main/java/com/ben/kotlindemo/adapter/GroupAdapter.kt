package com.ben.kotlindemo.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.ben.kotlindemo.R
import com.ben.kotlindemo.bean.Group

class GroupAdapter(val context: Context, private val listGroup: List<Group>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    private var isShowGroupItem = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (listGroup.isEmpty()) 0 else listGroup.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.groupTitle.text = listGroup[position].title
        //创建默认的线性LayoutManager
        val manager = LinearLayoutManager(context)
        //显示底部位置
        manager.stackFromEnd = true
        holder.groupRecyclerView.layoutManager = manager
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        holder.groupRecyclerView.setHasFixedSize(true)
        val childAdapter = ChildAdapter(listGroup[position].groupItems)
        //列表添加分割线
//        holder.groupRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        holder.groupRecyclerView.adapter = childAdapter

        //初始化列表形式
        holder.groupRecyclerView.visibility = View.GONE
        holder.groupImage.setImageResource(R.mipmap.group_open)
        isShowGroupItem = false

        childAdapter.addOnItemClickListener(object : ChildAdapter.OnRecyclerItemClickListener {
            override fun onItemClick(view: View, data: List<*>, position: Int) {
//                Toast.makeText(context, "_(:з」∠)_", Toast.LENGTH_SHORT).show()
            }
        })

        holder.groupLayout.setOnClickListener {
            if (!isShowGroupItem) {
                holder.groupRecyclerView.visibility = View.VISIBLE
                holder.groupImage.setImageResource(R.mipmap.group_close)
                isShowGroupItem = true
            } else {
                holder.groupRecyclerView.visibility = View.GONE
                holder.groupImage.setImageResource(R.mipmap.group_open)
                isShowGroupItem = false
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val groupTitle = view.findViewById<TextView>(R.id.group_title)!!
        val groupRecyclerView = view.findViewById<RecyclerView>(R.id.rl_group)!!
        val groupLayout = view.findViewById<RelativeLayout>(R.id.group_layout)!!
        val groupImage = view.findViewById<ImageView>(R.id.group_image)!!
    }
}