package us.gijuno.gyeonhae.presentation

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod

@BindingAdapter("setDrawableTopCompat")
fun setDrawableTopCompat(view: TextView, resId: Int) {
    view.setCompoundDrawablesRelativeWithIntrinsicBounds(
        0,
        resId,
        0,
        0)
}

data class LayoutMenuButton(val icon: Int, val text: String)