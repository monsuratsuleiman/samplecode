package com.org.samplecode.options

import com.org.samplecode.*
import java.math.BigDecimal
import java.time.LocalDate


data class OptionStrategy(override val legs: List<OptionLeg>): Strategy

enum class OptionType {
    Call,
    Put
}
enum class ExerciseType {
    Call,
    Put
}

data class OptionLeg(val underlying: Underlying,
                     val optionType: OptionType,
                     val strikePrice: BigDecimal,
                     val expiryDate: LocalDate
): StrategyLeg