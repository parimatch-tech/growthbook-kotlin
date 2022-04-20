package com.sdk.growthbook.tests

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class GBTestHelper {

    companion object {

        val jsonParser = Json { ignoreUnknownKeys = true }
        private val testData = jsonParser.decodeFromString<JsonElement>(gbTestCases)

        fun getEvalConditionData(): JsonArray {
            return testData.jsonObject["evalCondition"] as JsonArray
        }

        fun getRunExperimentData(): JsonArray {
            return testData.jsonObject["run"] as JsonArray
        }

        fun getFNVHashData(): JsonArray {
            return testData.jsonObject["hash"] as JsonArray
        }

        fun getBucketRangeData(): JsonArray {
            return testData.jsonObject["getBucketRange"] as JsonArray
        }

        fun getInNameSpaceData(): JsonArray {
            return testData.jsonObject["inNamespace"] as JsonArray
        }

        fun getChooseVariationData(): JsonArray {
            return testData.jsonObject["chooseVariation"] as JsonArray
        }

        fun getEqualWeightsData(): JsonArray {
            return testData.jsonObject["getEqualWeights"] as JsonArray
        }
    }
}

@Serializable
class GBContextTest (
    val attributes: JsonElement = JsonObject(HashMap()),
    val qaMode: Boolean = false,
    val enabled: Boolean = true,
    val forcedVariations: HashMap<String, Int>? = null
)
