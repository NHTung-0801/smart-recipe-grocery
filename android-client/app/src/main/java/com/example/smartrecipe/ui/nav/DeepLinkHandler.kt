package com.example.smartrecipe.ui.nav

import android.content.Intent
import android.net.Uri

object DeepLinkHandler {
    // Ví dụ URL chia sẻ: smartrecipe://recipe/105
    private const val RECIPE_SCHEME = "smartrecipe"
    private const val RECIPE_HOST = "recipe"

    /**
     * Bóc tách Recipe ID từ Intent khởi chạy
     * Trả về ID của công thức nếu format hợp lệ, ngược lại trả về null
     */
    fun parseRecipeIdFromIntent(intent: Intent?): Long? {
        val data: Uri? = intent?.data
        if (data != null && data.scheme == RECIPE_SCHEME && data.host == RECIPE_HOST) {
            val pathSegments = data.pathSegments
            // Lấy path segment đầu tiên (chính là ID: 105)
            if (pathSegments.isNotEmpty()) {
                return pathSegments[0].toLongOrNull()
            }
        }
        return null
    }
}