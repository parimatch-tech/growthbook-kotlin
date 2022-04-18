package com.sdk.growthbook.integration

import com.sdk.growthbook.local.GrowthBookLocalSDK
import com.sdk.growthbook.model.GBLocalContext
import org.intellij.lang.annotations.Language
import org.junit.Test
import kotlin.test.assertEquals

internal class VerifySDKReturnFeatureValues {

    @Language("json")
    val json = """
            {
              "status": 200,
              "features": {
                "bool_feature_true": {
                  "defaultValue": true
                },
                "bool_feature_false": {
                  "defaultValue": false
                },
                "string_feature": {
                  "defaultValue": "Default value"
                },
                "number_feature": {
                  "defaultValue": 888
                },
                "number_feature_negative": {
                  "defaultValue": -1
                }
              }
            }
        """.trimMargin()

    @Test
    fun verifyLocalSDKReturnFeatureDefaultValue() {
        val sdk = GrowthBookLocalSDK(
            GBLocalContext(
                enabled = true,
                attributes = mapOf(),
                forcedVariations = emptyMap(),
                qaMode = false,
            ),
            json
        )

        assertEquals(true, sdk.feature("bool_feature_true")?.value)
        assertEquals(false, sdk.feature("bool_feature_false")?.value)
        assertEquals("Default value", sdk.feature("string_feature")?.value)

        assertEquals(888, sdk.feature("number_feature")?.value)
        assertEquals(-1, sdk.feature("number_feature_negative")?.value)

        //Check not exist feature
        assertEquals(null, sdk.feature("no_exist_feature"))
    }
}