package us.gijuno.gyeonhae.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding>(
    @LayoutRes val layoutId: Int,
) : AppCompatActivity() {

    private var _binding: T? = null
    protected val binding: T
        get() = with(_binding) {
            this ?: throw IllegalStateException("Binding not initialized")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun showToast(msg: String, length: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, msg, length).show()
}
