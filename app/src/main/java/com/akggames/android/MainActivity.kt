package com.akggames.android

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akggames.android.ui.dialog.LoginDialog
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import com.akggames.akg_sdk.ui.dialog.LoginDialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val f = supportFragmentManager
        val loginDialogFragment=  LoginDialogFragment.newInstance(f)


        val loginDialog = LoginDialog(this, R.style.DialogSlideAnimCoconutFullScreen, application)

        btnShow.setOnClickListener {
            val ftransaction = f.beginTransaction()
//            ftransaction.add(loginDialogFragment,"Login")
            loginDialogFragment.show(ftransaction, "Login")

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("FRAGMENT_GOOGLE", "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
        }
    }
}
