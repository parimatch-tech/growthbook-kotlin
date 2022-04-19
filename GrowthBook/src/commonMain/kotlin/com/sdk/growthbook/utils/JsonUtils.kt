package com.sdk.growthbook.utils

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.longOrNull

fun convertToPrimitiveIfPossible(jsonElement: Any): Any {
    return if (jsonElement is JsonPrimitive) {
        jsonElement.intOrNull
            ?: jsonElement.longOrNull
            ?: jsonElement.doubleOrNull
            ?: jsonElement.floatOrNull
            ?: jsonElement.booleanOrNull
            ?: jsonElement.contentOrNull
            ?: jsonElement
    } else {
        jsonElement
    }
}