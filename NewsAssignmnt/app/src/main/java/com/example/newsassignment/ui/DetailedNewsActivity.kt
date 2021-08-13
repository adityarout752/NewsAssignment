package com.example.newsassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import com.example.newsassignment.R
import kotlinx.android.synthetic.main.activity_detailed_news.*

class DetailedNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_news)

       val url=intent?.getStringExtra("urls")
        Log.d("adi","$url")
        webView.apply {
            webChromeClient= WebChromeClient()
            if (url != null) {

                loadUrl(url)
            }
        }

    }
}