package com.org.samplecode.options

import com.org.samplecode.Price
import com.org.samplecode.PricingResult
import com.org.samplecode.Pricer
import com.org.samplecode.Strategy
import java.lang.RuntimeException
import java.math.BigDecimal

data class ValidateResult(val isValid: Boolean, val error: String? = null)
interface OptionStrategyValidator {
    fun validate(optionStrategy: OptionStrategy): ValidateResult
}

interface PricingEngine {
    fun price(optionStrategy: OptionStrategy): Price
}

class OptionPricer(private val validator: OptionStrategyValidator,
                   private val pricingEngine: PricingEngine): Pricer {

    override fun price(strategy: Strategy): PricingResult {
        val optionStrategy = strategy as? OptionStrategy ?: throw RuntimeException("Can only price options")
        val validateResult = validator.validate(optionStrategy)
        return if(!validateResult.isValid) {
            PricingResult(false, validateResult.error ?: "Failed validation")
        } else {
            val price = pricingEngine.price(optionStrategy)
            PricingResult(true, null, price)
        }
    }
}