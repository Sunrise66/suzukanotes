package com.sunrise.suzukanotes.ui.chara

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup
import com.sunrise.suzukanotes.R
import com.sunrise.suzukanotes.databinding.CharaFragmentBinding
import com.sunrise.suzukanotes.databinding.CharaItemBinding
import com.sunrise.suzukanotes.entity.bean.Card
import com.sunrise.suzukanotes.share.CharaShareViewModel
import com.sunrise.suzukanotes.share.CharaShareViewModelFactory

class CharaFragment : Fragment() {

    companion object {
        fun newInstance() = CharaFragment()
    }

    private lateinit var binding: CharaFragmentBinding
    private lateinit var rv: RecyclerView
    private val sharedChara: CharaShareViewModel by lazy {
        ViewModelProvider(requireActivity())[CharaShareViewModel::class.java]
    }
    private val viewModel: CharaViewModel by lazy {
        ViewModelProvider(this, CharaShareViewModelFactory(sharedChara))[CharaViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharaFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        rv = binding.charaList
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
    }

    private fun setObserver() {
        viewModel.charaLiveList.observe(viewLifecycleOwner) { cardList->
            if (cardList.isNotEmpty()) {
                rv.grid(spanCount = cardList.size / 5).setup {
                    addType<Card>(R.layout.chara_item)
                    onBind {
                        val charaBinding = getBinding<CharaItemBinding>()
                        charaBinding.charaListVm = viewModel
                        charaBinding.itemPosition = adapterPosition
                    }
                    onClick(R.id.unit_icon){
                        when(it){
                            R.id.unit_icon -> {
                                sharedChara.selectedChara = cardList[adapterPosition]
                                viewModel.selectedPosition.value = adapterPosition
                                println(viewModel.selectedPosition.value)
                            }
                        }
                    }
                }.models = cardList
            }
        }
        sharedChara.charaList.observe(viewLifecycleOwner) {
            viewModel.filterDefault()
        }
    }
}