package com.example.smartrecipe.ui.nav

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

// Chuyển trang an toàn bằng NavDirections (truyền theo Safe Args)
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.let {
        navigate(direction)
    }
}

// Chuyển trang an toàn bằng ID (Action ID hoặc Destination ID)
fun NavController.safeNavigate(@IdRes resId: Int) {
    currentDestination?.getAction(resId)?.let {
        navigate(resId)
    }
}