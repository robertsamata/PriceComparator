package com.example.PriceComparatorBackend.controller;

import com.example.PriceComparatorBackend.model.Discount;
import com.example.PriceComparatorBackend.model.Product;
import com.example.PriceComparatorBackend.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/discounts/top")
    public List<Product> topDiscounts() {
        return service.getTopDiscountedProducts();
    }

    @GetMapping("/discounts/new")
    public List<Discount> newDiscounts() {
        return service.getNewDiscounts();
    }

    // other endpoints
}