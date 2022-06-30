package ru.donkids.mobile.domain.use_case

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import javax.inject.Inject

class OpenChromeUrl @Inject constructor() {
    private val chromePkg: String = "com.android.chrome"

    operator fun invoke(context: Context, url: String) {
        val tabsIntent = CustomTabsIntent.Builder().build()
        if (isChromeInstalled(context)) {
            tabsIntent.intent.setPackage(chromePkg)
        }
        tabsIntent.launchUrl(context, Uri.parse(url))
    }

    private fun isChromeInstalled(context: Context): Boolean {
        return try {
            context.packageManager.getPackageInfo(chromePkg, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}