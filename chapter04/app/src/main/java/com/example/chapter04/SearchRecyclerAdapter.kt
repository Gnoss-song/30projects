package com.example.chapter04

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter04.databinding.ViewholderSearchResultItemBinding

class SearchRecyclerAdapter :RecyclerView.Adapter<SearchRecyclerAdapter.SearchResultItemViewHolder>(){

    private val searchResultList : List<Any> = listOf()
    lateinit var searchResultClickListener: (Any) -> Unit

    inner class SearchResultItemViewHolder(val binding : ViewholderSearchResultItemBinding,val searchResultClickListener : (Any) -> Unit) : RecyclerView.ViewHolder(itemView){

        fun bindData(data:Any) = with(binding){
            textTextView.text = "제목"
            subtextTextView.text = "부제목"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultItemViewHolder{
        val view = ViewholderSearchResultItemBinding.bind(parent)
        return SearchResultItemViewHolder(view,searchResultClickListener)
    }

    override fun onBindViewHolder(holder: ???, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}