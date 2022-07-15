package ru.donkids.mobile.data.repository

import ru.donkids.mobile.R
import ru.donkids.mobile.data.local.database.CatalogDatabase
import ru.donkids.mobile.data.mapper.toProduct
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.enums.Status
import ru.donkids.mobile.data.remote.request.ProductRequest
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.use_case.HttpRequest
import ru.donkids.mobile.domain.use_case.localize.ServerError
import ru.donkids.mobile.domain.use_case.localize.StringResource
import ru.donkids.mobile.domain.use_case.login.LoginAuto
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val api: DonKidsApi,
    private val db: CatalogDatabase,
    private val httpRequest: HttpRequest,
    private val serverError: ServerError,
    private val stringResource: StringResource,
    private val loginAuto: LoginAuto
) : CatalogRepository {
    override suspend fun updateCatalog() = httpRequest {
        val dbList = db.getCatalog().sortedBy { product ->
            product.id
        }

        loginAuto().collect { result ->
            when (result) {
                is Resource.Success -> {
                    val user = result.data
                    val response = api.getProducts(
                        ProductRequest(user.id)
                    )
                    when (response.status) {
                        Status.OK -> {
                            response.products?.let { list ->
                                val catalog = list.map { productDto ->
                                    productDto.toProduct()
                                }.sortedBy { product ->
                                    product.id
                                }
                                if (dbList != catalog) {
                                    emit(Resource.Success(Unit))
                                    db.updateCatalog(catalog)
                                }
                            } ?: emit(serverError())
                        }
                        Status.ERROR -> {
                            emit(serverError(response.error))
                        }
                    }
                }
                is Resource.Error -> {
                    emit(Resource.Error(result.message, result.isCritical))
                }
                else -> Unit
            }
        }
    }

    override suspend fun getCategories() = httpRequest {
        val dbList = db.getCategories()
        if (dbList.isNotEmpty()) {
            emit(Resource.Success(dbList))
            emit(Resource.Loading(false))
        }

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
                                val categories = list.map {
                                    it.toProduct()
                                }
                                if (dbList != categories) {
                                    emit(Resource.Success(categories))
                                    db.updateCategories(categories)
                                }
                            } ?: emit(serverError())
                        }
                        Status.ERROR -> {
                            emit(serverError(response.error))
                        }
                    }
                }
                is Resource.Error -> {
                    emit(Resource.Error(result.message, result.isCritical))
                }
                else -> Unit
            }
        }
    }

    override suspend fun getProductById(id: Int, update: Boolean) = httpRequest {
        val dbEntity = db.getProductWithId(id)
        if (dbEntity != null) {
            emit(Resource.Success(dbEntity))
            emit(Resource.Loading(false))
        }

        if (!update) {
            return@httpRequest
        }

        loginAuto().collect { result ->
            when (result) {
                is Resource.Success -> {
                    val user = result.data
                    val response = api.getProducts(
                        ProductRequest(
                            id = user.id,
                            ids = id.toString()
                        )
                    )
                    when (response.status) {
                        Status.OK -> {
                            response.products?.let { list ->
                                val products = list.map {
                                    it.toProduct()
                                }
                                if (products.isNotEmpty()) {
                                    val product = products[0]

                                    if (dbEntity != product) {
                                        emit(Resource.Success(product))
                                        db.updateProduct(product)
                                    }
                                }
                            } ?: emit(serverError())
                        }
                        Status.ERROR -> {
                            emit(serverError(response.error))
                        }
                    }
                }
                is Resource.Error -> {
                    emit(Resource.Error(result.message, result.isCritical))
                }
                else -> Unit
            }
        }
    }

    override suspend fun getProductByCode(code: String, update: Boolean) = httpRequest {
        val dbEntity = db.getProductWithCode(code)
        if (dbEntity == null) {
            emit(Resource.Error(stringResource(R.string.product_unavailable)))
            return@httpRequest
        }

        emit(Resource.Success(dbEntity))
        emit(Resource.Loading(false))

        if (!update) {
            return@httpRequest
        }

        loginAuto().collect { result ->
            when (result) {
                is Resource.Success -> {
                    val user = result.data
                    val response = api.getProducts(
                        ProductRequest(
                            id = user.id,
                            ids = dbEntity.id.toString()
                        )
                    )
                    when (response.status) {
                        Status.OK -> {
                            response.products?.let { list ->
                                val products = list.map {
                                    it.toProduct()
                                }
                                if (products.isNotEmpty()) {
                                    val product = products[0]
                                    if (dbEntity != product) {
                                        emit(Resource.Success(product))
                                        db.updateProduct(product)
                                    }
                                }
                            } ?: emit(serverError())
                        }
                        Status.ERROR -> {
                            emit(serverError(response.error))
                        }
                    }
                }
                is Resource.Error -> {
                    emit(Resource.Error(result.message, result.isCritical))
                }
                else -> Unit
            }
        }
    }
}
