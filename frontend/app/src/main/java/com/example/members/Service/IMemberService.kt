package com.example.members.Service

import android.content.Context
import com.example.members.models.Member

interface IMemberService {
    fun getAll(context: Context, completionHandler: (response: ArrayList<Member>?) -> Unit)

    fun getById(context: Context, memberId: Int, completionHandler: (response: Member?) -> Unit)

    fun deleteById(context: Context, memberId: Int, completionHandler: () -> Unit)

    fun updateMember(context: Context, member: Member, completionHandler: () -> Unit)

    fun createMember(context: Context, member: Member, completionHandler: () -> Unit)
}