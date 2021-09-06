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
        val tipOffWebView = findViewById<WebView>(R.id.tip_off_webViews)

        tipOffWebView.webViewClient = WebViewClient()
        tipOffWebView.webChromeClient = WebChromeClient()

        tipOffWebView.apply {
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
        }

        tipOffWebView.loadUrl("https://forms.gle/FrnvgCuRT3NzcSZJ6")
    }
}
