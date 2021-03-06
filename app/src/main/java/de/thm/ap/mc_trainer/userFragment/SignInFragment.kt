package de.thm.ap.mc_trainer.userFragment

import android.app.AlertDialog

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import de.thm.ap.mc_trainer.R
import de.thm.ap.mc_trainer.databinding.FragmentSignInBinding
import de.thm.ap.mc_trainer.firebase.UserDAO

//import de.thm.ap.mc_trainer.utils.BaseActivity


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var userDAO: UserDAO
   // private val helpers = BaseActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
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
                userDAO.startProgressbar(activity)
                userDAO.userLogin(this, binding.emailLogin.text.toString().trim(), binding.passwordLogin.text.toString().trim())
            }
        }

        binding.signUp.setOnClickListener {

            userDAO.startProgressbar(activity)
            userDAO.toSignUpForm(this)
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

        if(binding.passwordLogin.text.toString().isBlank()){
            binding.passwordWrapper.helperText = "required*"
        }else{
            binding.passwordWrapper.helperText = null
        }
        return isValid(email, password)
    }

    fun startMainActivity(){
        findNavController().navigate(R.id.nav_home)
        erase()
    }

    fun toSignUpForm(){
        findNavController().navigate(R.id.signUpFragment)
    }

    fun showError(){
        val alertDial = AlertDialog.Builder(activity)

        alertDial.setTitle("Please sign up")
            .setMessage("Please check your Email or your password\nor Sign up")
            .setPositiveButton("ok", null)
            .show()
            .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.purple_700))
        binding.passwordLogin.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

}