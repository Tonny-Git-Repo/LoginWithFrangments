package de.thm.ap.mc_trainer.utils

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController

import de.thm.ap.mc_trainer.R
import de.thm.ap.mc_trainer.appActivity.MainActivity
import de.thm.ap.mc_trainer.userFragment.SignInFragment
import de.thm.ap.mc_trainer.userFragment.SignUpFragment
import java.util.*
import kotlin.concurrent.timerTask
/*

class BaseActivity: AppCompatActivity() {

    private lateinit var progressDialog: Dialog
    var doubleBackToExitPressedOne = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun showProgressDialog(){
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.in_process)
        progressDialog.show()
    }
    fun hideProgressDialog(){
        progressDialog.dismiss()
    }

    fun doubleBackToExit(){
        if(doubleBackToExitPressedOne){
            super.onBackPressed()
            return
        }else{
            this.doubleBackToExitPressedOne = true
            Toast.makeText(this, "Press back again to logout", Toast.LENGTH_SHORT).show()
        }
       // val intent = Intent(this, MainActivity::class.java)

        val timerTask = timerTask {
            doubleBackToExitPressedOne = false
        }
        Timer().schedule(timerTask,2000)
    }


    fun toSignUpForm(activity: SignInFragment) {
        activity.toSignUpForm()
        hideProgressDialog()
    }
    fun backToSignUp(activity: SignUpFragment){
        activity.toSignInFragment()
        hideProgressDialog()
    }
}*/
