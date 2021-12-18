package de.thm.ap.mc_trainer.firebase

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import de.thm.ap.mc_trainer.models.User


/***
 *This is the class that manage the operations of sign in and sign up
 */
class UserDAO {

    private val fireStore = FirebaseFirestore.getInstance()
    var loggedIn = false
    private fun getAllUser() = fireStore.collection("users")

    fun userLogin(context: View, email: String, emailValue: String, password: String){

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(emailValue, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    isLogged(true)
                    Log.d("TAG_SIGN", "sign in ${task}")
                }else{
                    task.exception?.message?.let { Log.e("TAG", it) }
                }
            }

    }

    fun registerUser(context: View, name: String, email: String, password:String): Boolean {
        var succed = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { tasks ->

                if (tasks.isSuccessful) {
                    val firebaseUser: FirebaseUser = tasks.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val user = User(firebaseUser.uid, name, registeredEmail, password)
                    getAllUser()
                        .add(user)
                        .addOnSuccessListener {
                            succed = true
                        }

                } else {
                    if (tasks.exception?.message?.toString() == "The email address is already in use by another account.") {
                        Snackbar.make(context, "The email address is already in use by another account!!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    } else {
                        tasks.exception?.message?.let { Log.e("TAG", it) }
                    }
                }
            }

        return succed
    }

   /* fun setUserId( idValue: String){
        idUser = idValue
    }
    fun getUserId(): String {
        return idUser
    }*/

    fun isLogged(value: Boolean){
        loggedIn = value
    }
    fun getIsLogged(): Boolean{
        return loggedIn
    }
    fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}