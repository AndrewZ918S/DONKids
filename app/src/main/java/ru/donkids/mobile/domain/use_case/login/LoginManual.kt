package ru.donkids.mobile.domain.use_case.login

import kotlinx.coroutines.flow.Flow
import ru.donkids.mobile.data.mapper.toUser
import ru.donkids.mobile.domain.model.User
import ru.donkids.mobile.domain.repository.LoginRepository
import ru.donkids.mobile.domain.use_case.user.SetUser
import ru.donkids.mobile.util.Resource
import ru.donkids.mobile.util.http
import ru.donkids.mobile.util.sha256
import javax.inject.Inject

class LoginManual @Inject constructor(
    private val repository: LoginRepository,
    private val setUser: SetUser
) {
    operator fun invoke(username: String, password: String): Flow<Resource<User>> = http {
        val serverKey = repository.getApiKey().key
        val hash = (password + username).sha256()
        val userKey = (serverKey + hash).sha256()
        val userId = serverKey.sha256()
        val userDto = repository.signUp(userKey, userId)
        when (userDto.status) {
            "ok" -> {
                val user = userDto.toUser(userId, System.currentTimeMillis())
                emit(Resource.Success(user))
                setUser(user)
            }
            "error" -> {
                emit(Resource.Error(userDto.errorType))
            }
        }
    }
}
