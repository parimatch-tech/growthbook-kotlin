package com.sdk.growthbook.integration

import com.sdk.growthbook.local.GrowthBookLocalSDK
import com.sdk.growthbook.model.GBLocalContext
import com.sdk.growthbook.utils.GBTrackingCallback
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class VerifySDKCallTrackingCallback {

    @Test
    fun verifyTrackingCallbackNotTriggerLocalSDK() {
        val json = """
            {
              "status": 200,
              "features": {
                "feature_1": {
                  "defaultValue": true
                }
              }
            }
        """.trimMargin()

        var isTrigger = false

        val trackingCallback: GBTrackingCallback =
            { _, _ -> isTrigger = true }

        val sdkInstance = buildLocalSDK(json, trackingCallback)

        sdkInstance.feature("feature_1")

        assertFalse(isTrigger, "Tracking callback not invoke")
    }

    @Test
    fun verifyTrackingCallbackTriggerLocalSDK() {
        val json = """
            {
              "status": 200,
              "features": {
                "feature_1": {
                  "defaultValue": "value_default",
                  "rules": [
                    {
                      "variations": [
                        "value_1",
                        "value_2"
                      ],
                      "weights": [
                        0.5,
                        0.5
                      ],
                      "hashAttribute": "id"
                    }
                  ]
                }
              }
            }
        """.trimIndent()

        var isTrigger = false

        val trackingCallback: GBTrackingCallback =
            { _, _ -> isTrigger = true }

        val sdkInstance = buildLocalSDK(json, trackingCallback)

        sdkInstance.feature("feature_1")

        assertTrue(isTrigger, "Tracking callback invoke")
    }

    private fun buildLocalSDK(
        json: String,
        trackingCallback: GBTrackingCallback,
    ): GrowthBookLocalSDK {
        return GrowthBookLocalSDK(
            GBLocalContext(
                true,
                mapOf("id" to "some_mock_id"),
                mapOf(),
                false
            ),
            json,
            trackingCallback,
        )
    }
}
