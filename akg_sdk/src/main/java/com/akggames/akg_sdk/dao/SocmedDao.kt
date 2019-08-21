package com.akggames.akg_sdk.dao

import android.app.Activity
import android.content.Context
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.presenter.LogoutPresenter
import com.akggames.akg_sdk.rx.IView
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient

object SocmedDao {

    fun logoutFacebook(activity: Activity,iView : IView,presenter: LogoutPresenter){

        LoginManager.getInstance().logOut()
        presenter.logout(activity)
    }

    fun logoutGoogle(activity: Activity,iView : IView,presenter: LogoutPresenter){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(IConfig.GOOGLE_CLIENT_ID)
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)

        mGoogleSignInClient.signOut().addOnCompleteListener {
            presenter.logout(activity)
        }
    }

    fun logoutPhone(activity: Activity,iView : IView,presenter: LogoutPresenter){
        presenter.logout(activity)
    }
    fun logoutGuest(activity: Activity,iView : IView,presenter: LogoutPresenter){
        presenter.logout(activity)
    }
}