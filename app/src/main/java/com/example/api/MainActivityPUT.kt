package com.example.api

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityPUT : AppCompatActivity() {
    lateinit var a: EditText
    lateinit var b: EditText
    lateinit var c: EditText
    lateinit var d: EditText
    lateinit var e: EditText
    lateinit var f: EditText
    lateinit var modifier: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_update)

        a = findViewById(R.id.a)
        b = findViewById(R.id.b)
        c = findViewById(R.id.c)
        d = findViewById(R.id.d)
        e = findViewById(R.id.e)
        f = findViewById(R.id.f)


        val extras = intent.extras
        val val1 = extras!!.getInt("code").toString()
        val id = extras!!.getInt("code")
        val val2 = extras!!.getString("intitule")
        val val3 = extras!!.getString("specialite")
        val val4 = extras!!.getString("societe")
        val val5 = extras!!.getInt("nbpostes").toString()
        val val6 = extras!!.getString("pays")

        a.setText(val1)
        b.setText(val2)
        c.setText(val3)
        d.setText(val4)
        e.setText(val5)
        f.setText(val6)

        modifier = findViewById(R.id.edit)
        modifier.setOnClickListener {
            val code = findViewById<EditText>(R.id.a).text.toString().toInt()
            val intitule = findViewById<EditText>(R.id.b).text.toString().trim()
            val specialite = findViewById<EditText>(R.id.c).text.toString().trim()
            val societe = findViewById<EditText>(R.id.d).text.toString().trim()
            val nbposts = findViewById<EditText>(R.id.e).text.toString().toInt()
            val pays = findViewById<EditText>(R.id.f).text.toString().trim()

            val updateoffre = Offre(code, intitule, specialite, societe, nbposts, pays)
            val scope = CoroutineScope(Dispatchers.Main)

            scope.launch {
                try {
                   ApiClient.apiService.updateOffre(updateoffre, id)

                    val builerP = AlertDialog.Builder(this@MainActivityPUT)
                    val dialog = builerP.create()
                    val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
                    val msg = dialogView.findViewById<TextView>(R.id.ms)
                    msg.text = "updating informations..."
                    dialog.setView(dialogView)
                    dialog.setCancelable(false)
                    dialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({ dialog.dismiss() }, 30000)


                                Toast.makeText(this@MainActivityPUT, "modifier avec success!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)

                } catch (e: Exception) {
                    Log.e("Error", e.message.toString())
                }
            }

        }
    }
}
