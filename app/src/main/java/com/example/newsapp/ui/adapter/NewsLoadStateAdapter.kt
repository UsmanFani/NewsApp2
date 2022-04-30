package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.LoadStateItemBinding

class NewsLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<NewsLoadStateAdapter.NewsLoadStateViewHolder>() {

    class NewsLoadStateViewHolder(private val binding: LoadStateItemBinding,private val retry: () -> Unit ):RecyclerView.ViewHolder(binding.root){

        init {
            binding.retryBtn.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState){
            binding.apply {
                progressCircular.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState is LoadState.Error
                errorTv.isVisible = loadState is LoadState.Error
                if(loadState is LoadState.Error){
                    errorTv.text = loadState.error.localizedMessage
                }
            }

        }
    }
    override fun onBindViewHolder(holder: NewsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NewsLoadStateViewHolder {
        return NewsLoadStateViewHolder(LoadStateItemBinding.inflate(LayoutInflater
            .from(parent.context),parent,false)){
            retry()
        }
    }

}