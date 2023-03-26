package com.poc.db.replicator.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
    private String warehouse;
    @Id
    @Column(name = "BAR_CODE")
    private String barCode;
    private String designation;
    private String category;
    @Column(name = "SUB_CATEGORY")
    private String subCategory;
    private double price;
    private int stock;
    private String refs;
}
