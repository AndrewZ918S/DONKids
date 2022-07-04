package ru.donkids.mobile.data.remote.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ru.donkids.mobile.data.remote.annotations.StringValue

internal class StringAdapter {
    @FromJson
    @StringValue
    fun strToBool(str: String): Boolean {
        return str == "1"
    }

    @ToJson
    fun boolToStr(@StringValue bool: Boolean): String {
        return if (bool) "1" else "0"
    }

    @FromJson
    @StringValue
    fun toDouble(value: String): Double {
        return value.toDouble()
    }

    @ToJson
    fun doubleToStr(@StringValue double: Double): String {
        return double.toString()
    }

    @FromJson
    @StringValue
    fun strToFloat(value: String): Float? {
        return value.toFloat()
    }

    @ToJson
    fun floatToStr(@StringValue float: Float?): String {
        return float.toString()
    }

    @FromJson
    @StringValue
    fun toLong(value: String): Long {
        return value.toLong()
    }

    @ToJson
    fun longToStr(@StringValue long: Long): String {
        return long.toString()
    }

    @FromJson
    @StringValue
    fun toInt(value: String): Int {
        return value.toInt()
    }

    @ToJson
    fun intToStr(@StringValue int: Int): String {
        return int.toString()
    }

    @FromJson
    @StringValue
    fun toShort(value: String): Short {
        return value.toShort()
    }

    @ToJson
    fun shortToStr(@StringValue short: Short): String {
        return short.toString()
    }
}
