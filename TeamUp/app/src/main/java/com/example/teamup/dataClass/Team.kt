package com.example.teamup.dataClass

data class Team(
    var id:Int,         // 唯一标识id
    var title:String,   // 标题
    var summary:String, // 简介
    var date:String,    // 创建日期（年-月-日）
    var time:String,    // 创建时间（时:分）
    var imgUri:String,  // 图像源地址Uri
    var imgId: Int,     // 图片的本地地址id
    var maxMember: Int, // 最大参加人数
    var numMember: Int, // 已加入人数
)
