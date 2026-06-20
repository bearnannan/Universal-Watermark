package com.universalwatermark.util

import com.universalwatermark.data.FileNameFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object FileNameGenerator {

    fun generateFileName(
        format: FileNameFormat,
        date: Date,
        note: String,
        address: String,
        index: Int = 0
    ): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(date)
        val timestampUnderscore = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US).format(date)
        
        val safeNote = sanitize(note)
        val safeAddress = sanitize(address)
        val uniqueId = UUID.randomUUID().toString().replace("-", "")

        return when (format) {
            FileNameFormat.TIMESTAMP_PROJECT -> {
                "${timestamp}_${safeNote}"
            }
            FileNameFormat.TIMESTAMP_ADDRESS -> {
                "${timestamp}_${safeAddress}"
            }
            FileNameFormat.INDEX_TIMESTAMP -> {
                "Index_${index}_${timestamp}"
            }
            FileNameFormat.UNIQUE_ID -> {
                "${timestamp}_${uniqueId}"
            }
            FileNameFormat.BS_PROJECT_ADDRESS_TIMESTAMP -> {
                "${safeNote}_${safeAddress}_${timestamp}"
            }
            FileNameFormat.ADDRESS_TIMESTAMP -> {
                "${safeAddress}_${timestamp}"
            }
            FileNameFormat.BS_PROJECT_TIMESTAMP -> {
                "${safeNote}_${timestamp}"
            }
            FileNameFormat.TAG_BS_PROJECT_TIMESTAMP -> {
                "tag_${safeNote}_${timestamp}"
            }
            FileNameFormat.BS_PROJECT_TIMESTAMP_TAG -> {
               "${safeNote}_${timestamp}_tag"
            }
            FileNameFormat.TIMESTAMP_UNDERSCORE -> {
                timestampUnderscore
            }
        }
    }

    // Replace illegal chars with dashes or underscores
    private fun sanitize(input: String): String {
        if (input.isBlank()) return "Unknown"
        return input.trim()
            .replace(Regex("[^a-zA-Z0-9ก-๙\\-]"), "-") // Allow Thai, Alphanum, Dash
            .replace(Regex("-+"), "-") // Collapse multiple dashes
            .trim('-')
    }
}
