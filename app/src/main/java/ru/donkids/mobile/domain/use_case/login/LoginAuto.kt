package ru.donkids.mobile.domain.use_case.login

import kotlinx.coroutines.flow.Flow
import ru.donkids.mobile.data.mapper.toUser
import ru.donkids.mobile.domain.model.User
import ru.donkids.mobile.domain.repository.LoginRepository
import ru.donkids.mobile.domain.use_case.HttpRequest
import ru.donkids.mobile.domain.use_case.user.GetUser
import ru.donkids.mobile.domain.use_case.user.SetUser
import ru.donkids.mobile.util.Resource
import ru.donkids.mobile.util.sha256
import javax.inject.Inject

class LoginAuto @Inject constructor(
    private val httpRequest: HttpRequest,
    private val repository: LoginRepository,
    private val loginError: LoginError,
    private val getUser: GetUser,
    private val setUser: SetUser
) {
    operator fun invoke(): Flow<Resource<User>> = httpRequest {
        val serverKey = repository.getApiKey()
        val userKey = (serverKey + getUser()?.id).sha256()
        val userId = serverKey.sha256()
        val userDto = repository.updateUser(userKey, userId)
        val user = userDto.toUser(userId)
        when (userDto.status) {
            "ok" -> {
                emit(Resource.Success(user))
                setUser(user)
            }
            "error" -> {
                emit(Resource.Error(loginError(userDto.errorType)))
            }
        }
    }
}
