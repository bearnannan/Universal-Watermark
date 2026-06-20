package com.universalwatermark.engine.template

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemplateEngine @Inject constructor() {
    
    fun resolve(template: String, metadata: Map<String, String>): String {
        var result = template
        metadata.forEach { (placeholder, value) ->
            // Filter out empty lines if the value is empty and the placeholder is on its own line
            if (value.isBlank()) {
                val regexWithNewline = Regex("(?m)^\\s*\\[$placeholder\\]\\s*\\r?\\n")
                result = result.replace(regexWithNewline, "")
            }
            result = result.replace("[$placeholder]", value)
        }
        return result.trim()
    }
}
