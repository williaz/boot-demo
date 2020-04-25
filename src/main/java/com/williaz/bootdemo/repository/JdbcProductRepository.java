package com.williaz.bootdemo.repository;

import com.williaz.bootdemo.domain.Product;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcOperations jdbcOperations;

    public JdbcProductRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    private RowMapper<Product> mapper = (resultSet, i) -> {
        Product p = new Product();
        p.setId(resultSet.getLong("id"));
        p.setName(resultSet.getString("name"));
        p.setType(resultSet.getString("type"));
        return p;
    };

    @Override
    public Product findById(Long id) {
        try {
            return jdbcOperations.queryForObject("select * from product where id = ?",
                    mapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Product> findByType(String type) {
        return jdbcOperations.query("select * from product where type = ?",
                mapper, type);
    }

    @Override
    public int add(Product product) {
        return jdbcOperations.update("insert into product (id, name, type) values (?, ?, ?)",
                product.getId(), product.getName(), product.getType());
    }

    @Override
    public int update(Product product) {
        return jdbcOperations.update("update product set name = ?, type = ? where id = ?",
                product.getName(), product.getType(), product.getId());
    }

    @Override
    public void createTable() {
        jdbcOperations.execute("create table product (id number primary key , name varchar2(30), type varchar2(30))");
    }

    @Override
    public void dropTable() {
        jdbcOperations.execute("drop table product");
    }

    @Override
    public int delete(Long id) {
        return jdbcOperations.update("delete from product where id = ?", id);
    }
}
