package de.thm.ap.loginwithfragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thm.ap.loginwithfragment.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        var isValid = true

        binding.signUp.setOnClickListener {
            val email = binding.newEmail.text.toString().ifEmpty {
                binding.newEmail.error ="Email must have a value!!"
                isValid = false
                ""
            }
            val password = binding.password.text.toString().ifEmpty {
                binding.password.error ="Password must have a value!!"
                isValid = false
                ""
            }
            val confrimPassword = binding.confirmPassword.text.toString().ifEmpty {
                binding.confirmPassword.error ="Please confrim your password!!"
                isValid = false
                ""
            }
            if(password.toString().trim() != confrimPassword.toString().trim()){
                AlertDialog.Builder(activity)
                    .setTitle("Incorrect Pasword confirmation")
                    .setMessage("Incorrect password!!\nPlease enter the same Password!!")
                    .setNeutralButton("ok", null)
                    .show()
            }else{

            }
        }

        return view
    }

}