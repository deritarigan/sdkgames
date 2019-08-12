package com.akggames.android

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.akggames.akg_sdk.AKG_SDK
import com.akggames.akg_sdk.dao.api.model.FloatingItem
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.ui.component.onTouchUtil
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.login.LoginDialogFragment
import com.facebook.CallbackManager

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var downRawX: Float = 0.toFloat()
    private var downRawY: Float = 0.toFloat()
    private var dX: Float = 0.toFloat()
    private var dY: Float = 0.toFloat()
    private lateinit var mView: View

    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatingButton.float()

        val f = supportFragmentManager
        val loginDialogFragment = LoginDialogFragment.newInstance(f)
        btnShow.setOnClickListener {
            val ftransaction = f.beginTransaction()
            ftransaction?.addToBackStack("dialog")
            loginDialogFragment.show(ftransaction, "Login")
        }



        init()
        subMarine()
        handleOnTouch()
    }


    override fun onStart() {
        super.onStart()
//        btnLogo.setOnClickListener {
//            if (!floatingButton.isFloating) {
//                floatingButton.float()
//            } else {
//                floatingButton.dip()
//
//            }
//        }
        btnDismiss.setOnClickListener {
//            if (flFloatingButton.visibility==View.VISIBLE) {
//                flFloatingButton.visibility=View.GONE
//            } else {
//                flFloatingButton.visibility=View.VISIBLE
//            }

            if (!floatingButton.isFloating) {
                floatingButton.float()
            } else {
                floatingButton.dip()
            }
        }
    }

    fun handleOnTouch(){
        floatingButton.setOnTouchListener(object:View.OnTouchListener{
            private var downRawX: Float = 0.toFloat()
            private var downRawY: Float = 0.toFloat()
            private var dX: Float = 0.toFloat()
            private var dY: Float = 0.toFloat()
            private lateinit var mView: View

            override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                val action = motionEvent?.action
                if (action == MotionEvent.ACTION_DOWN) {

                    downRawX = motionEvent.rawX
                    downRawY = motionEvent.rawY
                    dX = view!!.x - downRawX
                    dY = view.y - downRawY

                    return true // Consumed

                } else if (action == MotionEvent.ACTION_MOVE) {

                    val viewWidth = view?.width
                    val viewHeight = view?.height

                    val viewParent = view?.parent as View
                    val parentWidth = viewParent.width
                    val parentHeight = viewParent.height

                    var newX = motionEvent.rawX + dX
                    newX = Math.max(0f, newX) // Don't allow the FAB past the left hand side of the parent
                    newX = Math.min(
                        (parentWidth - viewWidth!!).toFloat(),
                        newX
                    ) // Don't allow the FAB past the right hand side of the parent

                    var newY = motionEvent.rawY + dY
                    newY = Math.max(0f, newY) // Don't allow the FAB past the top of the parent
                    newY = Math.min(
                        (parentHeight - viewHeight!!).toFloat(),
                        newY
                    ) // Don't allow the FAB past the bottom of the parent

                    view.animate()
                        .x(newX)
                        .y(newY)
                        .setDuration(0)
                        .start()

                    return true // Consumed

                } else if (action == MotionEvent.ACTION_UP) {

                    val upRawX = motionEvent.rawX
                    val upRawY = motionEvent.rawY

                    val upDX = upRawX - downRawX
                    val upDY = upRawY - downRawY

                    return if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
//                performClick()
                        if (!floatingButton.isFloating) {
                            floatingButton.float()

                        } else {
                            if(floatingButton.recyclerView.visibility==View.GONE){
                                floatingButton.recyclerView.visibility=View.VISIBLE
                                floatingButton.expandContainer()
                            }else{
                                floatingButton.recyclerView.visibility=View.GONE
                                floatingButton.shrinkContainer()
                            }
                        }
                        false
                    } else { // A drag
                        true // Consumed
                    }

                } else {
                    return view!!.onTouchEvent(motionEvent)
                }
            }

            private val CLICK_DRAG_TOLERANCE = 10f

        })
    }

    fun subMarine() {
        AKG_SDK.setFloatingButton(floatingButton,this)
    }

    fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)


//        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
//            override fun onSingleTapUp(e: MotionEvent?): Boolean {
//                if (lyMenu.visibility == View.VISIBLE) lyMenu.visibility = View.GONE
//                else lyMenu.visibility = View.VISIBLE
//                return super.onSingleTapUp(e)
//            }
//
//        })


//        var listener = View.OnTouchListener(function = { view, motionEvent ->
//            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
//                if (motionEvent.rawY - view.y > view.height) {
//                    view.y = view.y + (motionEvent.rawY - (view.y + view.height)) - view.height / 2
//                    view.x = motionEvent.rawX - view.width / 2
//                } else {
//                    if (lyMenu.visibility == View.VISIBLE) lyMenu.visibility = View.GONE
//                    else lyMenu.visibility = View.VISIBLE
//                }
//            }
//
//            true
//        })

//        fab.setOnTouchListener { view, motionEvent ->
//            if (motionEvent?.action == MotionEvent.ACTION_MOVE) {
//                view?.y = view!!.y + (motionEvent.rawY - (view.y + view.height)) - view.height / 2
//                view?.x = motionEvent.rawX - view.width / 2
//                //                    if (motionEvent.rawY - view!!.y > view.height) {
//                //
//                //                    } else {
//                //                        if (lyMenu.visibility == View.VISIBLE) lyMenu.visibility = View.GONE
//                //                        else lyMenu.visibility = View.VISIBLE
//                //                    }
//            }
//            gestureDetector.onTouchEvent(motionEvent)
//            true
//        }
//        moveableButton.setOnTouchListener { view, motionEvent ->
//            if (motionEvent?.action == MotionEvent.ACTION_MOVE) {
//                view?.y = view!!.y + (motionEvent.rawY - (view.y + view.height)) - view.height / 2
//                view?.x = motionEvent.rawX - view.width / 2
//                //                    if (motionEvent.rawY - view!!.y > view.height) {
//                //
//                //                    } else {
//                //                        if (lyMenu.visibility == View.VISIBLE) lyMenu.visibility = View.GONE
//                //                        else lyMenu.visibility = View.VISIBLE
//                //                    }
//            }
//            gestureDetector.onTouchEvent(motionEvent)
//            true
//        }
    }

    override fun onBackPressed() {
        BaseDialogFragment().onBackPressed()
    }
}
