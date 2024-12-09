package com.example.notesappjetpackcompose.utils

import java.util.concurrent.TimeUnit

object NoteUtils {



    fun calculateDelay(addedTimeMillis: Long, currentTimeMillis: Long): Long {
        val timeDifference = currentTimeMillis - addedTimeMillis

        return when {
            timeDifference < TimeUnit.MINUTES.toMillis(1) -> 1000L // Update every second for moments ago
            timeDifference < TimeUnit.HOURS.toMillis(1) -> TimeUnit.MINUTES.toMillis(1) // Update every minute
            timeDifference < TimeUnit.DAYS.toMillis(1) -> TimeUnit.HOURS.toMillis(1) // Update every hour
            else -> TimeUnit.DAYS.toMillis(1) // Update every day
        }
    }

    fun getTimeAgo(addedTimeMillis: Long, currentTimeMillis: Long): String {
        val timeDifference = currentTimeMillis - addedTimeMillis

        return when {
            timeDifference < TimeUnit.MINUTES.toMillis(1) -> "a few moments ago"
            timeDifference < TimeUnit.HOURS.toMillis(1) -> {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference)
                "$minutes minute${if (minutes > 1) "s" else ""} ago"
            }
            timeDifference < TimeUnit.DAYS.toMillis(1) -> {
                val hours = TimeUnit.MILLISECONDS.toHours(timeDifference)
                "$hours hour${if (hours > 1) "s" else ""} ago"
            }
            timeDifference < TimeUnit.DAYS.toMillis(7) -> {
                val days = TimeUnit.MILLISECONDS.toDays(timeDifference)
                "$days day${if (days > 1) "s" else ""} ago"
            }
            timeDifference < TimeUnit.DAYS.toMillis(30) -> {
                val weeks = timeDifference / TimeUnit.DAYS.toMillis(7)
                "$weeks week${if (weeks > 1) "s" else ""} ago"
            }
            timeDifference < TimeUnit.DAYS.toMillis(365) -> {
                val months = timeDifference / TimeUnit.DAYS.toMillis(30)
                "$months month${if (months > 1) "s" else ""} ago"
            }
            else -> {
                val years = timeDifference / TimeUnit.DAYS.toMillis(365)
                "$years year${if (years > 1) "s" else ""} ago"
            }
        }
    }
}