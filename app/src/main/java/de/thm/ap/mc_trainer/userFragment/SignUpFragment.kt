package de.thm.ap.mc_trainer.userFragment

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import de.thm.ap.mc_trainer.R
import de.thm.ap.mc_trainer.databinding.FragmentSignUpBinding
import de.thm.ap.mc_trainer.firebase.UserDAO


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var progressBar : ProgressBar
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var userNameIsValid = false
        var emailIsValid = false
        var passwordIsValid = false
        var confirmPasswordIsValid = false


        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        userDAO = UserDAO()

        //validating the user name dynamically as the user is entering his email
        binding.userName.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                if(text.length < 5){
                    binding.nameWrapper.helperText = "at least 6 charaters"
                }else{
                    binding.nameWrapper.helperText = null
                    passwordIsValid = true
                }
            }else if(text!!.isBlank()){
                binding.nameWrapper.helperText ="required*"
            }
        }

        //validating the email dynamically as the user is entering his email
        binding.newEmail.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                    binding.emailWrapper.helperText = "email not valid"
                }else{
                    binding.emailWrapper.helperText = null
                    binding.newEmail.error = null
                    emailIsValid = true
                }
            }
        }

        //validating the password dynamically as the user is entering his email
        binding.password.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                if(text.length < 7){
                    binding.passwordWrapper.helperText = "at least 7 charaters"
                }else{
                    binding.passwordWrapper.helperText = null
                    passwordIsValid = true
                }
            }else if(text!!.isBlank()){
                binding.passwordWrapper.helperText ="required*"
            }
        }

        //validating the the match between the password and the confirmation password dynamically as the user is entering his email
        binding.confirmPassword.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                if(text!! != binding.password.text){
                    binding.confirmPasswordWrapper.helperText = "Not matching"
                }else{
                    binding.confirmPasswordWrapper.helperText = null
                    passwordIsValid = true
                }
            }else if(text!!.isBlank()){
                binding.confirmPasswordWrapper.helperText ="required*"
            }
        }


        binding.signUp.setOnClickListener {

            if(validate(userNameIsValid, emailIsValid, passwordIsValid, confirmPasswordIsValid)){
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
                    if(userDAO.registerUser(view ,name, email, password)){
                        progressBar()
                        Handler().postDelayed({
                            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                            progressBar.visibility = View.INVISIBLE
                            progressBar.progress = 0
                        },500)
                    }
                    }
            }

        }




        binding.prevButton.setOnClickListener { (activity)
            progressBar()

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

    fun isValid(nameConf : Boolean, emailConf: Boolean, passwordConf: Boolean, passwordConfConf: Boolean): Boolean{
        return (nameConf && emailConf && passwordConf && passwordConfConf)
    }

    fun validate(nameConf : Boolean, emailConf: Boolean, passwordConf: Boolean, passwordConfConf: Boolean): Boolean{

        if(binding.userName.text.toString().isEmpty()){
            binding.nameWrapper.helperText = "required*"
        }else{
            binding.nameWrapper.helperText = null
        }

        if(binding.newEmail.text.toString().isNullOrBlank()){
            binding.emailWrapper.helperText = "required*"
        }else{
            binding.emailWrapper.helperText = null
        }

        if(binding.password.text.toString().isEmpty()){
            binding.passwordWrapper.helperText = "required*"
        }else{
            binding.passwordWrapper.helperText = null
        }

        if(binding.confirmPassword.text.toString().isEmpty()){
            binding.confirmPasswordWrapper.helperText = "required*"
        }else{
            binding.confirmPasswordWrapper.helperText = null
        }
        return isValid(nameConf, emailConf, passwordConf, passwordConfConf)
    }
    /*private fun validate(): Boolean{

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
    }*/

    private  fun progressBar(){
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        ObjectAnimator.ofInt(progressBar,"progress", 100)
            .setDuration(500)
            .start()
    }


}