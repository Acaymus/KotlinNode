package com.example.members

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.members.models.Member
import com.squareup.picasso.Picasso

class MemberAdapter(var  memberList: ArrayList<Member>, val context: Context):
    RecyclerView.Adapter<MemberAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.member_list_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(memberList[position], context)
    }

    override fun getItemCount(): Int {
        return memberList.size;
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindView(m: Member, context: Context){
            val url = "http://192.168.1.34:8080/img/member-"
         val txt_nombre: TextView = itemView.findViewById(R.id.textViewNombre)
         val txt_apellido: TextView = itemView.findViewById(R.id.textViewApellido)
         val txt_puesto: TextView = itemView.findViewById(R.id.textViewPuesto)
         val img: ImageView = itemView.findViewById(R.id.imageViewMember)
            txt_nombre.text=m.nombre
            txt_apellido.text=m.apellido
            txt_puesto.text=m.puesto

            val imageUrl = url + m.id.toString() + ".jpg"
            Picasso.with(context).load(imageUrl).into(img);

            itemView.setOnClickListener {
                val intent = Intent(context, MemberDetailActivity::class.java)
                intent.putExtra("memberId", m.id)
                intent.putExtra("state", "observando")
                Log.v("hola caracola antes", m.id.toString())
                context.startActivity(intent)
            }
        }
    }
}