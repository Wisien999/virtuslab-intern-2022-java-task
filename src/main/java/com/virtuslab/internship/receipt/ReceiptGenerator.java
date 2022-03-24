package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptGenerator {
    ProductService productService = new ProductService();

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        Map<Product, Integer> counter = new HashMap<>();

        for (var product : basket.getProducts()) {
            var dbProduct = productService.getProductByName(product.name());
            dbProduct.ifPresent(value -> counter.merge(value, 1, Integer::sum));
        }

        counter.forEach((product, count) ->
                receiptEntries.add(new ReceiptEntry(product, count, product.price().multiply(BigDecimal.valueOf(count))))
        );
        return new Receipt(receiptEntries);
    }
}
