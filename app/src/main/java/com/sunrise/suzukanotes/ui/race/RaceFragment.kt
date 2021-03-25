package com.sunrise.suzukanotes.ui.race

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunrise.suzukanotes.R
import com.sunrise.suzukanotes.databinding.RaceFragmentBinding

class RaceFragment : Fragment() {

    companion object {
        fun newInstance() = RaceFragment()
    }

    private val viewModel: RaceViewModel by lazy {
        ViewModelProvider(this)[RaceViewModel::class.java]
    }

    private lateinit var binding: RaceFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RaceFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}