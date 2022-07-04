package ru.donkids.mobile.data.repository

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import ru.donkids.mobile.data.local.database.CarouselDatabase
import ru.donkids.mobile.data.mapper.toBanner
import ru.donkids.mobile.data.mapper.toBannerEntity
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.domain.model.Banner
import ru.donkids.mobile.domain.repository.HomeRepository
import ru.donkids.mobile.domain.use_case.HttpRequest
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val db: CarouselDatabase,
    private val httpRequest: HttpRequest
) : HomeRepository {
    override suspend fun getBanners() = httpRequest<List<Banner>> {
        val entities = db.dao.getBanners()
        if (entities.isNotEmpty()) {
            emit(Resource.Success(
                entities.map { it.toBanner() }
            ))
        }

        val main: Document = Jsoup.connect(DonKidsApi.SITE_URL).get()

        val carousel = main.select("div[class=karusel-pole]")[0]
        val items = carousel.select("div[class=karusel-item]")

        val banners = ArrayList<Banner>()
        for (i in 0 until items.size) {
            val item = items[i]

            var image = item.select("img")[0].attr("src")
            var page = item.select("a")[0].attr("href")

            image = "/" + image.removePrefix(DonKidsApi.SITE_URL)
            page = "/" + page.removePrefix(DonKidsApi.SITE_URL)

            var vendorCode: String? = null
            if (page.startsWith("/index")) {
                val product = Jsoup.connect(DonKidsApi.SITE_URL + page).get()

                vendorCode = product.select("div[class=prod-info-block thin]")[0].html()
                vendorCode = vendorCode.replaceFirst("<h2.+h2>", "")
            }

            banners.add(
                Banner(
                    pageLink = DonKidsApi.SITE_URL + page,
                    imageLink = DonKidsApi.SITE_URL + image,
                    vendorCode = vendorCode
                )
            )
        }

        emit(Resource.Success(banners))

        db.dao.clearBanners()
        db.dao.insertBanners(
            banners.map { it.toBannerEntity() }
        )
    }
}
