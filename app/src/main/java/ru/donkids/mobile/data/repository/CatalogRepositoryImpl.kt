package ru.donkids.mobile.data.repository

import ru.donkids.mobile.data.local.database.CatalogDatabase
import ru.donkids.mobile.data.mapper.toProduct
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.enums.Error
import ru.donkids.mobile.data.remote.enums.Status
import ru.donkids.mobile.data.remote.request.ProductRequest
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.use_case.HttpRequest
import ru.donkids.mobile.domain.use_case.localize.ServerError
import ru.donkids.mobile.domain.use_case.login.LoginAuto
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val api: DonKidsApi,
    private val db: CatalogDatabase,
    private val httpRequest: HttpRequest,
    private val serverError: ServerError,
    private val loginAuto: LoginAuto
) : CatalogRepository {
    override suspend fun getCategories() = httpRequest<List<Product>> {
        loginAuto().collect { result ->
            when (result) {
                is Resource.Success -> {
                    val user = result.data
                    val response = api.getProducts(
                        ProductRequest(
                            id = user.id,
                            misc = "G"
                        )
                    )
                    when (response.status) {
                        Status.OK -> {
                            response.products?.let { list ->
                                val products = list.map {
                                    it.toProduct()
                                }
                                emit(Resource.Success(products))
                            } ?: emit(serverError())
                        }
                        Status.ERROR -> {
                            emit(serverError(response.error))
                        }
                    }
                }
                is Resource.Error -> {
                    emit(serverError(Error.LoginNeed))
                }
                else -> Unit
            }
        }
    }

    override suspend fun getProducts() = httpRequest<List<Product>> { /* TODO */ }
}