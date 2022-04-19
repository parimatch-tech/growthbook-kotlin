package com.sdk.growthbook.utils

import com.sdk.growthbook.model.GBExperiment
import com.sdk.growthbook.model.GBExperimentResult
import com.sdk.growthbook.model.GBFeature
import kotlinx.serialization.json.JsonElement

/**
 * Constants Class - GrowthBook
 */
internal class Constants {

    companion object {

        /**
         * ID Attribute Key
         */
        val idAttributeKey = "id"
    }
}

/**
 * Type Alias for Feature in GrowthBook
 */
internal typealias GBFeatures = HashMap<String, GBFeature>

typealias GBAttributes = Map<String, Any>

/**
 * Type Alias for Condition Element in GrowthBook Rules
 */
typealias GBCondition = JsonElement

/**
 * Triple Tuple for GrowthBook Namespaces
 * It has ID, StartRange & EndRange
 */
typealias GBNameSpace = Triple<String, Float, Float>

/**
 * Double Tuple for GrowthBook Ranges
 */
typealias GBBucketRange = Pair<Float, Float>

typealias GBTrackingCallback = (GBExperiment, GBExperimentResult) -> Unit
