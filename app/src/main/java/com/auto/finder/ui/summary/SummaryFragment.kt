package com.auto.finder.ui.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.auto.finder.R
import com.auto.finder.databinding.SummaryFragmentBinding
import com.auto.finder.utils.*

class SummaryFragment : Fragment() {

    companion object {
        fun newInstance() = SummaryFragment()
    }

    private val viewModel: SummaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // data binding is used
        val binding: SummaryFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.summary_fragment, container, false)

        viewModel.apply {
            arguments?.run {
                manufacturer = getString(MANUFACTURER, "")
                manufacturerKey = getString(MANUFACTURER_KEY, "")

                mainType = getString(MAIN_TYPE, "")
                mainTypeKey = getString(MAIN_TYPE_KEY, "")

                date = getString(BUILD_DATE, "")
                dateKey = getString(BUILD_DATE_KEY, "")
                binding.tvSummary.text = String.format(getString(R.string.selected_date, manufacturer, mainType, date))
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //enabling the home back button
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.summary_title)
        }
    }

}