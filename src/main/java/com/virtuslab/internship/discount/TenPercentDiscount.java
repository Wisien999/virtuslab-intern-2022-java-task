package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public class TenPercentDiscount extends AbstractDiscount {

    public static String NAME = "TenPercentDiscount";
    public static double VALUE = 0.1;

    @Override
    protected boolean shouldApply(Receipt receipt) {
        return receipt.totalPrice()
                .compareTo(BigDecimal.valueOf(DiscountConfig.TEN_PERCENT_DISCOUNT_TOTAL_PRICE_REQUIRED)) >= 0
                ;

    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double getValue() {
        return VALUE;
    }
}
