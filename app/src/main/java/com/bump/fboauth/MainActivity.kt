package com.bump.fboauth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var callBackManager: CallbackManager
    lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFacebookManager()

        fbLoginBtn.setOnClickListener {
            FBService.registerCallback (loginManager, callBackManager) { isSucceeded, message ->
                if (isSucceeded && message != null) {
                    startActivity(Intent(this, LogActivity::class.java))
                } else if (!isSucceeded && message != null) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }.logInWithReadPermissions(this, listOf("email", "public_profile", "user_friends"))
        }
    }

    fun initFacebookManager() {
        callBackManager = CallbackManager.Factory.create()
        loginManager = LoginManager.getInstance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode, resultCode, data)
    }

}
