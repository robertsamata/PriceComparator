package com.example.PriceComparatorBackend.service;

import com.example.PriceComparatorBackend.model.BasketItem;
import com.example.PriceComparatorBackend.model.Discount;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasketService {

    // Metoda care face split-ul coșului în două liste
    public Map<String, List<BasketItem>> splitBasketByDiscount(List<BasketItem> basket, List<Discount> discounts, LocalDate date) {
        List<BasketItem> discountedItems = new ArrayList<>();
        List<BasketItem> nonDiscountedItems = new ArrayList<>();

        for (BasketItem item : basket) {
            boolean hasDiscount = discounts.stream()
                    .anyMatch(d -> d.getProductId().equals(item.getProduct().getId())
                            && !date.isBefore(d.getFromDate())
                            && !date.isAfter(d.getToDate()));

            if (hasDiscount) {
                discountedItems.add(item);
            } else {
                nonDiscountedItems.add(item);
            }
        }

        Map<String, List<BasketItem>> result = new HashMap<>();
        result.put("discounted", discountedItems);
        result.put("nonDiscounted", nonDiscountedItems);

        return result;
    }

    // Exemplu simulare încărcare basket - în realitate încarci din DB sau sesiune
    public List<BasketItem> loadUserBasket() {
        // TODO: Încarcă lista produselor din coșul utilizatorului
        return List.of(); // momentan gol
    }

    // Exemplu simulare încărcare discounturi active
    public List<Discount> loadCurrentDiscounts() {
        // TODO: Încarcă discounturile active valabile
        return List.of(); // momentan gol
    }
}
