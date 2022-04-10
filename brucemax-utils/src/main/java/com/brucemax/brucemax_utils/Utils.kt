package com.brucemax.brucemax_utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}
fun Context.showToast(textRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textRes, duration).show()
}

fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}
fun Fragment.showToast(textRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, textRes, duration).show()
}

fun Activity.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Activity.showToast(textRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textRes, duration).show()
}

fun backgroundToast(context: Context, msg: String, duration: Int = Toast.LENGTH_LONG) = Handler(Looper.getMainLooper()).post { Toast.makeText(context, msg, duration).show() }
fun backgroundToast(context: Context, msgRes: Int, duration: Int = Toast.LENGTH_LONG) = Handler(Looper.getMainLooper()).post { Toast.makeText(context, msgRes, duration).show() }

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.spToPx(): Int = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun Fragment.hideKeyboard() {
    val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity?.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun hideKeyboard(activity: Activity?) {
    val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

