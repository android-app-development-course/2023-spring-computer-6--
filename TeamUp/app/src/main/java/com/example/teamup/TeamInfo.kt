package com.example.teamup

import java.util.Date

data class TeamInfo(
    var objectId:String,
    var leader:User,
    var competitionName:String,
    var information:String,
    var deadline:String,
    var expectedNum:Int,
    var curNum:Int,
    var members:Array<User>


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeamInfo

        if (objectId != other.objectId) return false
        if (leader != other.leader) return false
        if (competitionName != other.competitionName) return false
        if (information != other.information) return false
        if (deadline != other.deadline) return false
        if (expectedNum != other.expectedNum) return false
        if (curNum != other.curNum) return false
        if (!members.contentEquals(other.members)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = objectId.hashCode()
        result = 31 * result + leader.hashCode()
        result = 31 * result + competitionName.hashCode()
        result = 31 * result + information.hashCode()
        result = 31 * result + deadline.hashCode()
        result = 31 * result + expectedNum
        result = 31 * result + curNum
        result = 31 * result + members.contentHashCode()
        return result
    }
}
