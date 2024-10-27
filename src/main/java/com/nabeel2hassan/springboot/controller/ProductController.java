package com.nabeel2hassan.springboot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nabeel2hassan.springboot.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/print/products")
    public String printProducts() {
        return productService.printProducts();
    }

    @GetMapping("/product/print/max-price")
    public String printProductWithMaxPrice() {
        return productService.printProductWithMaxPrice();
    }
    

}
