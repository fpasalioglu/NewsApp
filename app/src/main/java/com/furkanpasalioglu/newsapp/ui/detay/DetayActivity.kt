package com.furkanpasalioglu.newsapp.ui.detay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.furkanpasalioglu.newsapp.databinding.ActivityDetayBinding
import com.furkanpasalioglu.newsapp.models.Article
import com.furkanpasalioglu.newsapp.ui.web.WebViewActivity
import com.squareup.picasso.Picasso

class DetayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val article : Article = intent.getSerializableExtra("article") as Article

        Picasso.get().load(article.urlToImage).into(binding.newsImage)
        title = article.author
        binding.newsAuthor.text = article.author
        binding.newsDate.text = article.publishedAt
        binding.newsContent.text = article.content
        binding.newsTitle.text = article.title

        binding.webViewButton.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java).apply {
                putExtra("url", article.url)
            }
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}