package com.example.smartrecipe.core.extensions

fun String?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()

// Hàm dọn dẹp chuỗi JSON từ Gemini AI trước khi dùng Gson bóc tách
fun String.cleanJsonString(): String {
    return this.trim()
        .removePrefix("```json")
        .removePrefix("```")
        .removeSuffix("```")
        .trim()
}

fun String.Companion.empty() = ""

fun String.extractJson(): String {
    val startIndex = this.indexOf("{")
    val endIndex = this.lastIndexOf("}")
    if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
        return this.substring(startIndex, endIndex + 1)
    }
    return this
}
