@file:Suppress("Deprecation")
package com.cashbox.android.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.data.api.ApiClient
import com.cashbox.android.data.datastore.DataStoreInstance
import com.cashbox.android.data.datastore.UserPreference
import com.cashbox.android.data.model.LoginGoogleBody
import com.cashbox.android.data.repository.UserRepository
import com.cashbox.android.databinding.ActivityLoginBinding
import com.cashbox.android.ui.main.MainActivity
import com.cashbox.android.ui.viewmodel.ViewModelFactory
import com.cashbox.android.utils.AnimationHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@SuppressLint("SourceLockedOrientationActivity")
class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val binding by viewBinding(ActivityLoginBinding::bind)
    private val loginViewModel by lazy {
        val factory = ViewModelFactory(UserRepository(ApiClient.apiClient))
        ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }
    private val userPreference by lazy {
        UserPreference(DataStoreInstance.getInstance(this))
    }
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOrientationAndStatusBar(R.color.sky_blue)
        setupButtons()
        setupGoogleSignIn()
        setupObservers()
    }

    private fun setupOrientationAndStatusBar(color: Int) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val resolvedColor = ContextCompat.getColor(this, color)
        if (window.statusBarColor != resolvedColor) {
            window.statusBarColor = resolvedColor
            window.decorView.systemUiVisibility = if (color == R.color.sky_blue) 0 else
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun setupButtons() {
        AnimationHelper.applyTouchAnimation(binding.btnLogin)
        AnimationHelper.applyTouchAnimation(binding.btnLoginWithGoogle)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLoginWithGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun setupGoogleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("806241381486-ihesjpl253igvodd3css54jijjh02lfs.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated onActivityResult Method")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                showToast("Google Sign In Failed")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    getIdToken(user!!)
                } else {
                    showToast("Authentication Failed")
                }
            }
    }

    private fun getIdToken(user: FirebaseUser) {
        user.let {
            it.getIdToken(true).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken = task.result?.token
                    loginViewModel.userLoginWithGoogle(LoginGoogleBody(idToken!!))
                } else {
                    showToast("Getting Id Token Failed")
                }
            }
        }
    }

    private fun setupObservers() {
        loginViewModel.loginResponse.observe(this) { response ->
            lifecycleScope.launch {
                userPreference.updateUserLoginStatus(true)
                userPreference.updateUsernameAndEmail(response.user.name, response.user.email)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }

        loginViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.tvLogin.visibility = View.GONE
                binding.pbLogin.visibility = View.VISIBLE
            } else {
                binding.tvLogin.visibility = View.VISIBLE
                binding.pbLogin.visibility = View.GONE
            }
        }

        loginViewModel.errorMessage.observe(this) { message ->
            showToast(message)
        }

        loginViewModel.exception.observe(this) { exception ->
            if (exception) {
                showToast("No Internet Connection")
                loginViewModel.resetExceptionValue()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val RC_SIGN_IN = 1001
    }
}