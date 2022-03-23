package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class DiscountApplier {
    private static final List<Class<? extends AbstractDiscount>> discountApplicationOrder = Arrays.asList(FifteenPercentGrainDiscount.class, TenPercentDiscount.class);

    public static Receipt applyDiscounts(Receipt receipt) {
        for (var discountClass : discountApplicationOrder) {
            try {
                var discount = discountClass.getConstructor().newInstance();
                receipt = discount.apply(receipt);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return receipt;
    }
}
