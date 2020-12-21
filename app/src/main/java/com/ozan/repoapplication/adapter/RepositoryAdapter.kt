package com.ozan.repoapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.ozan.repoapplication.R
import com.ozan.repoapplication.classes.RepoResponse
import javax.inject.Inject


class RepositoryAdapter @Inject constructor(private var list: List<RepoResponse>) :
    RecyclerView.Adapter<RepositoryAdapter.MyViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var onItemImageClick: ((Int) -> Unit)? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var desc: TextView = view.findViewById(R.id.txt_des)
        var image: ImageButton = view.findViewById(R.id.image)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }

            image.setOnClickListener {

                onItemImageClick?.invoke(adapterPosition)

            }
        }
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = list[position]

        holder.title.text = product.name
        holder.desc.text = product.description

        holder.image.setImageResource(android.R.drawable.star_big_off)

        if (product.isStarred){

            holder.image.setImageResource(android.R.drawable.star_big_on)

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(list: List<RepoResponse>) {
        this.list = list
    }
}