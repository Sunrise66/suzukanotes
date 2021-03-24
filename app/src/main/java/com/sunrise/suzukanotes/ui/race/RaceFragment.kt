package com.sunrise.suzukanotes.ui.race

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunrise.suzukanotes.R

class RaceFragment : Fragment() {

    companion object {
        fun newInstance() = RaceFragment()
    }

    private lateinit var viewModel: RaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.race_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RaceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}