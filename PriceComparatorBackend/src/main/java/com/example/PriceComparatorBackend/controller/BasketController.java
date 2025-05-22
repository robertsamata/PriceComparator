package com.example.PriceComparatorBackend.controller;

import com.example.PriceComparatorBackend.dto.BasketResponse;
import com.example.PriceComparatorBackend.model.BasketItem;
import com.example.PriceComparatorBackend.model.Discount;
import com.example.PriceComparatorBackend.service.BasketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/split")
    public BasketResponse splitBasket(@RequestParam(required = false) String shoppingDate) {
        LocalDate date = shoppingDate != null ? LocalDate.parse(shoppingDate) : LocalDate.now();

        List<BasketItem> basket = basketService.loadUserBasket();
        List<Discount> discounts = basketService.loadCurrentDiscounts();

        Map<String, List<BasketItem>> split = basketService.splitBasketByDiscount(basket, discounts, date);
        return new BasketResponse(split.get("discounted"), split.get("nonDiscounted"));
    }
}
