package com.vb.demobankapp.domain.model

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val birthDate: String,
    val profileImageUrl: String,
    val createdAt: Long
)
