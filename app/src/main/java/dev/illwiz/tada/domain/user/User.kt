package dev.illwiz.tada.domain.user

data class User(
    var id:Long = 0L,
    var username:String,
    var password:String,
    var avatar:String
) {
}