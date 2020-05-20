package com.ominext.cms.web;

import com.ominext.cms.response.CartResponse;
import com.ominext.cms.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/redirect-card")
    public ModelAndView addToCart(@RequestParam Long productId, @RequestParam Long userId) {
        service.addToCart(productId, userId);
        CartResponse response = service.cartInfo(userId);
        response.setProductId(productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cart", response);
        modelAndView.setViewName("/cart/detail-cart");
        return modelAndView;
    }

    @GetMapping("/delete-item")
    public ModelAndView deleteItem(@RequestParam Long productId, @RequestParam Long userId) {
        System.out.println(productId + userId);
        service.deleteCartItem(userId, productId);
        CartResponse response = service.cartInfo(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cart", response);
        modelAndView.setViewName("/cart/detail-cart");
        return modelAndView;
    }
}
