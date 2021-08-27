package us.gijuno.gyeonhae.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setDrawableTopCompat")
fun setDrawableTopCompat(view: TextView, resId: Int) {
    view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, resId, 0, 0)
}

data class LayoutMenuButton(val icon: Int, val text: String)
