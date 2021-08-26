package us.gijuno.gyeonhae.presentation.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import us.gijuno.gyeonhae.R
import us.gijuno.gyeonhae.databinding.FragmentInnerTitleBinding
import us.gijuno.gyeonhae.presentation.base.BaseFragment

class InnerTitleFragment : BaseFragment<FragmentInnerTitleBinding>(R.layout.fragment_inner_title) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return binding.root
    }
}
