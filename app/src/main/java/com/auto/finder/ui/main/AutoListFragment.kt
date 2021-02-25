package com.auto.finder.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.auto.finder.R
import com.auto.finder.databinding.AutoListFragmentBinding
import com.auto.finder.ui.OnRecyclerItemClickListener
import com.auto.finder.ui.main.adapter.AutoListRecyclerViewAdapter
import com.auto.finder.ui.main.adapter.LoadMoreListener
import com.auto.finder.ui.types.MainTypesFragment
import com.auto.finder.utils.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * [AutoListFragment] Landing screen to show list of manufacturers
 */
@AndroidEntryPoint
class AutoListFragment : Fragment(), OnRecyclerItemClickListener,
    LoadMoreListener.OnLoadMoreListener {

    private val viewModel: AutoListViewModel by viewModels()
    private lateinit var binding: AutoListFragmentBinding
    private lateinit var adapter: AutoListRecyclerViewAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var loadMoreListener: LoadMoreListener
    private var pageCount = 0
    private var autoNameList = arrayListOf<String>()
    private var manufacturerList = mapOf<String, String>()

    companion object {
        fun newInstance() = AutoListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // data binding is used
        binding = DataBindingUtil.inflate(inflater, R.layout.auto_list_fragment, container, false)

        adapter = AutoListRecyclerViewAdapter()
        adapter.setOnItemClickListener(this)

        binding.apply {
            layoutManager = LinearLayoutManager(requireContext())
            autoManufacturerRv.layoutManager = layoutManager
            autoManufacturerRv.adapter = adapter

            // Specify the current fragment as the lifecycle owner of the binding.
            // This is necessary so that the binding can observe updates.
            binding.lifecycleOwner = this@AutoListFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //disabling the home back button
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = getString(R.string.manufacturer_title)
        }
        setupPagination()
        setupObserver()
    }

    /**
     * Observe update on view model live data
     */
    private fun setupObserver() {
        viewModel.auto.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { auto ->
                        val list = arrayListOf<String>()
                        auto.wkda?.let { it1 ->
                            list.addAll(it1.values)
                            manufacturerList = it1
                        }
                        autoNameList = list

                        if (autoNameList.isNotEmpty()) {
                            adapter.updateAutoList(autoNameList)
                            setScrollVariablesStatus()

                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.no_data_found),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                }

                Status.LOADING -> {
                    Logger.d("AutoListFragment LOADING", "LOADING")
                    binding.progressBar.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    //Handle Error
                    Logger.d("AutoListFragment ERROR", it.message.toString())
                    binding.progressBar.visibility = View.GONE

                    handleAPIFail(requireActivity() as AppCompatActivity)

                }
            }
        })
    }

    override fun onItemClick(item: View?, auto: String, position: Int) {
        requireActivity().run {

            replaceWithNextFragment(
                this@AutoListFragment.id,
                supportFragmentManager,
                MainTypesFragment.newInstance(),
                Bundle().apply {
                    putString(MANUFACTURER, auto)
                    putString(
                        MANUFACTURER_KEY,
                        manufacturerList.getKeyFromValue(auto)
                    )
                }
            )
        }
    }

    /**
     * setting up scroll listener on recycler view
     */
    private fun setupPagination() {
        loadMoreListener = LoadMoreListener(layoutManager, this)
        loadMoreListener.setLoaded()
        binding.autoManufacturerRv.addOnScrollListener(loadMoreListener)
    }

    /**
     * disabling the progress view and loaded variable
     */
    private fun setScrollVariablesStatus() {
        loadMoreListener.setLoaded()
    }

    override fun onLoadMore() {
        activity?.let {
            // fetching only fewer elements inorder to manage the data consumption for load more only
            viewModel.fetchManufacturerList(pageCount++, PAGE_SIZE, WA_KEY)
        }
    }

}