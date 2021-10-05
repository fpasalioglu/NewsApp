package com.furkanpasalioglu.newsapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanpasalioglu.newsapp.adapter.FavoriteListAdapter
import com.furkanpasalioglu.newsapp.databinding.FragmentDashboardBinding
import com.furkanpasalioglu.newsapp.ui.detay.DetayActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val dashboardViewModel by activityViewModels<FavoriteViewModel>()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = FavoriteListAdapter{ position -> onListItemClick(position) }
        dashboardViewModel.newsList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.bilgiLayout.visibility = View.GONE
            if (it.isEmpty()){
                binding.bilgiLayout.visibility = View.VISIBLE
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onListItemClick(position: Int) {
        val intent = Intent(context, DetayActivity::class.java).apply {
            putExtra("article", dashboardViewModel.newsList.value?.get(position))
        }
        startActivity(intent)
    }
}