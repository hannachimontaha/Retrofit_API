package com.example.api

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var ajouter : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ajouter = findViewById(R.id.delete)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val scope = CoroutineScope(Dispatchers.Main)

        ajouter.setOnClickListener{

            val intent = Intent(applicationContext, MainActivityPOST::class.java)
            startActivity(intent)
        }
      /*  override fun onItemEditCLick(user: User) {
            val intent = Intent(this@MainActivity, CreateNewUserActivity::class.java)
            intent.putExtra("user_id", user.id)
            startActivityForResult(intent, 1000)*/

        scope.launch {
            try {
                val response = ApiClient.apiService.getOffres()

                if (response.isSuccessful && response.body() != null) {
                        recyclerView.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = OffreAdapter(response.body()!!) }
                    }

                    else {
                        Log.e("Error", response.message())
                    }

                } catch (e: Exception) {
                    Log.e("Error", e.message.toString())
                }

            }



        }
    }
