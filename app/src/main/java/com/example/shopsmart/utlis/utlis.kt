package com.example.shopsmart.utlis

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri

@SuppressLint("QueryPermissionsNeeded")
fun sendMessageToWhatsApp(phoneNumber: String, message: String, context: android.content.Context) {
    val packageManager = context.packageManager
    val i = Intent(Intent.ACTION_VIEW)

    try {
        val url =
            "https://api.whatsapp.com/send?phone=$phoneNumber".toString() + "&text=" + message
        i.setPackage("com.whatsapp")
        i.setData(Uri.parse(url))
        if (i.resolveActivity(packageManager) == null) {
            context.startActivity(i)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}