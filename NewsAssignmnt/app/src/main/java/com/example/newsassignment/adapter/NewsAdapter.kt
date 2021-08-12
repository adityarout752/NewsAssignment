package com.example.newsassignment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsassignment.R
import com.example.newsassignment.modelData.Article
import com.example.newsassignment.ui.DetailedNewsActivity
import kotlinx.android.synthetic.main.item_layout.view.*


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    private val differCallbacks=object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }
    //now async list differ calculates difference between two lists and only update those
    val differ= AsyncListDiffer(this,differCallbacks)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
return NewsViewHolder(LayoutInflater.from(parent.context).inflate(
    R.layout.item_layout,parent,false
))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article=differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)

            tvTitle.text=article.title
            tvDescription.text=article.description
            onItemClickListener?.let{
                it(article)
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemClickListener:((Article)->Unit)?=null
    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener=listener
    }
}