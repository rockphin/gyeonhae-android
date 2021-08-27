package us.gijuno.gyeonhae.presentation

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

@BindingAdapter("setDrawableTopCompat")
fun setDrawableTopCompat(view: TextView, resId: Int) {
    view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, resId, 0, 0)
}

data class LayoutMenuButton(@DrawableRes val icon: Int, @StringRes val text: Int)
