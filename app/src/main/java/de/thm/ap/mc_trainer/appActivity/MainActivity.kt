package de.thm.ap.mc_trainer.appActivity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import de.thm.ap.mc_trainer.R
import de.thm.ap.mc_trainer.databinding.ActivityMainBinding
import de.thm.ap.mc_trainer.firebase.UserDAO


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var backPressedTime = 0L
    private val userDAO = UserDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if((backPressedTime + 2000) > System.currentTimeMillis()){
            userDAO.startProgressbar(this)
            backToSignInFragment()
            super.onBackPressed()
        }else{
            Toast.makeText(this, "Press back again to logout", Toast.LENGTH_LONG).show()
        }

        backPressedTime = System.currentTimeMillis()
    }

    fun backToSignInFragment(){
        findNavController(R.id.action_signInFragment_to_signUpFragment)
        userDAO.stopProgressBar()
    }
}