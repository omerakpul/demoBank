package com.vb.demobankapp.presentation.ui.auth.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    private val auth = FirebaseAuth.getInstance()
    private var verificationId: String? = null

    fun sendOtp(phoneNumber: String, activity: Activity) {
        viewModelScope.launch {
            _state.value = LoginState.Loading

            try {
                val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // Otomatik doğrulama (test cihazlarında)
                        _state.value = LoginState.OtpSent
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        _state.value = LoginState.Error(e.message ?: "OTP gönderilemedi")
                    }

                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                        this@LoginViewModel.verificationId = verificationId
                        _state.value = LoginState.OtpSent
                    }

                    override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                        _state.value = LoginState.Error("OTP zaman aşımı")
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
                _state.value = LoginState.Error(e.message ?: "OTP gönderilemedi")
            }
        }
    }

    fun validateOtp(otp: String, onResult: (Boolean) -> Unit) {
        val verId = verificationId
        if (verId == null) {
            onResult(false)
            return
        }

        val credential = PhoneAuthProvider.getCredential(verId, otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun resetState() {
        _state.value = LoginState.Idle
    }
}