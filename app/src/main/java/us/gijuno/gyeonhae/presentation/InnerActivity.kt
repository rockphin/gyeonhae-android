package us.gijuno.gyeonhae.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import slush.Slush
import us.gijuno.gyeonhae.R

class InnerActivity : AppCompatActivity() {
    private val gridLayoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner)
        val activityStatus = intent.getStringExtra("index").toString()

        val buttonItemsList = mutableListOf<LayoutMenuButton>()
        val innerTitle = findViewById<TextView>(R.id.inner_title)
        val innerSubTitle = findViewById<TextView>(R.id.inner_subtitle)

        when (activityStatus) {
            "recognize" -> {
                innerTitle.text = "인식 기능"
                innerSubTitle.text = "원하시는 인식 기능을 선택해주세요."
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_braille, R.string.braille_scan))
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_text, R.string.text_scan))
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_picture, R.string.picture_scan))
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_barcode, R.string.barcode_scan))
            }
            "convenience" -> {
                innerTitle.text = "편의 기능"
                innerSubTitle.text = "원하시는 편의 기능을 선택해주세요."
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_tip_off, R.string.tip_off))
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_sos, R.string.sos))
            }
            "setting" -> {
                innerTitle.text = "설정"
                innerSubTitle.isVisible = false
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_vibrate, R.string.vibrate_off))
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_sound, R.string.sound_off))
                buttonItemsList.add(LayoutMenuButton(R.drawable.ic_auto_scan,
                    R.string.auto_scan_off))
            }
        }

        Slush.SingleType<LayoutMenuButton>()
            .setItemLayout(R.layout.layout_menu_button)
            .setLayoutManager(gridLayoutManager)
            .onBind { view, item ->
                val textViewWithDrawable = view.findViewById<TextView>(R.id.textview_with_drawable)
                textViewWithDrawable.text = getString(item.text)
                val compoundDrawables = item.icon
                textViewWithDrawable.setCompoundDrawablesRelativeWithIntrinsicBounds(0,
                    compoundDrawables,
                    0,
                    0)
            }
            .setItems(buttonItemsList)
            .into(findViewById(R.id.inner_button_recyclerview))
    }
}
