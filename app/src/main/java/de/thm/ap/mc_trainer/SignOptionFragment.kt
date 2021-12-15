package de.thm.ap.mc_trainer

import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.FragmentTransaction
import de.thm.ap.mc_trainer.databinding.FragmentSignOptionBinding
import java.util.*
import kotlin.concurrent.timerTask


class SignOptionFragment : Fragment() {

    private lateinit var progressBar : ProgressBar
    private lateinit var binding: FragmentSignOptionBinding
    private var i = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignOptionBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signUp.setOnClickListener {
            val signUp = SignUpFragment()

            replaceFragment(signUp)
        }


        binding.signIn.setOnClickListener {
            val signIn = SignInFragment()
            progressBar()
            val timerTask = timerTask {
                replaceFragment(signIn)
            }
            Timer().schedule(timerTask,500)
        }


        return view
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.addToBackStack( null)
        transaction.commit()
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