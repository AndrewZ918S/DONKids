package ru.donkids.mobile.data.remote

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class StringValue

internal class StringToIntAdapter {
    @ToJson
    fun toString(@StringValue value: Boolean): String {
        return if (value) "1" else "0"
    }

    @ToJson
    fun toString(@StringValue value: Number): String {
        return value.toString()
    }

    @StringValue
    @FromJson
    fun toBoolean(value: String): Boolean {
        return value == "1"
    }

    @StringValue
    @FromJson
    fun toDouble(value: String): Double {
        return value.toDouble()
    }

    @StringValue
    @FromJson
    fun toFloat(value: String): Float {
        return value.toFloat()
    }

    @StringValue
    @FromJson
    fun toLong(value: String): Long {
        return value.toLong()
    }

    @StringValue
    @FromJson
    fun toInt(value: String): Int {
        return value.toInt()
    }

    @StringValue
    @FromJson
    fun toShort(value: String): Short {
        return value.toShort()
    }
}
