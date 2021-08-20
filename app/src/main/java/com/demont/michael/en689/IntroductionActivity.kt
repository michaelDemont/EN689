package com.demont.michael.en689

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_introduction.*

class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        foedris.alpha = 0f
        foedris.animate().setDuration(4000).alpha(1f).withEndAction{

            val tent = Intent(this, MainActivity::class.java)
            startActivity(tent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()

        }
        lottie.animate()
    }
}