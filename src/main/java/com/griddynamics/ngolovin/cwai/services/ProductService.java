package com.griddynamics.ngolovin.cwai.services;

import com.griddynamics.ngolovin.cwai.entities.Product;
import com.griddynamics.ngolovin.cwai.exceptions.IllegalListPriceException;
import com.griddynamics.ngolovin.cwai.exceptions.ProductNotFoundException;
import com.griddynamics.ngolovin.cwai.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProductByUniqId(String uniqId) {
        return productRepository.findById(uniqId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with uniqId ['" + uniqId + "'] doesn't exist"));
    }

    @Transactional
    public Product updateProductByUniqId(String uniqId, Product newProduct) {
        String newListPrice = newProduct.getListPrice();
        try {
            if (new BigDecimal(newListPrice).compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("listPrice must be positive: " + newListPrice);
            }
        } catch (NumberFormatException e) {
            throw new IllegalListPriceException("Illegal listPrice: " + newListPrice);
        }

        return productRepository.findById(uniqId)
                .map(product -> {
                    product.setListPrice(newListPrice);
                    return productRepository.save(uniqId, product);
                })
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with uniqId ['" + uniqId + "'] doesn't exist"));
    }
}
