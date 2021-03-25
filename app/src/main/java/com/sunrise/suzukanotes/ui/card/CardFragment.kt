package com.sunrise.suzukanotes.ui.card

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunrise.suzukanotes.R
import com.sunrise.suzukanotes.databinding.CardFragmentBinding

class CardFragment : Fragment() {

    companion object {
        fun newInstance() = CardFragment()
    }

    private val viewModel: CardViewModel by lazy {
        ViewModelProvider(this)[CardViewModel::class.java]
    }

    private lateinit var binding: CardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CardFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}