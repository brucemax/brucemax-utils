package com.brucemax.brucemax_utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.preference.PreferenceManager
import java.util.*

/**
 * Manages setting of the app's locale.
 */
object LocaleHelper {

    const val PRF_LANGUAGE = "prefLanguage"

    fun onAttach(context: Context?): Context {
        val locale = getPersistedLocale(context)
        return setLocale(context!!, locale)
    }

    fun getPersistedLocale(context: Context?): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(PRF_LANGUAGE, "system") ?: "system"
    }

    /**
     * Set the app's locale to the one specified by the given String.
     *
     * @param context
     * @param localeSpec a locale specification as used for Android resources (NOTE: does not
     * support country and variant codes so far); the special string "system" sets
     * the locale to the locale specified in system settings
     * @return
     */
    fun setLocale(context: Context, localeSpec: String?): Context {
        val locale: Locale = if (localeSpec == "system") {
            getSystemLocale()
        } else {
            val splitLang = localeSpec?.split("_")
            if (splitLang?.size?.compareTo(2) == 0) {
                Locale(splitLang[0], splitLang[1])
            } else Locale(localeSpec)
        }
        Locale.setDefault(locale)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, locale)
        } else {
            updateResourcesLegacy(context, locale)
        }
    }

    fun getSystemLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            Resources.getSystem().configuration.locale
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, locale: Locale): Context {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}