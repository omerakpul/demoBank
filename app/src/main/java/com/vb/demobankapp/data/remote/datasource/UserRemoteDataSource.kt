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
}