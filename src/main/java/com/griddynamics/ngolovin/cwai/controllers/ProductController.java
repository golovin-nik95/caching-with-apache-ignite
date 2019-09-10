package com.griddynamics.ngolovin.cwai.controllers;

import com.griddynamics.ngolovin.cwai.entities.Product;
import com.griddynamics.ngolovin.cwai.exceptions.IllegalListPriceException;
import com.griddynamics.ngolovin.cwai.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.CacheNameResource;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    @CacheNameResource
    private final IgniteCache<String, Product> productCache;

    @GetMapping("/{uniqId}")
    public Product getProductByUniqId(@PathVariable String uniqId) {
        log.info("getProductByUniqId uniqId={}", uniqId);

        Product product = productCache.get(uniqId);
        if (product == null) {
            throw new ProductNotFoundException("Product with uniqId ['" + uniqId + "'] doesn't exist");
        }

        return product;
    }

    @PutMapping("/{uniqId}")
    public Product updateProductByUniqId(@PathVariable String uniqId, @RequestBody Product newProduct) {
        log.info("updateProductByUniqId uniqId={}", uniqId);

        String newListPrice = newProduct.getListPrice();
        try {
            if (new BigDecimal(newListPrice).compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("listPrice must be positive: " + newListPrice);
            }
        } catch (NumberFormatException e) {
            throw new IllegalListPriceException("Illegal listPrice: " + newListPrice);
        }

        Product storedProduct = productCache.get(uniqId);
        if (storedProduct == null) {
            throw new ProductNotFoundException("Product with uniqId ['" + uniqId + "'] doesn't exist");
        }
        storedProduct.setListPrice(newListPrice);
        productCache.put(uniqId, storedProduct);

        return storedProduct;
    }
}
