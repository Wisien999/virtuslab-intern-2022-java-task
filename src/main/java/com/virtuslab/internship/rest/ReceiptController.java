package com.virtuslab.internship.rest;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    ReceiptService receiptService;

    @GetMapping
    public ResponseEntity<Receipt> getReceiptFromBasket(@RequestBody Basket basket) {
        return ResponseEntity.of(receiptService.getReceiptFromBasket(basket));
    }
}
