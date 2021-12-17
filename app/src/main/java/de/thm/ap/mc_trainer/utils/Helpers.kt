package de.thm.ap.mc_trainer.utils

import android.animation.ObjectAnimator
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController

import de.thm.ap.mc_trainer.R

class Helpers {

    fun progressBar(progBar: ProgressBar){
        progBar.visibility = View.VISIBLE

        ObjectAnimator.ofInt(progBar,"progress", 100)
            .setDuration(500)
            .start()
    }
}