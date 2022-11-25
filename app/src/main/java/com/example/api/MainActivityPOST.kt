package com.example.api

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPOST : AppCompatActivity() {

    lateinit var ajout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_ajout)

       ajout = findViewById(R.id.add)

       ajout.setOnClickListener{
          val code = findViewById<EditText>(R.id.code).text.toString().toInt()
           val intitule = findViewById<EditText>(R.id.intitulé).text.toString().trim()
           val specialite = findViewById<EditText>(R.id.specialité).text.toString().trim()
           val societe = findViewById<EditText>(R.id.société).text.toString().trim()
           val nbposts = findViewById<EditText>(R.id.nbp).text.toString().toInt()
           val pays = findViewById<EditText>(R.id.pays).text.toString().trim()

           val addOffre = Offre(code, intitule, specialite, societe, nbposts, pays)
           val scope = CoroutineScope(Dispatchers.Main)

           scope.launch {
               try {
                   ApiClient.apiService.addOffre(addOffre)
                       .enqueue(object : Callback<ResponseBody> {
                           override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                               Toast.makeText(this@MainActivityPOST, t.message, Toast.LENGTH_SHORT).show()
                           }

                           override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                               Toast.makeText(this@MainActivityPOST, "ajouter avec success!", Toast.LENGTH_SHORT).show()
                               val intent = Intent(applicationContext, MainActivity::class.java)
                               startActivity(intent)
                           }
                       })
                   } catch (e: Exception) {
                       Log.e("Error", e.message.toString())
                   }
           }
        }
    }
}