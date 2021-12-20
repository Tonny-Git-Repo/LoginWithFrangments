package de.thm.ap.mc_trainer.userFragment


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import de.thm.ap.mc_trainer.R
import de.thm.ap.mc_trainer.databinding.FragmentSignUpBinding
import de.thm.ap.mc_trainer.firebase.UserDAO


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var userNameIsValid = false
        var emailIsValid = false
        var passwordIsValid = false
        var confirmPasswordIsValid = true


        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        userDAO = UserDAO()

        //validating the user name dynamically as the user is entering his email
        binding.userName.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()){
                if(text.length < 4){
                    binding.nameWrapper.helperText = "at least 4 charaters"
                }else{
                    binding.nameWrapper.helperText = null
                    userNameIsValid = true
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

       /* binding.confirmPassword.doOnTextChanged { text, start, before, count ->
            if(binding.password.text != binding.confirmPassword.text){
                binding.confirmPasswordWrapper.helperText = "not matching"
            }else{
                binding.confirmPasswordWrapper.helperText = null
            }
        }*/

        binding.signUp.setOnClickListener {
            if(validate(userNameIsValid, emailIsValid, passwordIsValid, confirmPasswordIsValid)){
                Log.d("TAG_VALID","is validated")

                if(binding.password.text.toString().trim() != binding.confirmPassword.text.toString().trim()){
                    Log.d("TAG_NM","password not matching")
                    showErrorPasswordNotMatch()
                }else{
                    userDAO.startProgressbar(activity)
                    userDAO.registerUser(this , binding.userName.text.toString(), binding.newEmail.text.toString(), binding.password.text.toString())
                }
            }

        }


        binding.prevButton.setOnClickListener { (activity)
            userDAO.startProgressbar(activity)
            userDAO.backToSignUp(this)
        }

        return view
    }


    fun toSignInFragment(){
        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    fun userRegisteredSuccess() {
        Toast.makeText(context, "user succesfully registrated", Toast.LENGTH_LONG).show()
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

        if(binding.newEmail.text.toString().isBlank()){
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


    private fun showErrorPasswordNotMatch(){
        AlertDialog.Builder(activity)
            .setTitle("Incorrect Pasword confirmation")
            .setMessage("Incorrect password!!\nPlease enter the same Password!!")
            .setNeutralButton("ok", null)
            .show()
    }

    fun showErrorEmailAlreadyUser(){
        AlertDialog.Builder(activity)
            .setTitle("Email already used")
            .setMessage("Sorry but this email is already used by someone!!")
            .setNeutralButton("ok", null)
            .show()
    }

    fun showErrorSomething(){
        AlertDialog.Builder(activity)
            .setTitle("Email already used")
            .setMessage("Sorry but this email is already used by someone!!")
            .setNeutralButton("ok", null)
            .show()
    }


}