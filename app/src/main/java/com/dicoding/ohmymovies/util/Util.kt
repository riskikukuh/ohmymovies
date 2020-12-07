package com.dicoding.ohmymovies.util

import android.content.Context
import android.widget.Toast
import java.io.IOException

object Util {

    fun readFromFile(context : Context, filename : String) : String? {
        val result = StringBuilder()
        try {
            val stream = context.resources.assets.open(filename)
            val len = stream.available()
            val buffer = ByteArray(len)
            stream.read(buffer)
            result.append(String(buffer))
        } catch (e: Exception) {
            throw IOException("")
        }
        return result.toString()
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}