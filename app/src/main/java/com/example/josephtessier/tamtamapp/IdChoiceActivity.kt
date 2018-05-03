package com.example.josephtessier.tamtamapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_createaccount.*
import kotlinx.android.synthetic.main.activity_id_choice.*

class IdChoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_choice)

        identificationCV.setOnClickListener {
            enregistrement()
        }

        inscriptionCV.setOnClickListener{
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }


        /*LOGINmotDePasseEdt.setOnKeyListener(View.OnKeyListener{ _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                enregistrement()
                return@OnKeyListener true
            }
            false
        })*/
    }
    private fun enregistrement() {
        if(!TextUtils.isEmpty(LOGINemailEdt.text.toString().trim()) && !TextUtils.isEmpty(LOGINmotDePasseEdt.text.toString().trim())){
            var email = LOGINemailEdt.text.toString().trim()
            var password = LOGINmotDePasseEdt.text.toString().trim()
            if(email.contains('@') && email.contains('.')) {
                Toast.makeText(this, email + " / " + password, Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this, HomePage::class.java))
//                    finish()
            } else Toast.makeText(this, "Email incorrect", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(LOGINmotDePasseEdt.getWindowToken(), 0)
    }



/* POUR MEMOIRE BDD
    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)


    }

    override fun onStop() {
        super.onStop()

        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }

    }*/
}
