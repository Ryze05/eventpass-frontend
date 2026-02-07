package com.example.eventpassfront.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatEventDate(timestamp: String): String {
    return try {
        val dateTime = LocalDateTime.parse(timestamp)

        val formatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy", Locale.getDefault())

        dateTime.format(formatter).replaceFirstChar { it.uppercase() }
    } catch (e: Exception) {
        timestamp
    }
}

@Composable
fun getDrawableId(imagenRes: String): Int {
    val context = androidx.compose.ui.platform.LocalContext.current

    val name = imagenRes.substringBefore(".")

    val id = context.resources.getIdentifier(name, "drawable", context.packageName)

    return if (id != 0) id else com.example.eventpassfront.R.drawable.concierto
}