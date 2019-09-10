package com.griddynamics.ngolovin.cwai.configs;

import com.griddynamics.ngolovin.cwai.entities.Product;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.store.cassandra.CassandraCacheStoreFactory;
import org.apache.ignite.cache.store.cassandra.datasource.DataSource;
import org.apache.ignite.cache.store.cassandra.persistence.KeyValuePersistenceSettings;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

@Configuration
public class IgniteConfig {

    private static final String PRODUCT_CACHE_NAME = "ProductCache";

    @Value("classpath:ignite/cassandra-persistence-settings.xml")
    private Resource cassandraPersistenceSettings;

    @Bean
    public Ignite ignite(DataSource cassandraDataSource) {
        CassandraCacheStoreFactory<String, Product> cassandraCacheStoreFactory = new CassandraCacheStoreFactory<>();
        cassandraCacheStoreFactory.setDataSource(cassandraDataSource);
        KeyValuePersistenceSettings persistenceSettings = new KeyValuePersistenceSettings(cassandraPersistenceSettings);
        cassandraCacheStoreFactory.setPersistenceSettings(persistenceSettings);

        CacheConfiguration<String, Product> productCacheConfig = new CacheConfiguration<>(PRODUCT_CACHE_NAME);
        productCacheConfig.setReadThrough(true);
        productCacheConfig.setWriteThrough(true);
        productCacheConfig.setStatisticsEnabled(true);
        productCacheConfig.setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        productCacheConfig.setCacheStoreFactory(cassandraCacheStoreFactory);

        IgniteConfiguration igniteConfig = new IgniteConfiguration();
        igniteConfig.setCacheConfiguration(productCacheConfig);

        return Ignition.getOrStart(igniteConfig);
    }

    @Bean
    public IgniteCache<String, Product> productCache(Ignite ignite) {
        return ignite.cache(PRODUCT_CACHE_NAME);
    }
}
