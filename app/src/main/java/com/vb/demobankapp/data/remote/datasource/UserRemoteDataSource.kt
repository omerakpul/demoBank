package com.vb.demobankapp.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.vb.demobankapp.data.remote.dto.UserDto
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {
    fun addUser(userDto: UserDto, onResult: (Boolean) -> Unit) {
        db.collection("users")
            .document(userDto.id ?: "")
            .set(userDto)
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

    fun login(phoneNumber: String, onResult: (Result<String>) -> Unit) {
        // Sadece OTP gönder
        try {
            // Burada SMS gönderme işlemi yapılır
            onResult(Result.success("OTP_SENT"))
        } catch (e: Exception) {
            onResult(Result.failure(e))
        }
    }

    fun validateOtp(phoneNumber: String, otp: String, onResult: (Result<UserDto?>) -> Unit) {
        // OTP doğru mu kontrol et
        if (otp == "123456") { // Gerçek uygulamada Firebase Auth kullanılır
            // OTP doğru, telefon numarasına göre kullanıcıyı bul
            db.collection("users")
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.isEmpty) {
                        // Kullanıcı yok, null döner (register ekranına gidecek)
                        onResult(Result.success(null))
                    } else {
                        // Kullanıcı var, kullanıcı bilgilerini döner (home'a gidecek)
                        val user = snapshot.documents[0].toObject(UserDto::class.java)
                        onResult(Result.success(user))
                    }
                }
                .addOnFailureListener { exception ->
                    onResult(Result.failure(exception))
                }
        } else {
            // OTP yanlış
            onResult(Result.failure(Exception("INVALID_OTP")))
        }
    }
}