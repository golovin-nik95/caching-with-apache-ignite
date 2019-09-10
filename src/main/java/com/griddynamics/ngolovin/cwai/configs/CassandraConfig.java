package com.griddynamics.ngolovin.cwai.configs;

import org.apache.ignite.cache.store.cassandra.datasource.DataSource;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraConfig {

    @Bean
    public DataSource cassandraDataSource(CassandraProperties cassandraProperties) {
        DataSource cassandraDataSource = new DataSource();
        String[] contactPoints = cassandraProperties.getContactPoints().toArray(new String[0]);
        cassandraDataSource.setContactPoints(contactPoints);
        cassandraDataSource.setPort(cassandraProperties.getPort());
        return cassandraDataSource;
    }
}
