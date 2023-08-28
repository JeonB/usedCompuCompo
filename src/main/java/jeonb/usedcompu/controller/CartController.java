package jeonb.usedcompu.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class CartController {

    @GetMapping("/cart")
    public String getCart() {

        return "cart/cart";
    }

}
