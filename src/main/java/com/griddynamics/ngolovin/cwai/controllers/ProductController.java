package com.griddynamics.ngolovin.cwai.controllers;

import com.griddynamics.ngolovin.cwai.entities.Product;
import com.griddynamics.ngolovin.cwai.exceptions.IllegalListPriceException;
import com.griddynamics.ngolovin.cwai.exceptions.ProductNotFoundException;
import com.griddynamics.ngolovin.cwai.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/{uniqId}")
    public Product getProductByUniqId(@PathVariable String uniqId) {
        log.info("getProductByUniqId uniqId={}", uniqId);

        return productRepository.findByUniqId(uniqId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with uniqId ['" + uniqId + "'] doesn't exist"));
    }

    @PutMapping("/{uniqId}")
    public Product updateProductByUniqId(@PathVariable String uniqId, @RequestBody Product product) {
        log.info("updateProductByUniqId uniqId={}", uniqId);

        String newListPrice = product.getListPrice();
        try {
            if (new BigDecimal(newListPrice).compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("listPrice must be positive: " + newListPrice);
            }
        } catch (NumberFormatException e) {
            throw new IllegalListPriceException("Illegal listPrice: " + newListPrice);
        }

        return productRepository.findByUniqId(uniqId)
                .map(existed -> {
                    existed.setListPrice(newListPrice);
                    return productRepository.save(existed);
                })
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with uniqId ['" + uniqId + "'] doesn't exist"));
    }
}
