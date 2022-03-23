package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

public class FifteenPercentGrainDiscount extends AbstractDiscount {
    public static String NAME = "FifteenPercentGrainDiscount";
    public static double VALUE = 0.15;

    @Override
    protected boolean shouldApply(Receipt receipt) {
        return receipt.entries().stream()
                .filter(entry -> entry.product().type() == Product.Type.GRAINS)
                .mapToInt(ReceiptEntry::quantity)
                .sum() >= DiscountConfig.FIFTEEN_PERCENT_DISCOUNT_GRAIN_PRODUCTS_REQUIRED
                &&
                receipt.discounts().stream().noneMatch(discountName -> discountName.equals(TenPercentDiscount.NAME))
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
