package com.vb.demobankapp.data.remote.datasource

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.vb.demobankapp.data.remote.dto.UserDto
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private var verificationId: String? = null

    fun addUser(userDto: UserDto, onResult: (Boolean) -> Unit) {
        db.collection("users")
            .add(userDto)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun getUserById(userId: String, onResult: (UserDto?) -> Unit) {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                onResult(document.toObject(UserDto::class.java))
            }
            .addOnFailureListener { onResult(null) }
    }

    fun deleteUser(userId: String, onResult: (Boolean) -> Unit) {
        db.collection("users")
            .document(userId)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun getAllUsers(onResult: (List<UserDto>) -> Unit) {
        db.collection("users")
            .get()
            .addOnSuccessListener { snapshot ->
                val users = snapshot.toObjects(UserDto::class.java)
                onResult(users)
            }
            .addOnFailureListener { onResult(emptyList()) }
    }

    fun login(phoneNumber: String, activity: Activity, onResult: (Result<String>) -> Unit) {
        try {
            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    onResult(Result.success("AUTO_VERIFIED"))
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onResult(Result.failure(e))
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    this@UserRemoteDataSource.verificationId = verificationId
                    onResult(Result.success("OTP_SENT"))
                }

                override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                    onResult(Result.failure(Exception("OTP zaman aşımı")))
                }
            }

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)

        } catch (e: Exception) {
            onResult(Result.failure(e))
        }
    }

    fun validateOtp(phoneNumber: String, otp: String, onResult: (Result<UserDto?>) -> Unit) {
        val verId = verificationId
        if (verId == null) {
            onResult(Result.failure(Exception("Verification ID yok")))
            return
        }

        val credential = PhoneAuthProvider.getCredential(verId, otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    db.collection("users")
                        .whereEqualTo("phoneNumber", phoneNumber)
                        .get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot.isEmpty) {
                                onResult(Result.success(null))
                            } else {
                                val user = snapshot.documents[0].toObject(UserDto::class.java)
                                onResult(Result.success(user))
                            }
                        }
                        .addOnFailureListener { exception ->
                            onResult(Result.failure(exception))
                        }
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Geçersiz OTP kodu")))
                }
            }
    }
}