package dev.illwiz.tada.data.repository.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.illwiz.tada.domain.user.User

@Entity
data class UserDB(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,
    var username:String,
    var password:String,
    var avatar:String
) {

    constructor(user: User) : this(user.id,user.username,user.password,user.avatar)

    fun toDomain():User {
        return User(
            id, username, password, avatar
        )
    }
}