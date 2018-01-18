package com.example.josephtessier.tamtamapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.josephtessier.tamtamapp.R.id.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    override fun onNothingSelected(parent: AdapterView<*>?) {
//        Toast.makeText(this, "Choisir une région", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { }


    // details sur l'utilisateur
    var userEmail: String? = null
    var userAge: Int? = null
    var userRegion: String? = null
    var userSexe: Char? = null
    var userThemes: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // POUR LE RECYCLER.VIEW
        val listDeThemes = arrayOf("Loisirs", "Fêtes", "Auto-moto", "Shopping", "Multimédias", "Maison-travaux", "Services", "Beauté-soins", "Gastronomie", "Restaurant")
        var themeArrayList: ArrayList<Theme> = ArrayList()

        for (i in 0 until listDeThemes.size) {
            var theme = Theme()
            theme.nomTheme = listDeThemes[i]
            theme.isSelected = false
            themeArrayList.add(theme)
        }



        val layoutManager = LinearLayoutManager(this)
        val adapter = ThemeAdapter(themeArrayList, this)

        //setup list recyclerView
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()



        // on cache les CardViews supplémentaires, on ne se concentre que sur le 1e
        cv2.visibility = View.INVISIBLE
        cv3.visibility = View.INVISIBLE
        cv4.visibility = View.INVISIBLE
        cv5.visibility = View.INVISIBLE
        sendAllDetailsButton.visibility = View.INVISIBLE
        previousBtn.visibility = View.INVISIBLE
        nextBtn.visibility = View.INVISIBLE

        previousBtn.setOnClickListener {
            nextBtn.visibility = View.VISIBLE
            when {
                cv2.visibility == View.VISIBLE -> {
                    switchCV(cv2, cv1)
                    previousBtn.visibility = View.INVISIBLE
                }
                cv3.visibility == View.VISIBLE -> switchCV(cv3, cv2)
                cv4.visibility == View.VISIBLE -> switchCV(cv4, cv3)
                cv5.visibility == View.VISIBLE -> switchCV(cv5, cv4)
            }
        }
        nextBtn.setOnClickListener {
            previousBtn.visibility = View.VISIBLE
            nextBtn.visibility = View.VISIBLE
            when {
                cv1.visibility == View.VISIBLE -> switchCV(cv1, cv2)
                cv2.visibility == View.VISIBLE -> switchCV(cv2, cv3)
                cv3.visibility == View.VISIBLE -> switchCV(cv3, cv4)
                cv4.visibility == View.VISIBLE -> {
                    switchCV(cv4, cv5)
                    nextBtn.visibility = View.INVISIBLE
                    sendAllDetailsButton.visibility = View.VISIBLE
                }
            }
        }

        val listDeRegions = arrayOf("— choix de région —".toUpperCase(), "region1", "region2", "region3", "region4", "region5", "region6")
        spinnerID.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listDeRegions)
        spinnerID.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                userRegion = listDeRegions[position]
            }

        }

        // liste des actions_listeners pour enregistrer les données
        sendEmailBtn.setOnClickListener {
            closeKeyboard()
            registerMail()
        }

        loginEmail.setOnKeyListener(View.OnKeyListener{ view, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                closeKeyboard()
                registerMail()
                return@OnKeyListener true
            }
            false
        })

        sexHommeBtn.setOnClickListener{
            userSexe = 'H'
            Toast.makeText(this, userSexe.toString(), Toast.LENGTH_SHORT).show()
            switchCV(cv2, cv3)
        }

        sexFemmeBtn.setOnClickListener {
            userSexe = 'F'
            Toast.makeText(this, userSexe.toString(), Toast.LENGTH_SHORT).show()
            switchCV(cv2, cv3)
        }

        sendAgeBtn.setOnClickListener {
            var age = RGroup.checkedRadioButtonId
            when (age) {
                radioButton15_25 -> userAge = 15
                radioButton25_40 -> userAge = 25
                radioButton40_60 -> userAge = 40
                radioButton60_plus -> userAge = 60

            }
            if(userAge.toString() != "null") {
                Toast.makeText(this, userAge.toString(), Toast.LENGTH_SHORT).show()
                switchCV(cv3, cv4)
            } else Toast.makeText(this, "Précisez votre âge", Toast.LENGTH_SHORT).show()
        }


        btnSubmit.setOnClickListener {
            if (userRegion!!.contains("—")) {
                Toast.makeText(this, "Sélectionner une région", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, userRegion, Toast.LENGTH_SHORT).show()
                switchCV(cv4, cv5)
                nextBtn.visibility = View.INVISIBLE
            }
        }

        // envoie de toutes les données
        sendAllDetailsButton.setOnClickListener {

            // récupération des themes enregistrés
            (0 until themeArrayList.size)
                    .filter { themeArrayList[it].isSelected }
                    .forEach { userThemes.add(themeArrayList[it].nomTheme) }
            Log.d("arrayListThemes >>>> ", userThemes.toString())

            Toast.makeText(this, userThemes.toString(), Toast.LENGTH_LONG).show()

            // on envoie les infos si le mail est correct
            if (!TextUtils.isEmpty(loginEmail.text.toString().trim())){
                Toast.makeText(this, "sending info", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Rentrer un email", Toast.LENGTH_SHORT).show()
                if(cv5.visibility == View.VISIBLE) {
                    switchCV(cv5, cv1)
                    nextBtn.visibility = View.VISIBLE
                    previousBtn.visibility = View.VISIBLE
                }
                if(cv4.visibility == View.VISIBLE) switchCV(cv4, cv1)
                if(cv3.visibility == View.VISIBLE) switchCV(cv3, cv1)
                if(cv2.visibility == View.VISIBLE) switchCV(cv2, cv1)
            }
        }

    }


    private fun switchCV(depart: CardView, destination: CardView){
        depart.visibility = View.INVISIBLE
        destination.visibility = View.VISIBLE
    }

    private fun registerMail(){
        // logiquement faudrait aussi vérifier que ce mail n'est pas déjà référencé dans notre BDD
        var email = loginEmail.text.toString().trim()
        if(!TextUtils.isEmpty(email)) {
            if (email.contains('@') && email.contains('.')) {
                userEmail = email
                Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show()
                switchCV(cv1, cv2)
                nextBtn.visibility = View.VISIBLE
                previousBtn.visibility = View.VISIBLE
            } else Toast.makeText(this, "Email invalide", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "Renseigner un email", Toast.LENGTH_SHORT).show()
    }

    private fun closeKeyboard(){
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(loginEmail.getWindowToken(), 0)
    }

}
class Theme {
    var nomTheme: String = ""
    var isSelected: Boolean = false
}


