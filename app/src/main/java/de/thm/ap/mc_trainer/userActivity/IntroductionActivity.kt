package de.thm.ap.mc_trainer.userActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import de.thm.ap.mc_trainer.R

class IntroductionActivity : AppCompatActivity() {

    /**
     * This the activity, where the sign in and sign up fragments are managed
     * after a login the other Fragments will be managed in the main activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.frame_layout)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}