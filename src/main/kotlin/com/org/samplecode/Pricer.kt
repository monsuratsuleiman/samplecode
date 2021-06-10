package com.org.samplecode

interface Pricer {
    fun price(strategy: Strategy): PricingResult
}