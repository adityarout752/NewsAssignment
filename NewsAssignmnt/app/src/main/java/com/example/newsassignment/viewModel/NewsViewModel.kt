package com.example.newsassignment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsassignment.modelData.NewsResponse
import com.example.newsassignment.remote.Resource
import com.example.newsassignment.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(var newsRepository: NewsRepository) :ViewModel(){
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    init{
        getBreakingNews("us")
    }
    fun getBreakingNews(country:String)=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response=newsRepository.getBreakingNews(country)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}