package ru.donkids.mobile.data.mapper

import ru.donkids.mobile.data.remote.dto.UserDto
import ru.donkids.mobile.domain.model.User

fun UserDto.toUser(id: String): User {
    return User(
        id,
        username
    )
}
