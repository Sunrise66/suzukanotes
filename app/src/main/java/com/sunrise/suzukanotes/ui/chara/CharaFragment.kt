package com.sunrise.suzukanotes.ui.chara

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunrise.suzukanotes.R
import com.sunrise.suzukanotes.databinding.CharaFragmentBinding

class CharaFragment : Fragment() {

    companion object {
        fun newInstance() = CharaFragment()
    }

    private lateinit var binding: CharaFragmentBinding
    private val viewModel: CharaViewModel by lazy {
        ViewModelProvider(this)[CharaViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharaFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}