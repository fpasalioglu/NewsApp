package com.furkanpasalioglu.newsapp.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanpasalioglu.newsapp.R
import com.furkanpasalioglu.newsapp.databinding.ItemNewsBinding
import com.furkanpasalioglu.newsapp.models.Article
import com.squareup.picasso.Picasso

class NewsViewHolder(view: View, private val onItemClicked: (position: Int) -> Unit)
    : RecyclerView.ViewHolder(view), View.OnClickListener {

    private val binding: ItemNewsBinding = ItemNewsBinding.bind(itemView)

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(news: Article?) {
        if (news != null) {
            binding.newsName.text = news.title
            binding.newsDesc.text = news.description
            binding.newsDate.text = news.publishedAt
            binding.newsAuthor.text = news.author
            Picasso.get().load(news.urlToImage).into(binding.newsImage)
        }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClicked: (position: Int) -> Unit): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
            return NewsViewHolder(view, onItemClicked)
        }
    }

    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }
}