package com.example.zacy.projectundytest0

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import android.R.attr.data
import com.google.firebase.auth.AuthResult
import android.support.annotation.NonNull
import android.support.constraint.ConstraintLayout
import android.webkit.JavascriptInterface
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential
import com.google.android.gms.common.api.GoogleApiClient


class MainActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
    var webView: WebView? = null
    val RC_SIGN_IN = 0;
    private var mGoogleApiClient: GoogleApiClient? = null

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance();
        webView = findViewById<WebView>(R.id.webView)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("71717485961-qq5125ih36lvjud9gj1irakh1qhnuceg.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
        webView!!.settings.loadsImagesAutomatically = true
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.domStorageEnabled = true
        webView!!.addJavascriptInterface(JavaScriptInterface(), "Android")
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView!!.loadUrl("file:///android_asset/signin.html")
    }

    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun logout() {
            FirebaseAuth.getInstance().signOut()
            mGoogleSignInClient!!.signOut()
        }
        @JavascriptInterface
        fun login(){
            signIn()
        }
    }

    public override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.getCurrentUser()
        if(currentUser!=null){
        updateUI(currentUser)
        }
    }

    fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // Sign in fails
                        updateUI(null)
                    }
                }
    }

    fun updateUI(account: FirebaseUser?){
        if(account!=null){
            webView!!.settings.loadsImagesAutomatically = true
            webView!!.settings.javaScriptEnabled = true
            webView!!.settings.domStorageEnabled = true
            webView!!.loadUrl("javascript:signedIn('"+account.displayName+"','"+account.email+"')")
        }
    }
}
