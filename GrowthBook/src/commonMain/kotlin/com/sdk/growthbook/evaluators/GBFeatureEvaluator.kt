package com.sdk.growthbook.evaluators

import com.sdk.growthbook.model.GBExperiment
import com.sdk.growthbook.model.GBExperimentResult
import com.sdk.growthbook.model.GBFeature
import com.sdk.growthbook.model.GBFeatureResult
import com.sdk.growthbook.model.GBFeatureSource
import com.sdk.growthbook.model.GBLocalContext
import com.sdk.growthbook.utils.Constants
import com.sdk.growthbook.utils.GBTrackingCallback
import com.sdk.growthbook.utils.GBUtils
import com.sdk.growthbook.utils.toJsonElement
import kotlinx.serialization.json.JsonPrimitive

/**
 * Feature Evaluator Class
 * Takes Context and Feature Key
 * Returns Calculated Feature Result against that key
 */
internal class GBFeatureEvaluator {

    /**
     * Takes Context and Feature Key
     * Returns Calculated Feature Result against that key
     */
    fun evaluateFeature(
        feature: GBFeature,
        featureKey: String,
        context: GBLocalContext,
        trackingCallback: GBTrackingCallback,
    ): GBFeatureResult {
        // Loop through the feature rules (if any)
        val rules = feature.rules
        if (rules != null && rules.isNotEmpty()) {

            for (rule in rules) {

                // If the rule has a condition and it evaluates to false, skip this rule and continue to the next one

                if (rule.condition != null) {
                    val attr = context.attributes.toJsonElement()
                    if (!GBConditionEvaluator().evalCondition(attr, rule.condition)) {
                        continue
                    }
                }

                // If rule.force is set
                if (rule.force != null) {
                    // If rule.coverage is set
                    if (rule.coverage != null) {

                        val key = rule.hashAttribute ?: Constants.idAttributeKey
                        // Get the user hash value (context.attributes[rule.hashAttribute || "id"]) and if empty, skip the rule
                        val attributeValue = context.attributes.get(key) as? String ?: ""
                        if (attributeValue.isEmpty())
                            continue
                        else {
                            // Compute a hash using the Fowler–Noll–Vo algorithm (specifically fnv32-1a)
                            val hashFNV = GBUtils.hash(attributeValue + featureKey)
                            // If the hash is greater than rule.coverage, skip the rule
                            if (hashFNV != null && hashFNV > rule.coverage) {
                                continue
                            }
                        }
                    }

                    // Return (value = forced value, source = force)
                    return prepareResult(value = rule.force, source = GBFeatureSource.force)
                } else {
                    // Otherwise, convert the rule to an Experiment object
                    val exp = GBExperiment(
                        rule.key ?: featureKey,
                        variations = rule.variations ?: ArrayList(),
                        coverage = rule.coverage,
                        weights = rule.weights,
                        hashAttribute = rule.hashAttribute,
                        namespace = rule.namespace
                    )

                    // Run the experiment.
                    val result =
                        GBExperimentEvaluator().evaluateExperiment(context, exp, trackingCallback)
                    if (result.inExperiment) {
                        return prepareResult(
                            value = result.value,
                            source = GBFeatureSource.experiment,
                            experiment = exp,
                            experimentResult = result
                        )
                    } else {
                        // If result.inExperiment is false, skip this rule and continue to the next one.
                        continue
                    }
                }
            }
        }

        // Return (value = defaultValue or null, source = defaultValue)
        return prepareResult(
            value = feature.defaultValue,
            source = GBFeatureSource.defaultValue
        )
    }

    /**
     * This is a helper method to create a FeatureResult object.
     * Besides the passed-in arguments, there are two derived values - on and off, which are just the value cast to booleans.
     */
    private fun prepareResult(
        value: Any?,
        source: GBFeatureSource,
        experiment: GBExperiment? = null,
        experimentResult: GBExperimentResult? = null
    ): GBFeatureResult {
        return GBFeatureResult(
            value = convert(value),
            source = source,
            experiment = experiment,
            experimentResult = experimentResult
        )
    }

    private fun convert(value: Any?): String? {
        val safeValue = value ?: return null
        return (safeValue as? JsonPrimitive)?.content ?: safeValue.toString()
    }
}
