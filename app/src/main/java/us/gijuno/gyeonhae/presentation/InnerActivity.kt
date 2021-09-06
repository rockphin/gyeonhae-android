package us.gijuno.gyeonhae.presentation

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import slush.Slush
import us.gijuno.gyeonhae.R
import us.gijuno.gyeonhae.databinding.ActivityInnerBinding
import us.gijuno.gyeonhae.presentation.base.BaseActivity

class InnerActivity : BaseActivity<ActivityInnerBinding>(R.layout.activity_inner) {
    private val gridLayoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner)
        val activityStatus = intent.getStringExtra("index") ?: return

        val buttonItemList = mutableListOf<LayoutMenuButton>()
        val innerTitle = findViewById<TextView>(R.id.inner_title)
        val innerSubtitle = findViewById<TextView>(R.id.inner_subtitle)

        when (activityStatus) {
            "recognize" -> {
                innerTitle.text = getString(R.string.scan_func)
                innerSubtitle.text = getString(R.string.select_scan_func)
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_braille, R.string.braille_scan, null))
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_text, R.string.text_scan, null))
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_picture, R.string.picture_scan, null))
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_barcode, R.string.barcode_scan, null))
            }
            "convenience" -> {
                innerTitle.text = getString(R.string.convenience_func)
                innerSubtitle.text = getString(R.string.select_convenience_func)
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_tip_off, R.string.tip_off, null))
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_sos, R.string.sos, null))
            }
            "setting" -> {
                innerTitle.text = getString(R.string.setting)
                innerSubtitle.isVisible = false
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_vibrate, R.string.vibrate_off, null))
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_sound, R.string.sound_off, null))
                buttonItemList.add(LayoutMenuButton(R.drawable.ic_auto_scan, R.string.auto_scan_off, null))
            }
        }

        Slush.SingleType<LayoutMenuButton>()
            .setItemLayout(R.layout.layout_menu_button)
            .setLayoutManager(gridLayoutManager)
            .onBind { view, item ->
                val textViewWithDrawable = view.findViewById<TextView>(R.id.textview_with_drawable)
                textViewWithDrawable.text = getString(item.text)
                val compoundDrawables = item.icon
                textViewWithDrawable.setCompoundDrawablesRelativeWithIntrinsicBounds(0, compoundDrawables, 0, 0)
            }
            .onItemClick { _, index ->
                Log.d("asdf", index.toString())
                startActivity(buttonItemList[index].intent)
            }
            .setItems(buttonItemList)
            .into(findViewById(R.id.inner_button_recyclerview))
    }
}
