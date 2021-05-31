package com.example.newsfresh

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.newsfresh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)
        addAdapter()
        fetchData()
    }

    private fun addAdapter(){
        mAdapter = NewsListAdapter(this)
        val newsRecyclerView:RecyclerView = binding.newsRecyclerView
        newsRecyclerView.adapter = mAdapter
    }

    private fun fetchData(){
//        val url = "https://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=API_KEY=2c2d1bc386b149c292b0cf66c4b97426"
//        val url = "GET https://gnews.io/api/v4/top-headlines?token=6a9d993214bdaa9cf836e6700e6db590"

        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
//
//        edit the link , just replace the health by the following categories:
//        business entertainment general health science sports technology


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val title: String = newsJsonObject.getString("title")
                    val author: String = newsJsonObject.getString("author")
                    val currentUrl: String = newsJsonObject.getString("url")
                    val urlToImage: String = newsJsonObject.getString("urlToImage")
                    val news = News(title, author, currentUrl, urlToImage)
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            {

            }
        )
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClick(item: News) {
        val url: String = item.url;
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent  = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}