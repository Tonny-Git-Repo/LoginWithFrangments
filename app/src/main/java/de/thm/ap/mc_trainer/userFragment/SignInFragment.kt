package de.thm.ap.mc_trainer.userFragment

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import de.thm.ap.mc_trainer.R
import de.thm.ap.mc_trainer.appActivity.MainActivity
import de.thm.ap.mc_trainer.databinding.FragmentSignInBinding
import de.thm.ap.mc_trainer.firebase.UserDAO


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var progressBar : ProgressBar
    private lateinit var db : FirebaseFirestore
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var emailIsValid = false
        var passwordIsValid = false
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        userDAO = UserDAO()
        db = FirebaseFirestore.getInstance()

        //validating the password dynamically as the user is entering his email
        binding.emailLogin.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                    binding.emailWrapper.helperText = "email not valid"
                }else{
                    binding.emailWrapper.helperText = null
                    binding.emailLogin.error = null
                    emailIsValid = true
                }
            }
        }

        //validating the email dynamically as the user is entering his email
        binding.passwordLogin.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                if(text.length < 6){
                    binding.passwordWrapper.helperText = "at least 6 charaters"
                }else{
                    binding.passwordWrapper.helperText = null
                    passwordIsValid = true
                }
            }else if(text!!.isBlank()){
                binding.passwordWrapper.helperText ="required*"
            }
        }




        binding.signIn.setOnClickListener {

            if(validate(emailIsValid, passwordIsValid)){
                userDAO.userLogin(view,"email", binding.emailLogin.text.toString().trim(), binding.passwordLogin.text.toString().trim())
                if(userDAO.getCurrentUserId().isNotEmpty()){
                    progressBar()
                    Handler().postDelayed({
                        //findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
                        startActivity(Intent(activity, MainActivity::class.java))
                        progressBar.visibility = View.INVISIBLE
                        progressBar.progress = 0
                        erase()
                    },500)
                }else{
                    AlertDialog.Builder(activity)
                        .setTitle("Please sign up")
                        .setMessage("Please check your Email or your password")
                        .setNeutralButton("ok", null)
                        .show()
                    binding.passwordLogin.onEditorAction(EditorInfo.IME_ACTION_DONE)
                }

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

    private fun erase() {
        binding.emailLogin.setText("")
        binding.passwordLogin.setText("")
    }

    fun isValid(email: Boolean, password: Boolean): Boolean{
        return email && password
    }

    fun validate(email: Boolean, password: Boolean): Boolean{

        if(binding.emailLogin.text.toString().isEmpty()){
            binding.emailWrapper.helperText = "required*"
        }else{
            binding.emailWrapper.helperText = null
        }

        if(binding.passwordLogin.text.toString().isNullOrBlank()){
            binding.passwordWrapper.helperText = "required*"
        }else{
            binding.passwordWrapper.helperText = null
        }
        return isValid(email, password)
    }
    /*private fun validate(): Boolean{

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
    }*/

    private  fun progressBar(){
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        val curProg = 100
        ObjectAnimator.ofInt(progressBar,"progress", curProg)
            .setDuration(500)
            .start()
    }

}