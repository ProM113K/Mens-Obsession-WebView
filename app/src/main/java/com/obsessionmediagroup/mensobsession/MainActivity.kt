package com.obsessionmediagroup.mensobsession

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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

        webView.webViewClient = MyWebViewClient()

        webView.loadUrl(WEB_URL)

        webView.settings.javaScriptEnabled = true

        webView.settings.setSupportZoom(true)
    }

    inner class MyWebViewClient : WebViewClient() {
        @Deprecated("Deprecated in Java")
        @Suppress("DEPRECATION")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (URLUtil.isNetworkUrl(url)) {
                return false
            } else if (url != null) {
                if (url.startsWith(prefix = "whatsapp:") || url.startsWith(prefix = "tg:")) {
                    try {
                        val sendIntent = Intent().apply {
                            this.action = Intent.ACTION_SEND
                            this.putExtra(Intent.EXTRA_TEXT, url)
                            this.type = "text/plain"
                        }
                        startActivity(sendIntent)
                        Toast.makeText(this@MainActivity, "Mengirim berita", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            this@MainActivity,
                            "Whatsapp belum terpasang",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return true
                } else if (url.startsWith("mailto:")) {
                    try {
                        val sendMailIntent = Intent().apply {
                            this.action = Intent.ACTION_SEND
                            this.putExtra(Intent.EXTRA_EMAIL, url)
                            this.type = "text/plain"
                        }
                        startActivity(sendMailIntent)
                        Toast.makeText(this@MainActivity, "Mengirim berita", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            this@MainActivity,
                            "Gagal mengirim email",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    return true
                }
            }
            return super.shouldOverrideUrlLoading(view, url)
        }
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