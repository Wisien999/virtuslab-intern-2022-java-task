package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public abstract class AbstractDiscount {
    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(getValue())));
            var discounts = receipt.discounts();
            discounts.add(getName());
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    protected abstract boolean shouldApply(Receipt receipt);
    public abstract String getName();
    public abstract double getValue();
}
