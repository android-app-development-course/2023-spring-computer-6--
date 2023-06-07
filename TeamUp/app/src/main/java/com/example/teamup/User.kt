package com.example.teamup

import cn.bmob.v3.BmobObject



class User(    var userName:String? =null,
               var account:String? =null,
               var password:String? =null,
               var university:String? =null,
               var major:String? =null,
               var gender:String? =null,
               var resume:String? =null,
               var joinTeam:Array<TeamInfo>? =null,
               var leadTeam:Array<TeamInfo>? =null):BmobObject( )


//data class User(
//    //    var Id: String? =null
//    var userName:String,
//    var account:String,
//    var password:String,
//    var university:String,
//    var major:String,
//    var gender:String,
//    var resume:String,
//    var joinTeam:Array<TeamInfo>,
//    var leadTeam:Array<TeamInfo>,
//)




