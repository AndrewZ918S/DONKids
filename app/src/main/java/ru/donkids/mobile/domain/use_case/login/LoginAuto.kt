package ru.donkids.mobile.domain.use_case.login

import kotlinx.coroutines.flow.Flow
import ru.donkids.mobile.data.remote.enums.Status
import ru.donkids.mobile.domain.model.User
import ru.donkids.mobile.domain.repository.LoginRepository
import ru.donkids.mobile.domain.use_case.HttpRequest
import ru.donkids.mobile.domain.use_case.localize.ServerError
import ru.donkids.mobile.domain.use_case.user.GetUser
import ru.donkids.mobile.domain.use_case.user.SetUser
import ru.donkids.mobile.util.Resource
import ru.donkids.mobile.util.sha256
import javax.inject.Inject

class LoginAuto @Inject constructor(
    private val httpRequest: HttpRequest,
    private val repository: LoginRepository,
    private val serverError: ServerError,
    private val getUser: GetUser,
    private val setUser: SetUser
) {
    operator fun invoke(): Flow<Resource<User>> = httpRequest {
        val user = getUser()

        if (user != null && repository.checkUser(user)) {
            emit(Resource.Success(user))
            return@httpRequest
        }

        val serverKey = repository.getApiKey()
        val userKey = (serverKey + user?.id).sha256()
        val userId = serverKey.sha256()
        val response = repository.updateUser(userKey, userId)

        when (response.status) {
            Status.OK -> {
                response.username?.let { name ->
                    val update = User(
                        id = userId,
                        name = name
                    )
                    emit(Resource.Success(update))
                    setUser(update)
                } ?: emit(serverError())
            }
            Status.ERROR -> {
                emit(serverError(response.error))
                setUser(null)
            }
        }
    }
}
