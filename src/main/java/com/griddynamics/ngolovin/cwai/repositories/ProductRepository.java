package com.griddynamics.ngolovin.cwai.repositories;

import com.griddynamics.ngolovin.cwai.configs.IgniteConfig;
import com.griddynamics.ngolovin.cwai.entities.Product;
import org.apache.ignite.springdata20.repository.IgniteRepository;
import org.apache.ignite.springdata20.repository.config.RepositoryConfig;

@RepositoryConfig(cacheName = IgniteConfig.PRODUCT_CACHE_NAME)
public interface ProductRepository extends IgniteRepository<Product, String> {
}
