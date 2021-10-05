package com.furkanpasalioglu.newsapp.ui.detay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.furkanpasalioglu.newsapp.R
import com.furkanpasalioglu.newsapp.database.RoomDb
import com.furkanpasalioglu.newsapp.database.dao.ArticlesDao
import com.furkanpasalioglu.newsapp.databinding.ActivityDetayBinding
import com.furkanpasalioglu.newsapp.models.Article
import com.furkanpasalioglu.newsapp.ui.webview.WebViewActivity
import com.squareup.picasso.Picasso

import androidx.core.content.ContextCompat

class DetayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetayBinding
    private lateinit var article : Article
    private lateinit var articlesDao : ArticlesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = RoomDb.getDatabase(application)
        articlesDao = db?.articlesDao()!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        article = intent.getSerializableExtra("article") as Article

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

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (articlesDao.getNews(article.title))
            menu.getItem(0).icon = ContextCompat.getDrawable( this, R.drawable.ic_baseline_favorite_24)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detay_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_fav -> {
                if (articlesDao.getNews(article.title)){
                    item.icon = ContextCompat.getDrawable( this, R.drawable.ic_baseline_favorite_border_24)
                    articlesDao.delete(article.title)
                }else{
                    item.icon = ContextCompat.getDrawable( this, R.drawable.ic_baseline_favorite_24)
                    articlesDao.insert(article)
                }
                true
            }

            R.id.action_share -> {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                share.putExtra(Intent.EXTRA_SUBJECT, article.title)
                share.putExtra(Intent.EXTRA_TEXT, article.title + " haberinin linki: \n" + article.url)
                startActivity(Intent.createChooser(share, "Haberi Paylaş"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}