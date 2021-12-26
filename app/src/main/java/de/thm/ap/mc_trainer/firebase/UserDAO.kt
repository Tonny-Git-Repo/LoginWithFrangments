package de.thm.ap.mc_trainer.firebase

import android.app.AlertDialog
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import de.thm.ap.mc_trainer.R
import de.thm.ap.mc_trainer.models.User
import de.thm.ap.mc_trainer.userFragment.SignInFragment
import de.thm.ap.mc_trainer.userFragment.SignUpFragment
//import de.thm.ap.mc_trainer.utils.BaseActivity


/***
 *This is the class that manage the operations of sign in and sign up
 */
class UserDAO {

    private val fireStore = FirebaseFirestore.getInstance()
    //private val helper = BaseActivity()
    private lateinit var dialogProgresBar : AlertDialog
    private fun getAllUser() = fireStore.collection("users")

    fun userLogin(activity: SignInFragment, emailValue: String, password: String){
        Log.d("TAG_SIGN", "Call Me on UserLogin()")
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(emailValue, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d("TAG_SIGN", "before isLogged()")
                    activity.startMainActivity()
                    stopProgressBar()
                    Log.d("TAG_SIGN", "sign in ${task}")
                }else{
                    stopProgressBar()
                    activity.showError()
                    task.exception?.message?.let { Log.e("TAG", it) }
                }
            }
    }

    fun registerUser(activity: SignUpFragment, name: String, email: String, password:String){
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { tasks ->

                if (tasks.isSuccessful) {
                    val firebaseUser: FirebaseUser = tasks.result!!.user!!
                    val newUser = firebaseUser.email!!
                    val user = User(firebaseUser.uid, name, newUser, password)
                    getAllUser()
                        .add(user)
                        .addOnSuccessListener {
                            activity.userRegisteredSuccess()
                            backToSignUp(activity)
                        }

                } else {
                    if (tasks.exception?.message?.toString() == "The email address is already in use by another account.") {
                        activity.showErrorEmailAlreadyUser()
                        stopProgressBar()
                    } else {
                        activity.showErrorSomething()
                        stopProgressBar()
                        tasks.exception?.message?.let { Log.e("TAG", it) }
                    }
                }
            }

    }


    fun getCurrentUserId(): String {
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if(currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

   /* fun getCurrentUser(): User{

    }*/
    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun startProgressbar(activity: FragmentActivity?){
        val dialogView = (activity?.layoutInflater)?.inflate(R.layout.in_process,null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        dialogProgresBar = builder.create()
        dialogProgresBar.show()
    }

    fun stopProgressBar(){
        dialogProgresBar.dismiss()
    }

    fun toSignUpForm(activity: SignInFragment) {
        activity.toSignUpForm()
        stopProgressBar()
    }
    fun backToSignUp(activity: SignUpFragment){
        activity.toSignInFragment()
        stopProgressBar()
    }


}