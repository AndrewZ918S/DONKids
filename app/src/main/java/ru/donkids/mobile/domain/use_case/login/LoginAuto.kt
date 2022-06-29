package ru.donkids.mobile.domain.use_case.login

import kotlinx.coroutines.flow.Flow
import ru.donkids.mobile.data.mapper.toUser
import ru.donkids.mobile.domain.model.User
import ru.donkids.mobile.domain.repository.LoginRepository
import ru.donkids.mobile.domain.use_case.user.GetUser
import ru.donkids.mobile.domain.use_case.user.SetUser
import ru.donkids.mobile.util.Resource
import ru.donkids.mobile.util.http
import ru.donkids.mobile.util.sha256
import javax.inject.Inject

class LoginAuto @Inject constructor(
    private val repository: LoginRepository,
    private val getUser: GetUser,
    private val setUser: SetUser
) {
    operator fun invoke(): Flow<Resource<User>> = http {
        val serverKey = repository.getApiKey().key
        val userKey = (serverKey + getUser()?.id).sha256()
        val userId = serverKey.sha256()
        val userDto = repository.signIn(userKey, userId)
        when (userDto.status) {
            "ok" ->  {
                val user = userDto.toUser(userId, System.currentTimeMillis())
                emit(Resource.Success(user))
                setUser(user)
            }
            "error" -> {
                emit(Resource.Error(userDto.errorText))
            }
        }
    }
}
