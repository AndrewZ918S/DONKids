package ru.donkids.mobile.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import ru.donkids.mobile.data.local.database.HomeDatabase
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.domain.model.Banner
import ru.donkids.mobile.domain.model.Recent
import ru.donkids.mobile.domain.repository.HomeRepository
import ru.donkids.mobile.domain.use_case.HttpRequest
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val db: HomeDatabase,
    private val httpRequest: HttpRequest
) : HomeRepository {
    override suspend fun getBanners() = httpRequest {
        val dbList = db.getBanners()
        if (dbList.isNotEmpty()) {
            emit(Resource.Success(dbList))
            emit(Resource.Loading(false))
        }

        val main: Document = Jsoup.connect(DonKidsApi.SITE_URL).get()

        val carousel = main.select("div[class=karusel-pole]")[0]
        val items = carousel.select("div[class=karusel-item]")

        val banners = ArrayList<Banner>()
        for (i in 0 until items.size) {
            val item = items[i]

            var imagePath = item.select("img")[0].attr("src")
            var pagePath = item.select("a")[0].attr("href")

            imagePath = "/" + imagePath.replace(Regex("${DonKidsApi.SITE_URL}/*"), "")
            pagePath = "/" + pagePath.replace(Regex("${DonKidsApi.SITE_URL}/*"), "")

            var productCode: String? = null
            if (pagePath.startsWith("/index")) {
                val product = Jsoup.connect(DonKidsApi.SITE_URL + pagePath).get()

                productCode = product.select("div[class=prod-info-block thin]")[0].html()
                productCode = productCode.replace(Regex("<h2.+h2>"), "")
            }

            banners.add(
                Banner(
                    pagePath = pagePath,
                    imagePath = imagePath,
                    productCode = productCode
                )
            )
        }

        if (dbList != banners) {
            emit(Resource.Success(banners))
            db.updateBanners(banners)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getHistory() = httpRequest {
        val dbList = db.getHistory()
        if (dbList.isNotEmpty()) {
            emit(Resource.Success(dbList))
        }
    }

    override suspend fun updateHistory(recent: Recent) {
        db.updateHistory(recent)
    }
}
