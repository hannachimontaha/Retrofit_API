package com.example.api

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


internal class OffreAdapter (private val data : MutableList<Offre>) : RecyclerView.Adapter<OffreAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ligne,parent,false)
        return  MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val offre : Offre = data[position]
        holder.bindView(offre)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        //récuperer tous le contenu de ligne.xml
        private var code : TextView = view.findViewById(R.id.code)
        private var intitulé : TextView = view.findViewById(R.id.intitulé)
        private var specialité : TextView = view.findViewById(R.id.specialité)
        private var société : TextView = view.findViewById(R.id.société)
        private var nbpostes : TextView = view.findViewById(R.id.nbpostes)
        private var pays : TextView = view.findViewById(R.id.pays)
        private var btn : Button = view.findViewById(R.id.delete)
        private var edit : Button = view.findViewById(R.id.edit)


        fun bindView(offre: Offre){
            code.text = "code : "+ offre.code.toString()
            intitulé.text ="intitulé : "+ offre.intitulé
            specialité.text ="specialité : "+ offre.specialité
            société.text ="société : "+ offre.société
            nbpostes.text ="nbpostes : "+ offre.nbpostes.toString()
            pays.text ="pays : "+ offre.pays
            btn.setOnClickListener { v->
                var scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    try {
                        ApiClient.apiService.deleteOffres(offre.code)
                        data.removeAt(adapterPosition)
                        notifyDataSetChanged()
                        Toast.makeText(v.context, "supprimer avec success!", Toast.LENGTH_SHORT).show()

                    } catch (e: Exception) {

                        Log.e("Error", e.message.toString())
                    }
                }
            }
            edit.setOnClickListener{ v->
                val intent = Intent(v.context, MainActivityPUT::class.java)
                intent.putExtra("code", offre.code)
                intent.putExtra("intitule", offre.intitulé)
                intent.putExtra("specialite", offre.specialité)
                intent.putExtra("societe", offre.société)
                intent.putExtra("nbpostes", offre.nbpostes)
                intent.putExtra("pays", offre.pays)

                v.context.startActivity(intent)
            }
        }
    }




}




