package com.yamikrish.app.rxjavademo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yamikrish.app.rxjavademo.R
import com.yamikrish.app.rxjavademo.model.UserModel
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerAdapter(val context: Context, var data : ArrayList<UserModel>?) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItems(data?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return Holder(v)
    }

    override fun getItemCount(): Int = data?.size?:0

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(user: UserModel?){
            itemView.name.text =  user?.name
            itemView.email.text = user?.email
        }
    }

}