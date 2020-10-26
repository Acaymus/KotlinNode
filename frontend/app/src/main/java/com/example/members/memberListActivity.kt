package com.example.members

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.members.models.Member
import com.example.members.Service.MemberServiceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton


class memberListActivity : AppCompatActivity() {
    private lateinit var members: ArrayList<Member>
    private lateinit var recycleView: RecyclerView
    private lateinit var viewAdapter: MemberAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)

        members=ArrayList<Member>()

        viewManager = LinearLayoutManager(this)

        viewAdapter = MemberAdapter(members, this)

        recycleView = findViewById<RecyclerView>(R.id.recycleViewMembers)

        recycleView.layoutManager = viewManager

        recycleView.adapter = viewAdapter

        getAllMembers()

        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton2)
        fab.setOnClickListener {
            val intent = Intent(this, MemberDetailActivity::class.java)
            intent.putExtra("state", "Adding")
            startActivity(intent)
        }
    }

    private fun getAllMembers() {

        val memberServiceImpl = MemberServiceImpl()
        memberServiceImpl.getAll(this) { response ->
            run {
                if (response != null) {
                    viewAdapter.memberList = response
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }
}