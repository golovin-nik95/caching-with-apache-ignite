package com.griddynamics.ngolovin.cwai.configs;

import com.griddynamics.ngolovin.cwai.entities.Product;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.store.cassandra.CassandraCacheStoreFactory;
import org.apache.ignite.cache.store.cassandra.datasource.DataSource;
import org.apache.ignite.cache.store.cassandra.persistence.KeyValuePersistenceSettings;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.transactions.spring.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

@Configuration
public class IgniteConfig {

    public static final String PRODUCT_CACHE_NAME = "ProductCache";

    @Value("classpath:ignite/cassandra-persistence-settings.xml")
    private Resource cassandraPersistenceSettings;

    @Bean
    public IgniteConfiguration igniteConfiguration(DataSource cassandraDataSource) {
        CassandraCacheStoreFactory<String, Product> cassandraCacheStoreFactory = new CassandraCacheStoreFactory<>();
        cassandraCacheStoreFactory.setDataSource(cassandraDataSource);
        KeyValuePersistenceSettings persistenceSettings = new KeyValuePersistenceSettings(cassandraPersistenceSettings);
        cassandraCacheStoreFactory.setPersistenceSettings(persistenceSettings);

        CacheConfiguration<String, Product> productCacheConfiguration = new CacheConfiguration<>(PRODUCT_CACHE_NAME);
        productCacheConfiguration.setReadThrough(true);
        productCacheConfiguration.setWriteThrough(true);
        productCacheConfiguration.setStatisticsEnabled(true);
        productCacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        productCacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        productCacheConfiguration.setCacheStoreFactory(cassandraCacheStoreFactory);

        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setCacheConfiguration(productCacheConfiguration);
        igniteConfiguration.setIgniteInstanceName(PRODUCT_CACHE_NAME + "-instance");

        return igniteConfiguration;
    }

    @Bean
    public Ignite igniteInstance() {
        return Ignition.start(igniteConfiguration(null));
    }

    @Bean
    public SpringTransactionManager transactionManager() {
        SpringTransactionManager transactionManager = new SpringTransactionManager();
        transactionManager.setIgniteInstanceName(igniteInstance().name());

        return transactionManager;
    }
}
