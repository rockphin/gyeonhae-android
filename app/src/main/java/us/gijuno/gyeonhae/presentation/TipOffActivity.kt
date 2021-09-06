package us.gijuno.gyeonhae.presentation

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import us.gijuno.gyeonhae.R

class TipOffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_off)
        val tipOffWebview = findViewById<WebView>(R.id.tip_off_webViews)

        tipOffWebview.webViewClient = WebViewClient()
        tipOffWebview.webChromeClient = WebChromeClient()

        tipOffWebview.apply {
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
        }

        tipOffWebview.loadUrl("https://forms.gle/FrnvgCuRT3NzcSZJ6")
    }
}
