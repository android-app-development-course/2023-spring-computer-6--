package com.example.teamup.DataClass

import cn.bmob.v3.BmobObject

class User(
    var userName:String? =null,
    var account:String? =null,
    var password:String? =null,
    var university:String? =null,
    var major:String? =null,
    var gender:String? =null,
    var resume:String? =null,
    var joinTeam:Array<String>? =null, // 作为队员的 TeamInfo.ObjectID
    var leadTeam:Array<String>? =null // 作为队长的 TeamInfo.ObjectID
):BmobObject() {
}