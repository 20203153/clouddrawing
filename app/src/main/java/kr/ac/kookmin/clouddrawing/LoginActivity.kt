package kr.ac.kookmin.clouddrawing

import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.ac.kookmin.clouddrawing.dto.User

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private var showOneTapUI = true
    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest

    private lateinit var googleLoginButton: Button

    private val TAG = "Auth"

    private val launcher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {
            Log.d(TAG, "resultCode: ${it.resultCode}")
            if(it.resultCode == Activity.RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(it.data)
                    val idToken = credential.googleIdToken

                    when {
                        idToken != null -> {
                            Log.d(TAG, "Got ID Token ${credential.displayName}")

                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener { task ->
                                    if(task.isSuccessful) {
                                        Log.d(TAG, "signInWithCredential: SUCCESS")

                                        if(task.result.additionalUserInfo?.isNewUser == true) {
                                            Log.d(TAG, "signInWithCredential: NewUser")
                                            suspend {
                                                val result = User.createCurrentUser()
                                                Log.d(TAG, "newUser created: $result")
                                                returnMain()
                                            }
                                        } else returnMain()
                                    } else {
                                        Log.d(TAG, "signInWithCredential: Failure")
                                    }
                                }
                        }
                        else -> {
                            Log.d(TAG, "Not taken Token")
                        }
                    }
                } catch(e: ApiException) {
                    Log.d(TAG, "OneTapException: ${e.localizedMessage}")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        auth = Firebase.auth
        googleLoginButton = findViewById(R.id.googleLoginButton)

        val currentUser = auth.currentUser
        if(currentUser != null) {
            Log.d(TAG, "Already logged in user ${currentUser.uid}")
            showOneTapUI = false
            auth.signOut()
            returnMain()
        }

        initSignIn()
        initListener()
    }

    private fun returnMain() {
        finish()
    }

    private fun initSignIn() {
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.google_oauth_api_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .setAutoSelectEnabled(true)
            .build()
        oneTapClient = Identity.getSignInClient(this)
    }

    private fun initListener() {
        googleLoginButton.setOnClickListener {
            oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(this) { result ->
                    try {
                        launcher.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
                    } catch (e: SendIntentException) {
                        Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                    }
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Login failure: ${e.localizedMessage}")
                }
        }
    }
}