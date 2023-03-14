package com.obsessionmediagroup.mensobsession

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsCompat

@SuppressLint("StaticFieldLeak")
private lateinit var webView: WebView

@SuppressLint("SetJavaScriptEnabled")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        webView = findViewById(R.id.mo_webView)

        WindowInsetsCompat.Type.navigationBars()

        webView.webViewClient = WebViewClient()

        webView.loadUrl(WEB_URL)

        webView.settings.javaScriptEnabled = true

        webView.settings.setSupportZoom(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val WEB_URL = "https://www.mensobsession.com/"
    }
}