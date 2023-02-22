package com.example.restapiexample.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restapiexample.SecondActivity
import com.example.restapiexample.UpdateActivity
import com.example.restapiexample.databinding.ItemLyBinding
import com.example.restapiexample.models.User
import com.squareup.picasso.Picasso

class UserAdapter: RecyclerView.Adapter<UserAdapter.ItemHolder>() {

    inner class ItemHolder(val b : ItemLyBinding) : RecyclerView.ViewHolder(b.root){

        fun bind(itemData: User){
            Picasso.get().load(itemData.picture).into(b.img)

            b.text1.text = itemData.firstName
            b.text2.text = itemData.lastName
            itemView.setOnClickListener {
                val context = itemView.context
                Intent(context, SecondActivity::class.java).run {
                    putExtra("user_id", itemData.id)
                    context.startActivity(this)
                }
            }
            b.updateBtn.setOnClickListener {
                val context = itemView.context
                Intent(context, UpdateActivity::class.java).run {
                    putExtra("update_key", itemData)
                    context.startActivity(this)
                }
            }
            b.deleteBtn.setOnClickListener {
                onItemClickListener?.invoke(itemData.id!!)
            }
        }
    }
    private var onItemClickListener: ((String) -> Unit)? = null
    fun setOnItemClickListener(listener: ((String) -> Unit)? = null) {
        onItemClickListener = listener
    }
    var baseList = emptyList<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ItemLyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return baseList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baseList[position]
        holder.bind(itemData)
    }
}