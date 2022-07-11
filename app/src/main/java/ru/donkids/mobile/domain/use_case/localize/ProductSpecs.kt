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
                    "размер" -> stringResource(R.string.size)
                    "материал" -> stringResource(R.string.material)
                    "комплектация" -> stringResource(R.string.includes)
                    "возраст" -> stringResource(R.string.age)
                    else -> key
                }

                val localValue = when {
                    value.contains("см") -> {
                        value.replace(
                            regex = Regex("\\s*см"),
                            replacement = " ${stringResource(R.string.unit_cm)}"
                        )
                    }
                    value.startsWith("от") -> {
                        stringResource(
                            R.string.for_age,
                            value.replace(Regex("[^\\d]+"), "")
                        )
                    }
                    else -> value
                }

                map[localKey] = localValue
            }
        }

        return map
    }
}