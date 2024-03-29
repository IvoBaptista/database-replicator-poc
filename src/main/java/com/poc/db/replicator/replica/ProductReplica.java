package com.poc.db.replicator.replica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT")
public class ProductReplica {
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
