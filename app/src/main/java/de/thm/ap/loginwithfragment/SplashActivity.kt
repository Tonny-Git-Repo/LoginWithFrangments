package de.thm.ap.loginwithfragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, IntroActivity::class.java)

        val timerTask = timerTask {
            startActivity(intent)
            finish()
        }
        Timer().schedule(timerTask,2500)

       //this on still work but it is deprecated
       /* Handler().postDelayed({
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        },2500)*/
    }
}