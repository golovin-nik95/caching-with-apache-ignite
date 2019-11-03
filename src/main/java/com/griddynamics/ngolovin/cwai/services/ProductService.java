package com.griddynamics.ngolovin.cwai.services;

import com.griddynamics.ngolovin.cwai.entities.Product;
import com.griddynamics.ngolovin.cwai.exceptions.IllegalListPriceException;
import com.griddynamics.ngolovin.cwai.exceptions.ProductNotFoundException;
import com.griddynamics.ngolovin.cwai.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.resources.CacheNameResource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {

    @CacheNameResource
    private final IgniteCache<String, Product> productCache;
    private final ProductRepository productRepository;

    public Product getProductByUniqId(String uniqId) {
        return productRepository.findById(uniqId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with uniqId ['" + uniqId + "'] doesn't exist"));
    }

    public Product updateProductByUniqId(String uniqId, Product newProduct) {
        String newListPrice = newProduct.getListPrice();
        try {
            if (new BigDecimal(newListPrice).compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("listPrice must be positive: " + newListPrice);
            }
        } catch (NumberFormatException e) {
            throw new IllegalListPriceException("Illegal listPrice: " + newListPrice);
        }

        return productCache.invoke(uniqId, (CacheEntryProcessor<String, Product, Product>) (entry, arguments) -> {
            if (!entry.exists()) {
                throw new ProductNotFoundException("Product with uniqId ['" + uniqId + "'] doesn't exist");
            }

            Product product = entry.getValue();
            product.setListPrice(newListPrice);
            entry.setValue(product);

            return product;
        });
    }
}
