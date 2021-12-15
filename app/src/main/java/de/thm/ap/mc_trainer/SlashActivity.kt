package de.thm.ap.mc_trainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.timerTask

class SlashActivity : AppCompatActivity() {
    /***
     * This class will just be displayed for 2.5 s just for greating the user, then
     * lead to the introduction Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash)

        val intent = Intent(this, IntroductionActivity::class.java)

        val timerTask = timerTask {
            startActivity(intent)
            finish()
        }
        Timer().schedule(timerTask,2500)
    }
}