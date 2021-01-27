package com.gtu.replyme

import android.app.Activity
import android.app.WallpaperColors
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class LoginActivityTest{

    private lateinit var successTask: Task<AuthResult>
    private lateinit var failureTask: Task<AuthResult>


    @Mock private lateinit var mAuth: FirebaseAuth
     private lateinit var mLoginActivity: LoginActivity

    private var logInResult = UNDEF

    companion object {
        private const val SUCCESS = 1
        private const val FAILURE = -1
        private const val UNDEF = 0
    }

    @Before
    fun setUp() {

        @Mock
        mAuth = FirebaseAuth.getInstance()
        MockitoAnnotations.initMocks(this)
        successTask = object : Task<AuthResult>() {
            override fun isComplete(): Boolean = true

            override fun isSuccessful(): Boolean = true
            // ...
            override fun addOnCompleteListener(executor: Executor,
                                               onCompleteListener: OnCompleteListener<AuthResult>
            ): Task<AuthResult> {
                onCompleteListener.onComplete(successTask)
                return successTask
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun getResult(): AuthResult? {
                TODO("Not yet implemented")
            }

            override fun <X : Throwable?> getResult(p0: Class<X>): AuthResult? {
                TODO("Not yet implemented")
            }

            override fun getException(): Exception? {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(p0: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(
                p0: Executor,
                p1: OnSuccessListener<in AuthResult>
            ): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(
                p0: Activity,
                p1: OnSuccessListener<in AuthResult>
            ): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(p0: OnFailureListener): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(
                p0: Executor,
                p1: OnFailureListener
            ): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(
                p0: Activity,
                p1: OnFailureListener
            ): Task<AuthResult> {
                TODO("Not yet implemented")
            }
        }

        failureTask = object : Task<AuthResult>() {
            override fun isComplete(): Boolean = true

            override fun isSuccessful(): Boolean = false
            // ...
            override fun addOnCompleteListener(executor: Executor,
                                               onCompleteListener: OnCompleteListener<AuthResult>): Task<AuthResult> {
                onCompleteListener.onComplete(failureTask)
                return failureTask
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun getResult(): AuthResult? {
                TODO("Not yet implemented")
            }

            override fun <X : Throwable?> getResult(p0: Class<X>): AuthResult? {
                TODO("Not yet implemented")
            }

            override fun getException(): Exception? {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(p0: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(
                p0: Executor,
                p1: OnSuccessListener<in AuthResult>
            ): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(
                p0: Activity,
                p1: OnSuccessListener<in AuthResult>
            ): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(p0: OnFailureListener): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(
                p0: Executor,
                p1: OnFailureListener
            ): Task<AuthResult> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(
                p0: Activity,
                p1: OnFailureListener
            ): Task<AuthResult> {
                TODO("Not yet implemented")
            }
        }
        mLoginActivity = LoginActivity()

    }

    @Test
    fun logInSuccess_test() {

        val email = "muhammed2132@gmail.com"
        val password = "123456"
        Mockito.`when`(mAuth!!.signInWithEmailAndPassword(email, password))
            .thenReturn(successTask)
        mLoginActivity!!.LogIn(email,password)
        assert(logInResult == SUCCESS)
    }

}