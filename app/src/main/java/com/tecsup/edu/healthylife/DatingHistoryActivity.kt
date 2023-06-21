package com.tecsup.edu.healthylife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DatingHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datinghistory)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.dating)
        val drawable = resources.getDrawable(R.color.lila_5f5)
        supportActionBar?.setBackgroundDrawable(drawable)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}