package com.williaz.bootdemo.domain;

import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private String type;

    public Product(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Product() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (!id.equals(product.id)) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        return type != null ? type.equals(product.type) : product.type == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
