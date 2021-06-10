package com.org.samplecode

import java.math.BigDecimal


data class Underlying(val id: String,)
interface StrategyLeg

interface Strategy {
    val legs: List<StrategyLeg>
}
interface Price
data class PricingResult(
    val success: Boolean,
    val error: String? = null,
    val price: Price? = null) {

    constructor(price: Price): this(true, null, price)
}

