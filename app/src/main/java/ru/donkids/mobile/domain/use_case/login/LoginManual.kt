package ru.donkids.mobile.domain.use_case.login

import ru.donkids.mobile.data.remote.enums.Status
import ru.donkids.mobile.domain.model.User
import ru.donkids.mobile.domain.repository.LoginRepository
import ru.donkids.mobile.domain.use_case.HttpRequest
import ru.donkids.mobile.domain.use_case.localize.ServerError
import ru.donkids.mobile.domain.use_case.user.SetUser
import ru.donkids.mobile.util.Resource
import ru.donkids.mobile.util.sha256
import javax.inject.Inject

class LoginManual @Inject constructor(
    private val httpRequest: HttpRequest,
    private val repository: LoginRepository,
    private val serverError: ServerError,
    private val setUser: SetUser
) {
    operator fun invoke(username: String, password: String) = httpRequest<User> {
        val serverKey = repository.getApiKey()
        val hash = (password + username).sha256()
        val userKey = (serverKey + hash).sha256()
        val userId = serverKey.sha256()
        val response = repository.loginUser(userKey, userId)

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
