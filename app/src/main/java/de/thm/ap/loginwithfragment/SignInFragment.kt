package de.thm.ap.loginwithfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import de.thm.ap.loginwithfragment.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        var isValid = true

        binding.signIn.setOnClickListener {
            val email = binding.emailLogin.text.toString().ifEmpty {
                binding.emailLogin.error = "Email must have a value!!"
                isValid = false
                ""
            }
            val password = binding.passwordLogin.text.toString().ifEmpty {
                binding.passwordLogin.error = "Pasword must have a value!!"
                isValid = false
                ""
            }

            if(isValid){
                Log.d("LOG_SIGNED_IN", "signed in succesfull")
            }
        }

        return view
    }

}