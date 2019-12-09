package com.miklesam.openpunkapi.adapters

import androidx.recyclerview.widget.DiffUtil
import com.miklesam.openpunkapi.data.Beer

class DiffCallback(oldList: List<Beer>, newList: List<Beer>): DiffUtil.Callback() {
    private val oldList = oldList.toList()
    private val newList = newList.toList()
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldList[oldPos] == newList[newPos]
    }
    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldList[oldPos] == newList[newPos]
    }
    override fun getChangePayload(oldPos: Int, newPos: Int): Any? {
        return super.getChangePayload(oldPos, newPos)
    }
}