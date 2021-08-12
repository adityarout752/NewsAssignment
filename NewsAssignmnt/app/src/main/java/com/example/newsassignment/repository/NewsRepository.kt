package com.example.newsassignment.repository

import com.example.newsassignment.remote.RetrofitInstance


class NewsRepository (){

    suspend fun getBreakingNews(country:String)=
        RetrofitInstance.api.getBreakingNews(country)


}