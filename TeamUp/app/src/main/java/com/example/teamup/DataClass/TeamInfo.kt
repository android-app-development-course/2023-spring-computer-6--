package com.example.teamup.DataClass
import cn.bmob.v3.BmobObject

class TeamInfo(
    var leader:String?=null,    // 队长的 User.ObjectID
    var competitionName:String?=null,
    var information:String?=null,
    var deadline:String?=null,
    var expectedNum: Int?=null,
    var curNum:Int?=null,
    var members:Array<String>?=null    // 队员的 User.ObjectID
):BmobObject()