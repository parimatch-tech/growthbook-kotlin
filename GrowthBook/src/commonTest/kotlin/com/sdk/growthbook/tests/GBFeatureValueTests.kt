package com.sdk.growthbook.tests

import com.sdk.growthbook.evaluators.GBFeatureEvaluator
import com.sdk.growthbook.model.GBFeature
import com.sdk.growthbook.model.GBFeatureResult
import com.sdk.growthbook.model.GBFeatureSource
import com.sdk.growthbook.model.GBLocalContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class GBFeatureValueTests {

    @Test
    fun verifyResultValueNullFeatureNull() {
        val featureJson = """{}""".trimMargin()

        assertEquals(null, getFeatureResult(featureJson).value)
    }

    @Test
    fun verifyResultValueNullFeatureHasEmptyRule() {
        val featureJson = """{
            "rules": [
                  {}
              ]
            }
            """.trimMargin()

        assertEquals(null, getFeatureResult(featureJson).value)
    }

    @Test
    fun verifyUsesDefaultValueNumber() {
        val featureJson = """{
          "defaultValue": 1
        }""".trimMargin()

        val result = getFeatureResult(featureJson)
        assertEquals(1, result.value)
        assertEquals(GBFeatureSource.defaultValue, result.source)
    }

    @Test
    fun verifyUsesCustomValuesString() {
        val featureJson = """{
            "defaultValue": "yes"
        }""".trimMargin()

        val result = getFeatureResult(featureJson)
        assertEquals("yes", result.value)
        assertEquals(GBFeatureSource.defaultValue, result.source)
    }

    @Test
    fun verifyForceRule() {
        val featureJson = """{
            "defaultValue": 2,
            "rules": [
              {
                "force": 1
              }
            ]
          }
        """.trimIndent()

        val result = getFeatureResult(featureJson)
        assertEquals(1, result.value)
        assertEquals(GBFeatureSource.force, result.source)
    }

    @Test
    fun verifyForceRuleCoverageIncluded() {
        val featureJson = """{
            "defaultValue": 2,
            "rules": [
              {
                "force": 1,
                "coverage": 0.5
              }
            ]
          }""".trimMargin()

        val attributes = mapOf<String, Any>(
            "id" to "3"
        )

        val result = getFeatureResult(featureJson, attributes)
        assertEquals(1, result.value)
        assertEquals(GBFeatureSource.force, result.source)
    }

    @Test
    fun verifyForceRuleCoverageExcluded() {
        val featureJson = """{
            "defaultValue": 2,
            "rules": [
              {
                "force": 1,
                "coverage": 0.5
              }
            ]
          }""".trimMargin()

        val attributes = mapOf<String, Any>(
            "id" to "1"
        )

        val result = getFeatureResult(featureJson, attributes)
        assertEquals(2, result.value)
        assertEquals(GBFeatureSource.defaultValue, result.source)
    }

    @Test
    fun verifyForceRuleCoverageMissingAttribute() {
        val featureJson = """{
            "defaultValue": 2,
            "rules": [
              {
                "force": 1,
                "coverage": 0.5
              }
            ]
          }""".trimMargin()

        val result = getFeatureResult(featureJson, mapOf())
        assertEquals(2, result.value)
        assertEquals(GBFeatureSource.defaultValue, result.source)
    }

    @Test
    fun verifyForceRuleConditionPass() {
        val featureJson = """{
            "defaultValue": 2,
            "rules": [
              {
                "force": 1,
                "condition": {
                  "country": {
                    "${'$'}in": [
                      "US",
                      "CA"
                    ]
                  },
                  "browser": "firefox"
                }
              }
            ]
          }""".trimMargin()

        val attributes = mapOf<String, Any>(
            "country" to "US",
            "browser" to "firefox",
        )

        val result = getFeatureResult(featureJson, attributes)
        assertEquals(1, result.value)
        assertEquals(GBFeatureSource.force, result.source)
    }

    @Test
    fun verifyForceRuleConditionFail() {
        val featureJson = """{
            "defaultValue": 2,
            "rules": [
              {
                "force": 1,
                "condition": {
                  "country": {
                    "${'$'}in": [
                      "US",
                      "CA"
                    ]
                  },
                  "browser": "firefox"
                }
              }
            ]
          }""".trimMargin()

        val attributes = mapOf<String, Any>(
            "country" to "US",
            "browser" to "chrome",
        )

        val result = getFeatureResult(featureJson, attributes)
        assertEquals(2, result.value)
        assertEquals(GBFeatureSource.defaultValue, result.source)
    }

    @Test
    fun verifyEmptyExperimentRuleC() {
        val featureJson = """{
            "rules": [
              {
                "variations": [
                  "a",
                  "b",
                  "c"
                ]
              }
            ]
        }""".trimIndent()

        val attributes = mapOf<String, Any>(
            "id" to "123"
        )

        val result = getFeatureResult(featureJson, attributes, "feature")
        assertEquals("c", result.value)
        assertEquals("c", result.experimentResult?.value)

        assertEquals(GBFeatureSource.experiment, result.source)
        assertEquals(2, result.experimentResult?.variationId)
        assertEquals("id", result.experimentResult?.hashAttribute)
        assertEquals("123", result.experimentResult?.hashValue)
    }

    @Test
    fun verifyEmptyExperimentRuleB() {
        val featureJson = """{
            "rules": [
              {
                "variations": [
                  "a",
                  "b",
                  "c"
                ]
              }
            ]
        }""".trimIndent()

        val attributes = mapOf<String, Any>(
            "id" to "fds"
        )

        val result = getFeatureResult(featureJson, attributes, "feature")
        assertEquals("b", result.value)
        assertEquals("b", result.experimentResult?.value)

        assertEquals(GBFeatureSource.experiment, result.source)
        assertEquals(1, result.experimentResult?.variationId)
        assertEquals("id", result.experimentResult?.hashAttribute)
        assertEquals("fds", result.experimentResult?.hashValue)
    }

    @Test
    fun verifyEmptyExperimentRuleA() {
        val featureJson = """{
            "rules": [
              {
                "variations": [
                  "a",
                  "b",
                  "c"
                ]
              }
            ]
        }""".trimIndent()

        val attributes = mapOf<String, Any>(
            "id" to "456"
        )

        val result = getFeatureResult(featureJson, attributes, "feature")
        assertEquals("a", result.value)
        assertEquals("a", result.experimentResult?.value)

        assertEquals(GBFeatureSource.experiment, result.source)
        assertEquals(0, result.experimentResult?.variationId)
        assertEquals("id", result.experimentResult?.hashAttribute)
        assertEquals("456", result.experimentResult?.hashValue)
    }

    @Test
    fun createsExperimentsProperly() {
        val featureJson = """{
            "rules": [
              {
                "coverage": 0.99,
                "hashAttribute": "anonId",
                "namespace": [
                  "pricing",
                  0,
                  1
                ],
                "key": "hello",
                "variations": [
                  true,
                  false
                ],
                "weights": [
                  0.1,
                  0.9
                ],
                "condition": {
                  "premium": true
                }
              }
            ]
          }
        """.trimMargin()

        val attributes = mapOf<String, Any>(
            "anonId" to "123",
            "premium" to true
        )

        val result = getFeatureResult(featureJson, attributes)

        assertEquals(false, result.value)
        assertEquals(false, result.experimentResult?.value)
        assertEquals("hello", result.experiment?.key)

        assertEquals(GBFeatureSource.experiment, result.source)
    }

    @Test
    fun verifyRuleOrdersSkipFirst() {
        val featureJson = """{
            "defaultValue": 0,
            "rules": [
              {
                "force": 1,
                "condition": {
                  "browser": "chrome"
                }
              },
              {
                "force": 2,
                "condition": {
                  "browser": "firefox"
                }
              },
              {
                "force": 3,
                "condition": {
                  "browser": "safari"
                }
              }
            ]
        }
        """.trimMargin()

        val attributes = mapOf<String, Any>(
            "browser" to "firefox"
        )

        val result = getFeatureResult(featureJson, attributes)

        assertEquals(2, result.value)
        assertEquals(GBFeatureSource.force, result.source)
    }

    @Test
    fun verifyRuleOrdersSkipTwoRules() {
        val featureJson = """{
            "defaultValue": 0,
            "rules": [
              {
                "force": 1,
                "condition": {
                  "browser": "chrome"
                }
              },
              {
                "force": 2,
                "condition": {
                  "browser": "firefox"
                }
              },
              {
                "force": 3,
                "condition": {
                  "browser": "safari"
                }
              }
            ]
        }
        """.trimMargin()

        val attributes = mapOf<String, Any>(
            "browser" to "safari"
        )

        val result = getFeatureResult(featureJson, attributes)

        assertEquals(3, result.value)
        assertEquals(GBFeatureSource.force, result.source)
    }

    @Test
    fun verifyRuleOrdersSkipAll() {
        val featureJson = """{
            "defaultValue": 0,
            "rules": [
              {
                "force": 1,
                "condition": {
                  "browser": "chrome"
                }
              },
              {
                "force": 2,
                "condition": {
                  "browser": "firefox"
                }
              },
              {
                "force": 3,
                "condition": {
                  "browser": "safari"
                }
              }
            ]
          }
        """.trimMargin()

        val attributes = mapOf<String, Any>(
            "browser" to "no_browser"
        )

        val result = getFeatureResult(featureJson, attributes)

        assertEquals(0, result.value)
        assertEquals(GBFeatureSource.defaultValue, result.source)
    }

    @Test
    fun skipExperimentBecauseCovereSmall() {
        val featureJson = """{
            "defaultValue": 0,
            "rules": [
              {
                "variations": [
                  0,
                  1,
                  2,
                  3
                ],
                "coverage": 0.01
              },
              {
                "force": 3
              }
            ]
          }
        """.trimMargin()

        val attributes = mapOf<String, Any>(
            "id" to "123"
        )

        val result = getFeatureResult(featureJson, attributes)
        assertEquals(3, result.value)
        assertEquals(GBFeatureSource.force, result.source)
    }

    @Test
    fun skipsExperimentOnNamespace() {
        val featureJson = """{
            "defaultValue": 0,
            "rules": [
              {
                "variations": [
                  0,
                  1,
                  2,
                  3
                ],
                "namespace": [
                  "pricing",
                  0,
                  0.01
                ]
              },
              {
                "force": 4
              }
            ]
          }
        """.trimMargin()

        val attributes = mapOf<String, Any>(
            "id" to "123"
        )

        val result = getFeatureResult(featureJson, attributes, "feature")

        assertEquals(4, result.value)
        assertEquals(GBFeatureSource.force, result.source)
    }

    @Test
    fun skipExperimentMissingHashAttribute() {
        val featureJson = """{
            "defaultValue": 0,
            "rules": [
              {
                "variations": [
                  0,
                  1,
                  2,
                  3
                ],
                "hashAttribute": "company"
              },
              {
                "force": 4
              }
            ]
          }
        """.trimMargin()

        val attributes = mapOf<String, Any>(
            "id" to "123"
        )

        val result = getFeatureResult(featureJson, attributes, "feature")

        assertEquals(4, result.value)
        assertEquals(GBFeatureSource.force, result.source)
    }

    private fun getFeatureResult(
        featureJson: String,
        attributes: Map<String, Any> = mapOf(),
        key: String = "key"
    ): GBFeatureResult {
        val evaluator = GBFeatureEvaluator()
        val feature: GBFeature = Json.decodeFromString(featureJson)

        return evaluator.evaluateFeature(
            feature,
            key,
            GBLocalContext(true, attributes, mapOf(), false)
        ) { _, _ -> }
    }
}