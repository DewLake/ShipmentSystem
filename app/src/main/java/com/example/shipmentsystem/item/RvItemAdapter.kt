package com.example.shipmentsystem.item

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemViewBinding

class RvItemAdapter : RecyclerView.Adapter<RvItemAdapter.MyHolder>() {
    private val innerItemList = mutableListOf<Item>()

    var itemClickListener: (Item) -> Unit = {}
    private val itemSelectedColor = "#F57C00"       // 讚!
    private val itemDefaultColor = "#FFAB91"

    // 記錄點擊的項目位置
    private var mClickPosition = RecyclerView.NO_POSITION


    inner class MyHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId
//        var clickedPosition = RecyclerView.NO_POSITION        // 改由 Adapter 來管理

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myHolder = MyHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        myHolder.itemView.setOnClickListener {
            val pos = myHolder.adapterPosition
            Log.i("onItemClickLn", "position: $pos")

            itemClickListener.invoke(innerItemList[pos])

            // 記錄點擊位置
            this.mClickPosition = pos

            // 通知畫面更新
            this.notifyDataSetChanged()
        }


        return myHolder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = innerItemList[position]
        holder.id.text = currentItem.id.toString()
        holder.name.text = currentItem.name
        holder.price.text = "$ " + currentItem.price.toString()
//        holder.price.text = "$ ${currentItem.price}"          // 可以嘗試 String template 語法糖

        // 改變點擊項目的背景色
        if (mClickPosition == position)
            holder.itemView.setBackgroundColor(Color.parseColor(itemSelectedColor))
        else
            holder.itemView.setBackgroundColor(Color.parseColor(itemDefaultColor))
    }


    override fun getItemCount(): Int = innerItemList.size

    fun update(updateList: List<Item>) {
        innerItemList.clear()
        innerItemList.addAll(updateList)
        this.notifyDataSetChanged()
    }
}