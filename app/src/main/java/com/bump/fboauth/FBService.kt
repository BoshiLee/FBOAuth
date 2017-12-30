package com.bump.fboauth

import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import org.json.JSONException

/**
 * Created by boshili on 2017-12-29.
 */
object FBService {

    fun registerCallback(loginManager: LoginManager, callbackManager: CallbackManager ,complete: (Boolean, String?) -> Unit) : LoginManager {

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val accessToken = result?.accessToken
                Log.d("Success", "facebook login success")
                println(accessToken)
                val request = GraphRequest.newMeRequest(accessToken) { returnObject, response ->

                    try {
                        println(returnObject)
                        val email = returnObject.getString("email")

                        complete(true, null)

                    } catch (e: JSONException) {
                        complete(false, e.localizedMessage)
                    }
                }
                request.parameters.putString("fields", "email, name, id, gender, picture.type(large)")
                request.executeAsync()
            }

            override fun onError(error: FacebookException?) {
                complete(false, error?.localizedMessage)
            }

            override fun onCancel() {
                complete(false, "Cancel login")
            }
        })
        return loginManager
    }

}