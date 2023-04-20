package com.example.thigk2

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thigk2.R
import com.example.thigk2.modelthem

class listview (private val list: List<modelthem>):RecyclerView.Adapter<listview.ViewHolder>() {

    // item listener
    private lateinit var listener: onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        listener = clickListener
    }

    /*------------------------------------------------------------------------------------------------------*/
    class ViewHolder(view: View, clickListener: onItemClickListener):RecyclerView.ViewHolder(view){
        val data : TextView = view.findViewById(R.id.txtDataItem)
        val img : ImageView = view.findViewById(R.id.imageView)
        init {
            view.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_listview,parent,false)
        return ViewHolder(itemView,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = holder.itemView.context as Activity
        holder.data.setText("Tên sản phẩm: "+list[position].tensp)
        Glide.with(activity)
            .load(list[position].linkimg.toString())
            .into(holder.img)


    }

    override fun getItemCount(): Int {
        return list.size
    }
}