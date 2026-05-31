package com.example.smartrecipe.core.extensions

import android.view.View

// Ẩn/Hiện View nhanh gọn
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

// Extension function để chặn click đúp (Debounce Click)
fun View.setOnSingleClickListener(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceTime) {
                lastClickTime = currentTime
                action()
            }
        }
    })
}