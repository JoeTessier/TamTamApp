package com.example.josephtessier.tamtamapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_createaccount.*

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener{
            enregistrement()
        }

        loginPasswordEdt.setOnKeyListener(View.OnKeyListener{ _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                enregistrement()
                return@OnKeyListener true
            }
            false
        })
    }
    private fun enregistrement() {
        if(!TextUtils.isEmpty(loginEmailEdt.text.toString().trim()) && !TextUtils.isEmpty(loginPasswordEdt.text.toString().trim())){
            var email = loginEmailEdt.text.toString().trim()
            var password = loginPasswordEdt.text.toString().trim()
            if(email.contains('@') && email.contains('.')) {
                Toast.makeText(this, email + " / " + password, Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this, HomePage::class.java))
//                    finish()
            } else Toast.makeText(this, "Email incorrect", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
    }
}
