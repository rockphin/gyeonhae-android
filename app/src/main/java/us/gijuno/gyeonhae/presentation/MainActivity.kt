package us.gijuno.gyeonhae.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import slush.Slush
import us.gijuno.gyeonhae.R

class MainActivity : AppCompatActivity() {
    private val gridLayoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonItemsList = listOf(
            LayoutMenuButton(R.drawable.ic_scan, R.string.scan_func),
            LayoutMenuButton(R.drawable.ic_convenience, R.string.convenience_func),
            LayoutMenuButton(R.drawable.ic_setting, R.string.setting),
            LayoutMenuButton(R.drawable.ic_guide, R.string.app_guide),
        )

        Slush.SingleType<LayoutMenuButton>()
            .setItemLayout(R.layout.layout_menu_button)
            .setLayoutManager(gridLayoutManager)
            .onBind { view, item ->
                val textViewWithDrawable = view.findViewById<TextView>(R.id.textview_with_drawable)
                textViewWithDrawable.text = getString(item.text)
                val compoundDrawables = item.icon
                textViewWithDrawable.setCompoundDrawablesRelativeWithIntrinsicBounds(0, compoundDrawables, 0, 0)
            }
            .setItems(buttonItemsList)
            .into(findViewById(R.id.button_recyclerview))
    }
}
