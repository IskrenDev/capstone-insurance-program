package com.github.iskrendev.insuranceprogram.common;

import java.math.BigDecimal;

public interface Insurance {
    BigDecimal calculateInsuranceAmount(int duration, BigDecimal rate);
}
