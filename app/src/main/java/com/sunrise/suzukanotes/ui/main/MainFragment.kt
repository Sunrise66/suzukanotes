package com.sunrise.suzukanotes.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.sunrise.suzukanotes.R
import com.sunrise.suzukanotes.base.TabAdapter
import com.sunrise.suzukanotes.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private val adapter by lazy {
        TabAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.vm = viewModel
        setTabClickListener()
    }

    private fun switchFragments(index: Int) {
        val tag = index.toString()
        val transaction = childFragmentManager.beginTransaction()
        childFragmentManager.findFragmentById(R.id.frame_container)?.let {
            transaction.detach(it)
        }
        var fragment = childFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = adapter.createFragment(index)
            transaction.add(R.id.frame_container, fragment, tag)
        } else {
            transaction.attach(fragment)
        }
    }

    private fun setTabClickListener() {
        val btnCard = binding.btnCard
        val btnChara = binding.btnChara
        val btnRace = binding.btnRace
        btnCard.setOnClickListener {
            viewModel.apply {
                btnCardClicked.value = true
                btnCharClicked.value = false
                btnRaceClicked.value = false
            }
            it.setBackgroundResource(R.drawable.tab_bottom_left_on)
            btnChara.setBackgroundResource(R.drawable.tab_bottom_middle_off)
            btnRace.setBackgroundResource(R.drawable.tab_bottom_right_off)
            switchFragments(0)
        }
        btnChara.setOnClickListener {
            viewModel.apply {
                btnCharClicked.value = true
                btnRaceClicked.value = false
                btnCardClicked.value = false
            }
            it.setBackgroundResource(R.drawable.tab_bottom_middle_on)
            btnCard.setBackgroundResource(R.drawable.tab_bottom_left_off)
            btnRace.setBackgroundResource(R.drawable.tab_bottom_right_off)
            switchFragments(1)
        }
        btnRace.setOnClickListener {
            viewModel.apply {
                btnRaceClicked.value = true
                btnCharClicked.value = false
                btnCardClicked.value = false
            }
            it.setBackgroundResource(R.drawable.tab_bottom_right_on)
            btnCard.setBackgroundResource(R.drawable.tab_bottom_left_off)
            btnChara.setBackgroundResource(R.drawable.tab_bottom_middle_off)
            switchFragments(2)
        }
        btnChara.performClick()
    }

}