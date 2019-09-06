package com.griddynamics.ngolovin.cwai.repositories;

import com.griddynamics.ngolovin.cwai.entities.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CassandraRepository<Product, String> {

    Optional<Product> findByUniqId(String uniqId);
}
