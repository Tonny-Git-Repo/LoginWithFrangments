package de.thm.ap.mc_trainer

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.thm.ap.mc_trainer.databinding.FragmentSignInBinding
import de.thm.ap.mc_trainer.databinding.FragmentSignUpBinding
import de.thm.ap.mc_trainer.firebase.FirestoreClass
import de.thm.ap.mc_trainer.models.User
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.concurrent.schedule


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signUp.setOnClickListener {

            if(validate()){
                val name = binding.userName.text.toString()
                val email = binding.newEmail.text.toString()
                val password = binding.password.text.toString()
                val confirmPassword = binding.confirmPassword.text.toString()

                if((password.toString().trim() ) != (confirmPassword.toString().trim()) ){
                    AlertDialog.Builder(activity)
                        .setTitle("Incorrect Pasword confirmation")
                        .setMessage("Incorrect password!!\nPlease enter the same Password!!")
                        .setNeutralButton("ok", null)
                        .show()
                }else{
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { tasks ->

                            if (tasks.isSuccessful) {

                                val firebaseUser: FirebaseUser = tasks.result!!.user!!
                                val registeredEmail = firebaseUser.email!!
                                val user = User(firebaseUser.uid, name, registeredEmail)
                                FirestoreClass().registerUser(this, user)
                                FirebaseAuth.getInstance().signOut()
                                progressBar()
                                Handler().postDelayed({
                                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                                     progressBar.visibility = View.INVISIBLE
                                     progressBar.progress = 0
                                },500)

                            } else {
                                if(tasks.exception?.message?.toString() == "The email address is already in use by another account."){
                                    Snackbar.make(it, "The email address is already in use by another account!!",
                                        Snackbar.LENGTH_LONG).show()
                                }else{
                                    tasks.exception?.message?.let { Log.e("TAG", it) }
                                }
                            }
                        }

                }
            }

        }




        binding.prevButton.setOnClickListener { (activity)
            progressBar()

            /*Timer().schedule(500){
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }
            Timer().schedule(timerTask {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }, 500)*/
            Handler().postDelayed({
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                progressBar.visibility = View.INVISIBLE
                progressBar.progress = 0
            },500)

        }

        return view
    }


    fun userRegisteredSuccess() {
        FirebaseAuth.getInstance().signOut()
    }


    private fun validate(): Boolean{

        var isValid = true

        if(binding.userName.text.toString().isEmpty()){
            binding.userName.error ="Name must have a value!!"
            isValid = false
        }

        if(binding.password.text.toString().isNotEmpty()){
            if(binding.password.text.toString().trim().length <= 6 ){
                view?.let { Snackbar.make(it, "Password must have at least 6 characters!!",
                    Snackbar.LENGTH_LONG).show() }
                isValid = false
            }
        }else{
            binding.password.error ="Password must have a value!!"
            isValid = false
        }

        if(binding.confirmPassword.text.toString().isNotEmpty()){
            if(binding.confirmPassword.text.toString().trim().length <= 6 ){
                view?.let { Snackbar.make(it, "Password must have at least 6 characters!!",
                    Snackbar.LENGTH_LONG).show() }
                isValid = false
            }
        }else{
            binding.confirmPassword.error ="Please confirm your password!!"
            isValid = false
        }

        if(binding.newEmail.text.toString().trim().isNotEmpty() ){

            if(binding.newEmail.text.toString().trim().contains("@") ){
                val parts = binding.newEmail.text.toString().split('@')

                if(!(parts.last().toString()).contains(".")){
                    view?.let { Snackbar.make(it, "Please enter a valid email!!",
                        Snackbar.LENGTH_LONG).show() }
                    isValid = false
                }

            }else{
                view?.let { Snackbar.make(it, "Please enter a valid email!!",
                    Snackbar.LENGTH_LONG).show() }
                isValid = false
            }
        }else{
            binding.newEmail.error = "Email must have a value"
            isValid = false
        }

        return isValid
    }

    private  fun progressBar(){
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        ObjectAnimator.ofInt(progressBar,"progress", 100)
            .setDuration(500)
            .start()
    }


}