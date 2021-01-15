package com.demirkiran.baseApp.ui.Adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.demirkiran.baseApp.R

import com.bumptech.glide.Glide
import com.demirkiran.baseApp.data.Picture
import com.squareup.picasso.Picasso
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.android.synthetic.main.fragment_picture.view.*
import javax.inject.Singleton

class MyPictureRecyclerViewAdapter  : RecyclerView.Adapter<MyPictureRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val differCallback = object : DiffUtil.ItemCallback<Picture>() {
        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_picture, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = differ.currentList[position]
        holder.itemView.apply {
            val imageUrl = picture.img_src
            Glide.with(holder.itemView.context).load(imageUrl).into(holder.itemView.user_photo);
            user_name.text = "${picture.camera.full_name}"

        }
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.itemView.user_photo);

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(picture)
        }
    }

    private var onItemClickListener: ((Picture) -> Unit)? = null

    fun setOnItemClickListener(listener: (Picture) -> Unit) {
        onItemClickListener = listener
    }
}