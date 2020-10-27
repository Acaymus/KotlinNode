package com.example.members

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.example.members.models.Member
import com.example.members.Service.MemberServiceImpl
import com.example.members.Service.MemberSingleton
import com.squareup.picasso.Picasso


class MemberDetailActivity: AppCompatActivity (){
    private lateinit var state: String
    private lateinit var textInputEditTextNombre: EditText
    private lateinit var textInputEditTextApellido: EditText
    private lateinit var textInputEditTextPuesto: EditText
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        state = this.intent.getStringExtra("state").toString()

        val memberId = this.intent.getIntExtra("memberId", 1)

        textInputEditTextNombre = findViewById(R.id.TextInputEditTextNombre)
        textInputEditTextApellido = findViewById(R.id.TextInputEditTextApellido)
        textInputEditTextPuesto = findViewById(R.id.TextInputEditTextPuesto)

        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            deleteMember(memberId)
        }

        if (state == "Showing") getMember(memberId)

        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            when (state) {
                "observando" -> {
                    changeButtonsToEditing()
                }
                "Editando" -> {
                    val member = Member(
                        memberId,
                        textInputEditTextNombre.text.toString(),
                        textInputEditTextApellido.text.toString(),
                        textInputEditTextPuesto.text.toString()
                    )
                    updateMember(member)
                }
                "Añadiendo" -> {
                    val member = Member(
                        memberId,
                        textInputEditTextNombre.text.toString(),
                        textInputEditTextApellido.text.toString(),
                        textInputEditTextPuesto.text.toString()
                    )
                    createMember(member)
                }
            }
        }

        if (state == "Añadiendo") changeButtonsToAdding()
    }

    private fun updateMember(member: Member) {
        val memberServiceImpl = MemberServiceImpl()
        memberServiceImpl.updateMember(this, member) { ->
            run {
                changeButtonsToShowing(member.id)
                val intent = Intent(this, memberListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createMember(member: Member) {
        val memberServiceImpl = MemberServiceImpl()
        memberServiceImpl.createMember(this, member) { ->
            run {
                changeButtonsToShowing(member.id)
                val intent = Intent(this, memberListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeButtonsToAdding() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Añadir miembro")
        textInputEditTextNombre.isEnabled = true
        textInputEditTextApellido.isEnabled = true
        textInputEditTextPuesto.isEnabled = true
        state = "Añadiendo"
    }

    private fun changeButtonsToShowing(memberId: Int) {
        buttonDelete.visibility = View.VISIBLE
        buttonDelete.isEnabled = true
        buttonEdit.setText("Editando miembro")
        textInputEditTextNombre.isEnabled = false
        textInputEditTextApellido.isEnabled = false
        textInputEditTextPuesto.isEnabled = false
        state = "Observando"
    }

    private fun changeButtonsToEditing() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Aplicando cambios")
        textInputEditTextNombre.isEnabled = true
        textInputEditTextApellido.isEnabled = true
        textInputEditTextPuesto.isEnabled = true
        state = "Editando"
    }

    private fun getMember(memberId: Int) {
        val memberServiceImpl = MemberServiceImpl()
        memberServiceImpl.getById(this, memberId) { response ->
            run {

                val txt_nombre: TextInputEditText = findViewById(R.id.TextInputEditTextNombre)
                val txt_apellido: TextInputEditText =
                    findViewById(R.id.TextInputEditTextApellido)
                val txt_puesto: TextInputEditText = findViewById(R.id.TextInputEditTextPuesto)
                val img: ImageView = findViewById(R.id.imageViewMember)

                txt_nombre.setText(response?.nombre ?: "")
                txt_apellido.setText(response?.apellido ?: "")
                txt_puesto.setText(response?.puesto.toString() ?: "")

                val url = MemberSingleton.getInstance(this).baseUrl + "/img/member-"
                val imageUrl = url + (response?.id.toString() ?: "0") + ".jpg"
                Picasso.with(this).load(imageUrl).into(img);
            }
        }
    }

    private fun deleteMember(memberId: Int) {
        val memberServiceImpl = MemberServiceImpl()
        memberServiceImpl.deleteById(this, memberId) { ->
            run {
                val intent = Intent(this, memberListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}