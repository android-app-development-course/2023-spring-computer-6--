package com.example.teamup.DataClass
import cn.bmob.v3.BmobObject

class TeamInfo(
    var leader:String?=null,    // 队长的 User.ObjectID
    var competitionName:String?=null,
    var information:String?=null,
    var deadline:String?=null,
    var expectedNum: Int?=null,
    var members:Array<String>?=null    // 队员的 User.ObjectID
):BmobObject() {
    // 获得现在的人数
    fun curNum():Int = (members?.size ?: 0) + 1

    // 判断是否满员
    fun isFull():Boolean =  curNum() >= (expectedNum ?: 0)

    // 判断是否为队长
    fun isLeader(UserID : String):Boolean = leader!! == UserID

    // 判断是否为队员
    fun isMember(UserID : String):Boolean = members?.contains(UserID) == true
}