package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.LoadStateItemBinding

class NewsLoadStateAdapter(private val retry:()->Unit): LoadStateAdapter<NewsLoadStateAdapter.NewsLoadStateViewHolder>() {

    class NewsLoadStateViewHolder(private val binding: LoadStateItemBinding,retry: () -> Unit ):RecyclerView.ViewHolder(binding.root){

        private val retryBtn:Button = binding.retryBtn.also {
            retry()
        }
        fun bind(loadState: LoadState){
            binding.apply {
                if(loadState is LoadState.Error){
                    errorTv.text = loadState.error.localizedMessage
                }
                progressCircular.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState is LoadState.Error
                errorTv.isVisible = loadState is LoadState.Error

            }

        }
    }
    override fun onBindViewHolder(holder: NewsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NewsLoadStateViewHolder {
        return NewsLoadStateViewHolder(LoadStateItemBinding.inflate(LayoutInflater
            .from(parent.context),parent,false),retry)
    }

}