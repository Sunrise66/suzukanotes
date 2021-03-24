package com.sunrise.suzukanotes.ui.chara

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunrise.suzukanotes.R

class CharaFragment : Fragment() {

    companion object {
        fun newInstance() = CharaFragment()
    }

    private lateinit var viewModel: CharaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chara_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}