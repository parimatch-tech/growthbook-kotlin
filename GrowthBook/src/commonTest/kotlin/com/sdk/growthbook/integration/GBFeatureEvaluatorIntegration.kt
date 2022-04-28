package com.sdk.growthbook.integration

import com.sdk.growthbook.evaluators.GBFeatureEvaluator
import com.sdk.growthbook.model.GBFeatureResult
import com.sdk.growthbook.model.GBLocalContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GBFeatureEvaluatorIntegration {

    @Test
    fun verifyDefaultValue() {
        val json = """
                {
                  "defaultValue": true
                }
                """.trimMargin()

        assertEquals(true, buildFeature(json, mapOf()).value)
    }

    @Test
    fun verifyValueByForceAttributeFirstCondition() {
        val json = """
                {
                  "defaultValue": "Default value",
                  "rules": [
                    {
                      "condition": {
                        "country": "IN"
                      },
                      "force": "Default value for india"
                    },
                    {
                      "condition": {
                        "brand": "KZ"
                      },
                      "force": "Default value for KZ brand"
                    }
                  ]
                }
                """.trimMargin()

        assertEquals(
            "Default value for india", buildFeature(
                json,
                mapOf(
                    "country" to "IN"
                )
            ).value
        )
    }

    @Test
    fun verifyValueByForceAttributeSecondCondition() {
        val json = """
                {
                  "defaultValue": "Default value",
                  "rules": [
                    {
                      "condition": {
                        "country": "IN"
                      },
                      "force": "Default value for country:IN"
                    },
                    {
                      "condition": {
                        "brand": "KZ"
                      },
                      "force": "Default value for brand:KZ"
                    }
                  ]
                }
                """.trimMargin()

        assertEquals(
            "Default value for brand:KZ", buildFeature(
                json,
                mapOf(
                    "brand" to "KZ"
                )
            ).value
        )
    }

    @Test
    fun verifyValueByForceAttributeTwoCondition() {
        val json = """
                {
                  "defaultValue": "Default value",
                  "rules": [
                    {
                      "condition": {
                        "country": "IN"
                      },
                      "force": "Default value for india"
                    },
                    {
                      "condition": {
                        "brand": "KZ"
                      },
                      "force": "Default value for KZ brand"
                    }
                  ]
                }
                """.trimMargin()

        assertEquals(
            "Default value for india", buildFeature(
                json,
                mapOf(
                    "country" to "IN",
                    "brand" to "KZ",
                )
            ).value
        )
    }

    private fun buildFeature(
        json: String,
        attributes: Map<String, Any>,
    ): GBFeatureResult {
        val context = GBLocalContext(
            enabled = true,
            attributes = attributes,
            forcedVariations = mapOf(),
            qaMode = false,
        )
        return GBFeatureEvaluator().evaluateFeature(
            Json.decodeFromString(json),
            "key",
            context,
            { _, _ -> }
        )
    }
}