package com.williaz.bootdemo.repository;

import com.williaz.bootdemo.domain.Product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
public class JdbcProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {
        productRepository.createTable();
    }

    @After
    public void tearDown() throws Exception {
        productRepository.dropTable();
    }

    @Test
    public void testCrud() {
        Product p1 = new Product(1L, "mask", "care");
        Product p2 = new Product(2L, "glasses", "care");
        productRepository.add(p1);
        productRepository.add(p2);
        assertEquals(p1, productRepository.findById(p1.getId()));
        assertEquals(2, productRepository.findByType("care").size());

        p1.setType("emergency");
        assertEquals(1, productRepository.update(p1));
        assertEquals(p1, productRepository.findById(p1.getId()));

        assertEquals(1, productRepository.delete(2L));
        assertNull(productRepository.findById(2L));
        assertEquals(0, productRepository.findByType("care").size());
    }

}