package de.thm.ap.mc_trainer.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import de.thm.ap.mc_trainer.SignUpFragment
import de.thm.ap.mc_trainer.models.User
import de.thm.ap.mc_trainer.utils.Constants

class FirestoreClass {

    private val fireStore = FirebaseFirestore.getInstance()

    fun registerUser(fragment: SignUpFragment, userInfo : User){
        Log.d("TAG_INREG", " judst before register User")
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                fragment.userRegisteredSuccess()
            }.addOnFailureListener{
                e -> Log.e("Failed", "Error writting document")
            }
        Log.d("TAG_Finisched", "after register User")
    }

/*    fun registerUser(name: String, email: User, password: String){

        val user: MutableMap<String, Any> = HashMap()
        user["name"] = name
        user["email"] = email
        user["password"] = password

        fireStore.collection("users")
            .add(user)
            .addOnSuccessListener {

            }
    }*/

    fun signInUser(){
        fireStore.collection("users")
            .document()
            .get()
            .addOnSuccessListener { document ->
                val loggegInUser = document.toObject(User::class.java)
                Log.d("TAG_REC", loggegInUser.toString())
            }
    }

    fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}