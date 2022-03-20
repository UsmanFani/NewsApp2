package com.example.newsapp.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ArticlePreviewBinding
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    //using viewBinding to get the view
    inner class ArticleViewHolder(itemView: ArticlePreviewBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val imView = itemView.articleIv
        val tvSource = itemView.sourceTv
        val tvTitle = itemView.titleTv
       // val tvDescription = itemView.descriptionTv
        val tvPublishedAt = itemView.publishedAtTv
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        return ArticleViewHolder(
            ArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.imView)
           // holder.tvDescription.text = article.description
            holder.tvTitle.text = article.title
            holder.tvSource.text = article.source?.name
            holder.tvSource.typeface= Typeface.SERIF
            holder.tvPublishedAt.text = article.publishedAt
            setOnClickListener {
                onItemClickListner?.let {
                    it(article)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //Using DiffUtil to compare two items
    //Basically using for compare the item so same items don't attach again
    //in adapter
    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    //AsyncListDiffer used to differentiate the list in background thread
    //and get position of item in adapter
    val differ = AsyncListDiffer(this, differCallBack)

    private var onItemClickListner: ((Article) -> Unit)? = null

    fun setOnItemClickListner(listner: (Article) -> Unit) {
        onItemClickListner = listner
    }
}