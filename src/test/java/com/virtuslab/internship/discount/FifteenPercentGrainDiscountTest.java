package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenPercentGrainDiscountTest {

    @Test
    void shouldApply15PercentGrainDiscountWhenAtLeast3GrainProducts() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var bread = productDb.getProduct("Bread");
        var careals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(careals, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentGrainDiscount();
        var expectedTotalPrice = cheese.price().add(bread.price()).add(bread.price())
                .add(careals.price()).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply15PercentGrainDiscountWhenLessThan3GrainProducts() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var bread = productDb.getProduct("Bread");
        var careals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(careals, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentGrainDiscount();
        var expectedTotalPrice = cheese.price().add(bread.price()).add(careals.price());

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply15PercentGrainDiscountWhen10PercentDiscountAppliedBefore() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(bread, 5));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var discount1 = new TenPercentDiscount();
        var discount2 = new FifteenPercentGrainDiscount();
        var expectedTotalPrice = cheese.price().add(bread.price().multiply(BigDecimal.valueOf(5))).add(cereals.price())
                .multiply(new BigDecimal("0.9"));

        // When
        var receiptAfterDiscount = discount2.apply(discount1.apply(receipt));

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
}
