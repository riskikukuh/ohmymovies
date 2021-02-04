package com.ohmymovies.core.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ohmymovies.core.utils.DiffCallback

abstract class BaseAdapter<T : BaseViewHolder<R>, R>(
    private val diffCallback: DiffCallback<R>,
    private val onClickCallback: (R) -> Unit
) : RecyclerView.Adapter<T>() {
    private val listData: MutableList<R> = mutableListOf()

    fun getList(): List<R> = listData

    fun addList(data: List<R>) {
        diffCallback.setList(listData, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listData) {
            clear()
            addAll(data)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bind(listData[position], onClickCallback)
    }

    override fun getItemCount(): Int = listData.count()
}