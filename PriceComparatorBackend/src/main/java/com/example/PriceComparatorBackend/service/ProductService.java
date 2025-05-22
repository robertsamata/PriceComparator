package com.example.PriceComparatorBackend.service;

import com.example.PriceComparatorBackend.model.Discount;
import com.example.PriceComparatorBackend.model.Product;
import com.example.PriceComparatorBackend.util.CsvReader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final CsvReader csvReader;
    private List<Product> products;
    private List<Discount> discounts;

    public ProductService(CsvReader csvReader) {
        this.csvReader = csvReader;
        this.products = csvReader.loadProducts("lidl_2025-05-08.csv");
        this.discounts = csvReader.loadDiscounts("lidl_discounts_2025-05-08.csv");
    }

    public List<Product> getTopDiscountedProducts() {
        System.out.println(this.products.get(0));
        return discounts.stream()
                .sorted(Comparator.comparingInt(Discount::getPercentage).reversed())
                .limit(5)
                .map(d -> products.stream()
                        .filter(p -> p.getId().equals(d.getProductId()))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Discount> getNewDiscounts() {
        LocalDate now = LocalDate.now();
        return discounts.stream()
                .filter(d -> d.getFromDate().isEqual(now) || d.getFromDate().isAfter(now.minusDays(1)))
                .collect(Collectors.toList());
    }

    // other features ...
}