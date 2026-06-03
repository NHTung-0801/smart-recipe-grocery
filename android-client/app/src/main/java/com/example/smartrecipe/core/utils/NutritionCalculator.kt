package com.example.smartrecipe.android_client.core.utils

object NutritionCalculator {

    fun calculateBMI(weightKg: Double, heightCm: Double): Double {
        if (heightCm <= 0) return 0.0
        val heightM = heightCm / 100
        return weightKg / (heightM * heightM)
    }

    fun calculateTDEE(weightKg: Double, heightCm: Double, age: Int, isMale: Boolean, activityMultiplier: Double): Double {
        val bmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age) + if (isMale) 5 else -161
        return bmr * activityMultiplier
    }

    // Tự động chia Macro dựa trên TDEE
    // Quy tắc: 1g Protein = 4 Calo, 1g Carbs = 4 Calo, 1g Fat = 9 Calo
    fun calculateStandardMacros(tdee: Int): Triple<Int, Int, Int> {
        val proteinGrams = (tdee * 0.30) / 4
        val carbsGrams = (tdee * 0.40) / 4
        val fatGrams = (tdee * 0.30) / 9
        return Triple(proteinGrams.toInt(), carbsGrams.toInt(), fatGrams.toInt())
    }

    fun calculateProgressPercentage(consumed: Int, goal: Int): Int {
        if (goal <= 0) return 0
        val percentage = (consumed.toDouble() / goal.toDouble()) * 100
        return percentage.toInt().coerceAtMost(100)
    }
}