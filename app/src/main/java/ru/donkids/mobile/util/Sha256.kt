package ru.donkids.mobile.util

import java.security.MessageDigest

fun String.sha256() = MessageDigest
    .getInstance("SHA-256")
    .digest(toByteArray())
    .fold("") { str, it ->
        str + "%02x".format(it)
    }
