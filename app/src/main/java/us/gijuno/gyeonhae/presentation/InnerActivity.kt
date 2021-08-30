package us.gijuno.gyeonhae.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import us.gijuno.gyeonhae.R

class InnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner)
        val activityStatus = intent.getStringExtra("index")
    }
}
