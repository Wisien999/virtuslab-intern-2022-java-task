package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountApplierTest {
    @Test
    public void shouldNotApplyDiscounts() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese").get();
        var bread = productDb.getProduct("Bread").get();
        var banana = productDb.getProduct("Banana").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(banana, 1));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = cheese.price()
                .add(bread.price())
                .add(banana.price().multiply(BigDecimal.valueOf(1)));

        // When
        var receiptAfterDiscounts = DiscountApplier.applyDiscounts(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(0, receiptAfterDiscounts.discounts().size());
    }

    @Test
    public void shouldApplyOnly10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese").get();
        var bread = productDb.getProduct("Bread").get();
        var cereals = productDb.getProduct("Cereals").get();
        var banana = productDb.getProduct("Banana").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(banana, 10));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = cheese.price()
                .add(bread.price())
                .add(cereals.price())
                .add(banana.price().multiply(BigDecimal.valueOf(10)))
                .multiply(new BigDecimal("0.9"));

        // When
        var receiptAfterDiscounts = DiscountApplier.applyDiscounts(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(1, receiptAfterDiscounts.discounts().size());
    }

    @Test
    public void shouldApplyOnly15PercentGrainDiscount() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese").get();
        var bread = productDb.getProduct("Bread").get();
        var cereals = productDb.getProduct("Cereals").get();
        var banana = productDb.getProduct("Banana").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(banana, 1));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = cheese.price()
                .add(bread.price())
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .add(banana.price().multiply(BigDecimal.valueOf(1)))
                .multiply(new BigDecimal("0.85"));

        // When
        var receiptAfterDiscounts = DiscountApplier.applyDiscounts(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(1, receiptAfterDiscounts.discounts().size());
    }

    @Test
    public void shouldApply10PercentDiscountAnd15PercentGrainDiscount() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese").get();
        var bread = productDb.getProduct("Bread").get();
        var cereals = productDb.getProduct("Cereals").get();
        var banana = productDb.getProduct("Banana").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 3));
        receiptEntries.add(new ReceiptEntry(banana, 5));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = cheese.price()
                .add(bread.price().multiply(BigDecimal.valueOf(2)))
                .add(cereals.price().multiply(BigDecimal.valueOf(3)))
                .add(banana.price().multiply(BigDecimal.valueOf(5)))
                .multiply(new BigDecimal("0.85")).multiply(new BigDecimal("0.9"));

        // When
        var receiptAfterDiscounts = DiscountApplier.applyDiscounts(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(2, receiptAfterDiscounts.discounts().size());
    }
}
