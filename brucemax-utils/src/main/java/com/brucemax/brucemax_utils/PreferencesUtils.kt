package com.brucemax.brucemax_utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager

inline fun Fragment.editPrefs(
        commit: Boolean = false,
        action: SharedPreferences.Editor.() -> Unit
) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    val editor = preferences.edit()
    action(editor)
    if (commit) {
        editor.commit()
    } else {
        editor.apply()
    }
}

inline fun Context.editPrefs(
        commit: Boolean = false,
        action: SharedPreferences.Editor.() -> Unit
) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = preferences.edit()
    action(editor)
    if (commit) {
        editor.commit()
    } else {
        editor.apply()
    }
}

fun <T> Fragment.fromPrefs(action: SharedPreferences.() -> T) : T {
    val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    return action(preferences)
}

fun <T> Context.fromPrefs(action: SharedPreferences.() -> T) : T {
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    return action(preferences)
}