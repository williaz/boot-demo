package com.williaz.bootdemo.repository;

import com.williaz.bootdemo.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product findById(Long id);
    List<Product> findByType(String type);
    int add(Product product);
    int update(Product product);
    int delete(Long id);
    void createTable();
    void dropTable();
}
