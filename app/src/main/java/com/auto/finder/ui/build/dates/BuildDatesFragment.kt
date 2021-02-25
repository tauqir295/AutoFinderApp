package com.auto.finder.ui.build.dates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.auto.finder.R
import com.auto.finder.databinding.MainTypesFragmentBinding
import com.auto.finder.ui.OnRecyclerItemClickListener
import com.auto.finder.ui.summary.SummaryFragment
import com.auto.finder.ui.types.adapter.MainTypeListRecyclerViewAdapter
import com.auto.finder.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuildDatesFragment : Fragment(), OnRecyclerItemClickListener {

    companion object {
        fun newInstance() = BuildDatesFragment()
    }

    private val viewModel: BuildDatesViewModel by viewModels()
    private lateinit var binding: MainTypesFragmentBinding
    private lateinit var adapter: MainTypeListRecyclerViewAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var buildDateList = mapOf<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // data binding is used
        binding = DataBindingUtil.inflate(inflater, R.layout.main_types_fragment, container, false)

        adapter = MainTypeListRecyclerViewAdapter()
        adapter.setOnItemClickListener(this)
        binding.apply {
            layoutManager = LinearLayoutManager(requireContext())
            autoMainTypeRv.layoutManager = layoutManager
            autoMainTypeRv.adapter = adapter

            // Specify the current fragment as the lifecycle owner of the binding.
            // This is necessary so that the binding can observe updates.
            binding.lifecycleOwner = this@BuildDatesFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //enabling the home back button
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.date_title)
        }
        viewModel.apply {
            arguments?.run {
                manufacturer = getString(MANUFACTURER, "")
                manufacturerKey = getString(MANUFACTURER_KEY, "")

                mainType = getString(MAIN_TYPE, "")
                mainTypeKey = getString(MAIN_TYPE_KEY, "")
                binding.manufacturerTv.text = String.format(getString(R.string.selected_main_type, manufacturer, mainType))

                manufacturerKey?.let { manufacturer ->
                    mainTypeKey?.let { mainType ->
                        if (auto.value == null) {
                            viewModel.fetchBuildDates(manufacturer, mainType, WA_KEY)
                        } else {
                            filterText?.let {
                                adapter.filter.filter(it) // filter data on device rotation
                            }
                        }
                    }
                }
            }
        }

        setupObserver()
    }

    /**
     * search the list for the key
     */
    private fun searchBuildDate() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterText = newText
                adapter.filter.filter(newText)
                return false
            }
        })
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
                            buildDateList = it1
                        }

                        if (list.isNotEmpty()) {
                            Logger.d("", list.toString())
                            adapter.updateAutoList(list)
                            searchBuildDate()

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
                this@BuildDatesFragment.id,
                supportFragmentManager,
                SummaryFragment.newInstance(),
                Bundle().apply {
                    putString(MANUFACTURER, viewModel.manufacturer)
                    putString(
                        MANUFACTURER_KEY,
                        viewModel.manufacturerKey
                    )

                    putString(MAIN_TYPE, auto)
                    putString(
                        MAIN_TYPE_KEY,
                        viewModel.mainTypeKey
                    )
                    putString(BUILD_DATE, auto)
                    putString(
                        BUILD_DATE_KEY,
                        buildDateList.getKeyFromValue(auto)
                    )
                }
            )
        }
    }
}