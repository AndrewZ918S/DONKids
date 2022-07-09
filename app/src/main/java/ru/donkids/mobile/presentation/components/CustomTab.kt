package ru.donkids.mobile.presentation.components

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun openCustomTab(context: Context, url: String) {
    val chromePkg = "com.android.chrome"
    val tabsIntent = CustomTabsIntent.Builder().build()
    try {
        context.packageManager.getPackageInfo(
            chromePkg,
            PackageManager.GET_ACTIVITIES
        )
        tabsIntent.intent.setPackage(chromePkg)
    } catch (e: PackageManager.NameNotFoundException) {
        // Ask to choose browser
    }
    tabsIntent.launchUrl(context, Uri.parse(url))
}
