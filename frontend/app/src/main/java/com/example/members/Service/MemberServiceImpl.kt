package com.example.members.Service

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.members.models.Member
import org.json.JSONObject

class MemberServiceImpl : IMemberService {
    override fun getAll(context: Context, completionHandler: (response: ArrayList<Member>?) -> Unit) {
        val path = MemberSingleton.getInstance(context).baseUrl + "/api/members"
        val arrayRequest = JsonArrayRequest(Request.Method.GET, path, null,
            { response ->
                var members: ArrayList<Member> = ArrayList()
                for (i in 0 until response.length()) {
                    val member = response.getJSONObject(i)
                    val id = member.getInt("id")
                    val nombre = member.getString("nombre")
                    val apellido = member.getString("apellido")
                    val puesto = member.getString("puesto")
                    members.add(Member(id, nombre, apellido, puesto))
                }
                completionHandler(members)
            },
            { error ->
                completionHandler(ArrayList<Member>())
            })
        MemberSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getById(
        context: Context,
        memberId: Int,
        completionHandler: (response: Member?) -> Unit
    ) {
        val path = MemberSingleton.getInstance(context).baseUrl + "/api/members/" + memberId
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
            { response ->
                if (response == null) completionHandler(null)

                val id = response.getInt("id")
                val nombre = response.getString("nombre")
                val apellido = response.getString("apellido")
                val puesto = response.getString("puesto")

                val member = Member(id, nombre, apellido, puesto)
                completionHandler(member)
            },
            { error ->
                completionHandler(null)
            })
        MemberSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun deleteById(context: Context, memberId: Int, completionHandler: () -> Unit) {
        val path = MemberSingleton.getInstance(context).baseUrl + "/api/members/" + memberId
        val objectRequest = JsonObjectRequest(Request.Method.DELETE, path, null,
            { response ->
                Log.v("Hola caracola", "se borró")
                completionHandler()
            },
            { error ->
                Log.v("Hola caracola", "error")
                completionHandler()
            })
        MemberSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun updateMember(context: Context, member: Member, completionHandler: () -> Unit) {
        val path = MemberSingleton.getInstance(context).baseUrl + "/api/members/" + member.id
        val memberJson: JSONObject = JSONObject()
        memberJson.put("id", member.id.toString())
        memberJson.put("nombre", member.nombre)
        memberJson.put("apellido", member.apellido)
        memberJson.put("puesto", member.puesto)

        val objectRequest = JsonObjectRequest(Request.Method.PUT, path, memberJson,
            { response ->
                completionHandler()
            },
            { error ->
                completionHandler()
            })
        MemberSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun createMember(context: Context, member: Member, completionHandler: () -> Unit) {
        val path = MemberSingleton.getInstance(context).baseUrl + "/api/members"
        val memberJson: JSONObject = JSONObject()
        memberJson.put("id", member.id.toString())
        memberJson.put("nombre", member.nombre)
        memberJson.put("apellido", member.apellido)
        memberJson.put("puesto", member.puesto)

        val objectRequest = JsonObjectRequest(Request.Method.POST, path, memberJson,
            { response -> completionHandler() },
            { error -> completionHandler() })
        MemberSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }
}
