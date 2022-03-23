package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        Map<Product, Integer> counter = new HashMap<>();

        for (var product : basket.getProducts()) {
            counter.merge(product, 1, Integer::sum);
        }

        counter.forEach((product, count) ->
                receiptEntries.add(new ReceiptEntry(product, count, product.price().multiply(BigDecimal.valueOf(count))))
        );
        return new Receipt(receiptEntries);
    }
}
