package com.ben.kotlindemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.ben.kotlindemo.R
import com.ben.kotlindemo.bean.Child

class ChildAdapter(private val listChild: List<Child>) : RecyclerView.Adapter<ChildAdapter.ViewHolder>(), View.OnClickListener {


    private var mOnItemClickListener: OnRecyclerItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (listChild.isEmpty()) 0 else listChild.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTitle.text = listChild[position].title

        holder.mStar.visibility = if (listChild[position].typeLayout == "1") View.VISIBLE else View.GONE
        holder.mStar.rating = if (listChild[position].typeLayout == "1") listChild[position].content.toFloat() else 0f
        holder.mContent.visibility = if (listChild[position].typeLayout == "1") View.GONE else View.VISIBLE
        holder.mContent.text = listChild[position].content

        holder.itemView.tag = position
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTitle: TextView = view.findViewById(R.id.title)
        val mContent: TextView = view.findViewById(R.id.content)
        val mStar: RatingBar = view.findViewById(R.id.ratingBar)


    }

    override fun onClick(v: View) {
        if (mOnItemClickListener != null) {
        }
        mOnItemClickListener!!.onItemClick(v, listChild, v.tag as Int)
    }

    fun addOnItemClickListener(listener: OnRecyclerItemClickListener) {
        mOnItemClickListener = listener
    }

    interface OnRecyclerItemClickListener {
        fun onItemClick(view: View, data: List<*>, position: Int)
    }
}

