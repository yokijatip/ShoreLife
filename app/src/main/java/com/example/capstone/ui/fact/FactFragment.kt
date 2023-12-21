package com.example.capstone.ui.fact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.data.di.Injection
import com.example.capstone.ui.Adapter.FactAdapter

class FactFragment : Fragment() {

    private lateinit var factViewModel: FactViewModel
    private lateinit var factAdapter: FactAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View { return inflater.inflate(R.layout.fragment_fact, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Inisialisasi Repository Lewat Injection
        val repository = Injection.provideRepository(requireActivity())
        val viewModelRepository = ViewModelFactory(repository)
        factViewModel = ViewModelProvider(this, viewModelRepository)[FactViewModel::class.java]

//        Setting dan Inisiasi Recycler View Item
        factAdapter = FactAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_fact)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = factAdapter

//        Jalankan Fungsi Observer
        observerFromViewModel()
        factViewModel.getAllItem()

    }

    //    Fungsi ini berguna buat nge observe alias mengawasi kalau ada data baru, jadi ini nge observe lewat view model
    private fun observerFromViewModel() {
        factViewModel.itemsLiveData.observe(viewLifecycleOwner) {
            factAdapter.setData(it)
        }
    }

    companion object {
        fun instance(): FactFragment {
            val fragment = FactFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}