package de.thm.ap.mc_trainer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import de.thm.ap.mc_trainer.databinding.ActivityIntroductionBinding

class IntroductionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)


        /*supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, SignInFragment())
            .commit()*/
        //setupActionBarWithNavController(findNavController(R.id.frame_layout))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.frame_layout)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}