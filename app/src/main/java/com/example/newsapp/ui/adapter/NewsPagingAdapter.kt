package com.example.newsapp.ui.adapter

import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ArticlePreviewBinding
import com.example.newsapp.model.Article
import com.example.newsapp.util.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.Duration.Companion.nanoseconds

class NewsPagingAdapter : PagingDataAdapter<Article, NewsPagingAdapter.ArticleViewHolder>(Diff) {

    class ArticleViewHolder(private val binding: ArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                Glide.with(binding.root)
                    .load(article.urlToImage)
                    .fitCenter().centerCrop()
                    .placeholder(R.drawable.ic_baseline_broken_image_24)
                    .into(articleIv)
                titleTv.text = article.title
                sourceTv.text = article.source?.name
                publishedAtTv.text = Constants().convertTime(article.publishedAt)
                descTv?.text = article.description
            }
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClickListner?.let {
                if (item != null) {
                    it(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    object Diff : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }


    private var onItemClickListner: ((Article) -> Unit)? = null

    fun setOnItemClickListner(listner: (Article) -> Unit) {
        onItemClickListner = listner
    }

}