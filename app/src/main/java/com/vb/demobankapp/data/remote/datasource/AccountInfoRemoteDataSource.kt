package com.vb.demobankapp.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.vb.demobankapp.data.remote.dto.AccountInfoDto
import com.vb.demobankapp.data.remote.dto.UserDto
import javax.inject.Inject

class AccountInfoRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {
    fun addAccount(accountInfoDto: AccountInfoDto, onResult: (Boolean) -> Unit) {
        db.collection("accounts")
            .document(accountInfoDto.accountId ?: "")
            .set(accountInfoDto)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }

    }

    fun getAccountById(accountId: String, onResult: (AccountInfoDto?) -> Unit) {
        db.collection("accounts")
            .document(accountId)
            .get()
            .addOnSuccessListener { document ->
                onResult(document.toObject(AccountInfoDto::class.java))
            }
            .addOnFailureListener { onResult(null) }
    }

    fun deleteAccount(accountId: String, onResult: (Boolean) -> Unit) {
        db.collection("accounts")
            .document(accountId)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun getAccountsByUserId(userId: String, onResult: (List<AccountInfoDto>) -> Unit) {
        db.collection("accounts")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val accounts = snapshot.toObjects(AccountInfoDto::class.java)
                onResult(accounts)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}