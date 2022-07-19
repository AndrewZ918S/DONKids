package ru.donkids.mobile.domain.use_case.localize

import ru.donkids.mobile.R
import javax.inject.Inject

class ProductSpecs @Inject constructor(
    private val stringResource: StringResource
) {
    operator fun invoke(specs: String): Map<String, String> {
        val map = LinkedHashMap<String, String>()

        specs.split("\n").forEach { field ->
            val key = field
                .replaceFirst(Regex("([^:]+):.+"), "$1")
                .lowercase()
                .trim()

            val value = field
                .replaceFirst(Regex("[^:]+:(.+)"), "$1")
                .lowercase()
                .trim()
                .removeSuffix(".")

            if (value != "0" && value != "см") {
                val localKey = when (key) {
                    "возраст" -> stringResource(R.string.age)
                    "комплектация" -> stringResource(R.string.includes)
                    "материал" -> stringResource(R.string.material)
                    "размер" -> stringResource(R.string.size)
                    "упаковка" -> stringResource(R.string.packing)
                    "размер упаковки" -> stringResource(R.string.packing_size)
                    "производитель" -> stringResource(R.string.manufacturer)
                    else -> return@forEach
                }

                map[localKey] = value
            }
        }

        return map
    }
}