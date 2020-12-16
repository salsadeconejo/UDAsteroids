package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.AsteroidFilter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        MainViewModelFactory(requireContext().applicationContext)
            .create(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.AsteroidClickListener {
            viewModel.displayDetailScreen(it)
        })

        viewModel.refreshImageOfTheDay()
        viewModel.refreshList()

        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.doneNavigatingToDetail()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.refreshFilter(
            when (item.itemId) {
                R.id.show_buy_menu -> {
                    AsteroidFilter.FAVORITE
                }
                R.id.show_rent_menu -> {
                    AsteroidFilter.TODAY
                }
                else -> {
                    AsteroidFilter.ALL
                }
            }
        )

        return true
    }
}
