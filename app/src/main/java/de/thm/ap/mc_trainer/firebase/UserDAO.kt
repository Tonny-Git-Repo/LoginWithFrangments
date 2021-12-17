package de.thm.ap.mc_trainer.firebase

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import de.thm.ap.mc_trainer.SignUpFragment
import de.thm.ap.mc_trainer.models.User

class UserDAO {

    private val fireStore = FirebaseFirestore.getInstance()

    private fun getAllUser() = fireStore.collection("users")

    fun userLogin(email: String, emailValue: String, password: String): User{
        val allUsers = getAllUser()
        //var userId = ""
        var user = User()
        getAllUser().whereEqualTo(email, emailValue)
            .get()
            .addOnSuccessListener {
                if(it.isEmpty){
                    Log.d("TAG_EMPTY", "the list is empty!!")
                }else {
                    //userId = "wrongPassword"
                    val loggedUser = it.toObjects(User::class.java)
                    for (elt in loggedUser) {
                        if (elt.password == password) {
                           // userId = getCurrentUserId()
                            user = elt
                            
                            Log.d("TAG_LOG_IN", "${user}")
                        }
                    }
                }
            }
        Log.d("TAG_LOG_IN", "${user} outOf ")
        return user
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



    fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}