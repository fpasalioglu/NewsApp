package com.furkanpasalioglu.newsapp.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.furkanpasalioglu.newsapp.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val url = intent.getStringExtra("url")

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url!!)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        else
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}