package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.DiscountApplier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceiptService {
    public Optional<Receipt> getReceiptFromBasket(Basket basket) {
        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(basket);

        var receiptWithDiscounts = DiscountApplier.applyDiscounts(receipt);

        return Optional.of(receiptWithDiscounts);
    }
}
