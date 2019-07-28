package com.example.gituserapp.features.webpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gituserapp.R
import kotlinx.android.synthetic.main.activity_repo_web_page.*

class RepositoryWebPageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_web_page)

        webView.loadUrl(intent.getStringExtra("RepoUrl"))

    }
}