package de.thm.ap.mc_trainer

import android.animation.ObjectAnimator

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import de.thm.ap.mc_trainer.databinding.FragmentSignInBinding
import de.thm.ap.mc_trainer.firebase.FirestoreClass
import de.thm.ap.mc_trainer.models.User


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signIn.setOnClickListener {

            if(validate()){
                binding.emailLogin.setText("")
                binding.passwordLogin.setText("")

                progressBar()
                Handler().postDelayed({
                    findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
                    progressBar.visibility = View.INVISIBLE
                    progressBar.progress = 0
                },500)
               /* Log.d("LOG_SIGNED_IN", "signed in succesfull")
                FirebaseFirestore.getInstance().collection("users")
                    .document()
                    .get()
                    .addOnSuccessListener { document ->
                        val loggegInUser = document.toObject(User::class.java)
                        Log.d("TAG_REC", document.toString())
                    }
                Log.d("LOG_SIGNED_IN", "signed ended")*/
            }else{
                Log.d("TAG_OUT", "signed in failed")
            }
        }

        binding.signUp.setOnClickListener {

            progressBar()
            Handler().postDelayed({
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
                progressBar.visibility = View.INVISIBLE
                progressBar.progress = 0
            },500)
        }

        return view
    }

    private fun validate(): Boolean{

        var isValid = true

        if(binding.passwordLogin.text.toString().isNotEmpty()){
            if(binding.passwordLogin.text.toString().trim().length <= 6 ){
                view?.let { Snackbar.make(it, "Password must have at least 6 characters!!", Snackbar.LENGTH_LONG).show() }
                isValid = false
            }
        }else{
            binding.passwordLogin.error ="Password must have a value!!"
            isValid = false
        }

        if(binding.emailLogin.text.toString().trim().isNotEmpty() ){

            if(binding.emailLogin.text.toString().trim().contains("@") ){
                val parts = binding.emailLogin.text.toString().split('@')

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
            binding.emailLogin.error = "Email must have a value"
            isValid = false
        }

        return isValid
    }

    private  fun progressBar(){
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        val curProg = 100
        ObjectAnimator.ofInt(progressBar,"progress", curProg)
            .setDuration(500)
            .start()
    }

}