package kr.ac.kookmin.clouddrawing

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.ac.kookmin.clouddrawing.components.GoogleSignupBtn
import kr.ac.kookmin.clouddrawing.dto.User

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var instance: SignupActivity

    private var showOneTapUI = true
    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest

    private val TAG = "Auth"

    @OptIn(DelicateCoroutinesApi::class)
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
                                            GlobalScope.launch {
                                                val result = User.createCurrentUser()
                                                Log.d(TAG, "newUser created: $result")
                                                returnMain()
                                            }
                                        } else {
                                            Log.d(TAG, "SignInWithCredential: Update LastLogin")
                                            GlobalScope.launch {
                                                val result = User.getCurrentUser()!!
                                                result.updateLastLogin()
                                                returnMain()
                                            }
                                        }
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

        instance = this

        setContent {
            make(onClickGoogle = {
                oneTapClient.beginSignIn(signUpRequest)
                    .addOnSuccessListener(this) { result ->
                        try {
                            launcher.launch(
                                IntentSenderRequest.Builder(result.pendingIntent.intentSender)
                                    .build()
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Login failure: ${e.localizedMessage}")
                    }
            })
        }

        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null) {
            Log.d(TAG, "Already logged in user ${currentUser.uid}")
            showOneTapUI = false
            returnMain()
        }

        initSignIn()
    }

    private fun returnMain() {
        startActivity(Intent(instance, MainActivity::class.java))
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

    companion object {
        @Composable
        fun make(onClickGoogle: () -> Unit = {}) {
            return Box(
                Modifier.background(color=Color(0xFFE3ECFF))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.loadinglogo),
                        contentDescription = "image description",
                        contentScale = ContentScale.None,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize(1f)
                ) {
                    GoogleSignupBtn(onClickGoogle)
                    Spacer(Modifier.height(85.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    SignupActivity.make()
}