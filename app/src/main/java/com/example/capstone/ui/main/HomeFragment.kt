package com.example.capstone.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.data.di.Injection
import com.example.capstone.ui.Adapter.HomeAdapter
import com.example.capstone.ui.main.ViewModelFactory
import com.example.capstone.ui.profile.ProfileActivity


class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnProfile = view.findViewById<ImageView>(R.id.btn_profile)
        btnProfile.setOnClickListener {
            navigateToProfileActivity()
        }

//        Inisiasi Repository Lewat Injection
        val repository = Injection.provideRepository(requireContext())
        val viewModelRepository = ViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelRepository)[HomeViewModel::class.java]

//        Setting Recycler View
        homeAdapter = HomeAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = homeAdapter

//        Jalankan fungsi Obersver
        observerFromViewModel()
        homeViewModel.getAllItem()

    }

    private fun observerFromViewModel() {
        homeViewModel.itemsLiveData.observe(viewLifecycleOwner) {
            homeAdapter.setData(it)
        }
    }

    private fun navigateToProfileActivity() {
        val intent = Intent(this@HomeFragment.requireContext(), ProfileActivity::class.java)
        startActivity(intent)
    }


    companion object {
        fun instance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}




