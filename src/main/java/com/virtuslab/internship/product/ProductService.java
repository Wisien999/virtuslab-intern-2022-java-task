package com.virtuslab.internship.product;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    ProductDb productDb = new ProductDb();

    public Optional<Product> getProductByName(String productName) {
        return productDb.getProduct(productName);
    }
}
