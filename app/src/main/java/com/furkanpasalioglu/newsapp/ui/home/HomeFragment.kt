package com.furkanpasalioglu.newsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanpasalioglu.newsapp.R
import com.furkanpasalioglu.newsapp.adapter.NewsListAdapter
import com.furkanpasalioglu.newsapp.databinding.FragmentHomeBinding
import com.furkanpasalioglu.newsapp.di.NetworkModule.query
import com.furkanpasalioglu.newsapp.enums.State
import com.furkanpasalioglu.newsapp.ui.detay.DetayActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var newsListAdapter: NewsListAdapter
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun initAdapter() {
        newsListAdapter = NewsListAdapter{ position -> onListItemClick(position) }
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = newsListAdapter
        homeViewModel.newsList.observe(this, {
            newsListAdapter.submitList(it)
        })
    }

    private fun initState() {
        homeViewModel.getState().observe(this, { state ->
            if (homeViewModel.listIsEmpty() && state == State.LOADING){
                binding.progressBar.visibility = View.VISIBLE
                binding.txtInfo.visibility = View.GONE
            } else
                binding.progressBar.visibility = View.GONE

            if (homeViewModel.listIsEmpty() && state == State.ERROR) {
                binding.txtError.visibility = View.VISIBLE
                binding.txtInfo.visibility = View.GONE
            } else
                binding.txtError.visibility = View.GONE

            if (!homeViewModel.listIsEmpty()) {
                newsListAdapter.notifyChanged()
            }
        })
    }

    private fun onListItemClick(position: Int) {
        val intent = Intent(context, DetayActivity::class.java).apply {
            putExtra("article", homeViewModel.newsList.value?.get(position))
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu);
        val item = menu.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query2: String?): Boolean {
                if (query2 != null) {
                    query = query2

                    initAdapter()
                    initState()
                }

                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}