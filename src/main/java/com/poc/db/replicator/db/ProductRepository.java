package com.poc.db.replicator.db;

import org.springframework.data.repository.Repository;

import java.util.List;


public interface ProductRepository extends Repository<Product, Long> {
    List<Product> findAll();
}
