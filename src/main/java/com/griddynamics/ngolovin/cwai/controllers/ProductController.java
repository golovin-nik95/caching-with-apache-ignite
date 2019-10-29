package com.griddynamics.ngolovin.cwai.controllers;

import com.griddynamics.ngolovin.cwai.entities.Product;
import com.griddynamics.ngolovin.cwai.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{uniqId}")
    public Product getProductByUniqId(@PathVariable String uniqId) {
        log.info("getProductByUniqId uniqId={}", uniqId);

        return productService.getProductByUniqId(uniqId);
    }

    @PutMapping("/{uniqId}")
    public Product updateProductByUniqId(@PathVariable String uniqId, @RequestBody Product newProduct) {
        log.info("updateProductByUniqId uniqId={}", uniqId);

        return productService.updateProductByUniqId(uniqId, newProduct);
    }
}
