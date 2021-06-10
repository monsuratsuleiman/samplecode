package com.org.samplecode.options

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.org.samplecode.Price
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class OptionPricerTest {
    @Test
    fun `should throw when not option strategy`(){
        org.junit.jupiter.api.assertThrows<RuntimeException> {
            val mockValidator: OptionStrategyValidator = mock()
            OptionPricer(mockValidator, mock()).price(mock())
        }
    }

    @Test
    fun `should return failed pricing result when validation fails`() {
        val mockValidator: OptionStrategyValidator = mock()
        val optionStrategy: OptionStrategy = mock()

        whenever(mockValidator.validate(optionStrategy)).thenReturn(ValidateResult(false, "I'm invalid"))
        val mockPricingEngine = mock<PricingEngine>()
        val result = OptionPricer(mockValidator, mockPricingEngine).price(optionStrategy)

        assertFalse(result.success)
        assertEquals(result.error, "I'm invalid")
        verify(mockPricingEngine, times(0)).price(optionStrategy)
    }

    @Test
    fun `should return pricing engine result when valid strategy`() {
        val mockValidator: OptionStrategyValidator = mock()
        val optionStrategy: OptionStrategy = mock()
        val mockPricingEngine = mock<PricingEngine>()
        val mockPrice = mock<Price>()

        whenever(mockPricingEngine.price(optionStrategy)).thenReturn(mockPrice)
        whenever(mockValidator.validate(optionStrategy)).thenReturn(ValidateResult(true))
        val result = OptionPricer(mockValidator, mockPricingEngine).price(optionStrategy)

        assertTrue(result.success)
        assertNull(result.error)
        assertEquals(mockPrice, result.price)
    }

}