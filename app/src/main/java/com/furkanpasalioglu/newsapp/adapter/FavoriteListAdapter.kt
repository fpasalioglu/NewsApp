package com.furkanpasalioglu.newsapp.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.furkanpasalioglu.newsapp.holders.NewsViewHolder
import com.furkanpasalioglu.newsapp.models.Article

class FavoriteListAdapter(private val onItemClicked: (position: Int) -> Unit)
    : PagedListAdapter<Article, RecyclerView.ViewHolder>(NewsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder.create(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsViewHolder).bind(getItem(position))
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun notifyChanged() {
        notifyItemChanged(super.getItemCount())
    }
}