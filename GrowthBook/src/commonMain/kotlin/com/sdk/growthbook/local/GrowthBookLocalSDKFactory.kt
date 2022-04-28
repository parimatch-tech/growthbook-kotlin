package com.sdk.growthbook.local

import com.sdk.growthbook.model.GBFeatureResult
import com.sdk.growthbook.model.GBLocalContext
import com.sdk.growthbook.utils.GBTrackingCallback
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object GrowthBookLocalSDKProvider {

    private var growthBookLocalSDK: GrowthBookLocalSDK? = null

    fun init(
        localContext: GBLocalContext,
        growthBookFeatures: String,
        trackingCallback: GBTrackingCallback = { _, _ -> }
    ) {
        growthBookLocalSDK = GrowthBookLocalSDK(localContext, growthBookFeatures, trackingCallback)
    }

    fun feature(featureKey: String): GBFeatureResult? {
        return growthBookLocalSDK?.feature(featureKey)
    }
}
