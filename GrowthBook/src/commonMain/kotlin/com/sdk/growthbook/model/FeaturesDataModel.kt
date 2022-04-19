package com.sdk.growthbook.model

import com.sdk.growthbook.utils.GBFeatures
import kotlinx.serialization.Serializable

/**
 * Data Object for Feature API Response
 */
@Serializable
internal data class FeaturesDataModel(
    val features : GBFeatures
)