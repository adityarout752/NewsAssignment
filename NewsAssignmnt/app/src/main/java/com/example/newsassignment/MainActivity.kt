package com.example.newsassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsassignment.adapter.NewsAdapter
import com.example.newsassignment.modelData.Article
import com.example.newsassignment.remote.Resource
import com.example.newsassignment.repository.NewsRepository
import com.example.newsassignment.ui.DetailedNewsActivity
import com.example.newsassignment.viewModel.NewsViewModel
import com.example.newsassignment.viewModel.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
lateinit var viewModel:NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        setUpRecyclerView()


        SetupViewModel()
        newsAdapter.setOnItemClickListener {




        val  intent=Intent(this@MainActivity,DetailedNewsActivity::class.java)
        intent.putExtra("urls",it.url)
        startActivity(intent)
        }




    }



    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun SetupViewModel(){
viewModel.breakingNews.observe(this, Observer { response->
    when (response) {
        is Resource.Success -> {
            hideProgressBar()
            response.data?.let { newsResponse ->
                newsAdapter.differ.submitList(newsResponse.articles)

            }
        }
        is Resource.Error -> {
            hideProgressBar()
            response.message?.let { message ->
                Log.e(TAG, "error is:$message")

            }
        }
        is Resource.Loading -> {
            showProgressBar()
        }
    }

})
    }

    private fun hideProgressBar() {
        ProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        ProgressBar.visibility = View.VISIBLE
    }
}

