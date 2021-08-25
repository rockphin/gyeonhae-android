package us.gijuno.gyeonhae.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(
    @LayoutRes val layoutId: Int,
) : Fragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = with(_binding) {
            this ?: throw IllegalStateException("Binding not initialized")
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    protected fun showToast(msg: String, length: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(context, msg, length).show()
}
