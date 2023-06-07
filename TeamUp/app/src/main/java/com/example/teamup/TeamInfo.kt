package com.example.teamup
import cn.bmob.v3.BmobObject


class TeamInfo(    var leader:String?=null,
                   var competitionName:String?=null,
                   var information:String?=null,
                   var deadline:String?=null,
                   var expectedNum:Int?=null,
                   var curNum:Int?=null,
                   var members:Array<String>?=null):BmobObject()



//data class TeamInfo(
//    var objectId:String,
//    var leader:User,
//    var competitionName:String,
//    var information:String,
//    var deadline:String,
//    var expectedNum:Int,
//    var curNum:Int,
//    var members:Array<User>
//
//
//)
