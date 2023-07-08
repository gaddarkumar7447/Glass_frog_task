package com.example.glasstask.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


fun Context.showToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.hideKeyboard() {
    val inputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun AlertDialog.Builder.createWithCustomLayout(
    context: Context,
    layoutResId: Int,
    positiveButtonCallback: () -> Unit
): AlertDialog {
    val builder = AlertDialog.Builder(context)
    builder.setCancelable(false)

    val dialogLayout = LayoutInflater.from(context).inflate(layoutResId, null)
    builder.setView(dialogLayout)

    builder.setPositiveButton("OK") { _, _ -> positiveButtonCallback.invoke() }
    builder.setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.dismiss() }

    return builder.create()
}
