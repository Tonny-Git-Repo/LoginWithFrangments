package de.thm.ap.loginwithfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_sign_option_fragment.*

import de.thm.ap.loginwithfragment.databinding.FragmentSignOptionFragmentBinding
import kotlin.concurrent.fixedRateTimer


class SignOptionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentSignOptionFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signUp.setOnClickListener {
            val signUp = SignUpFragment()

            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frame_layout, signUp)
            transaction.addToBackStack( null)
            transaction.commit()
        }


        binding.signIn.setOnClickListener {
            val signIn = SignInFragment()

            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frame_layout, signIn)
            transaction.addToBackStack( null)
            transaction.commit()
        }


        return view
    }


/*    private fun replaceFragment(fragment: Fragment){

        fragmentManager?.beginTransaction().apply {
            replaceFragment(fragment)
        }
//        fragmentTransaction.replace(R.id.frame_layout, fragment)
//        fragmentTransaction.commit()
    }*/
}