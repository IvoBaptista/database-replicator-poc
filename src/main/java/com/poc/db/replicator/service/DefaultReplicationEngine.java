package com.poc.db.replicator.service;

import com.poc.db.replicator.db.Product;
import com.poc.db.replicator.db.ProductRepository;
import com.poc.db.replicator.replica.ProductReplica;
import com.poc.db.replicator.replica.ProductReplicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

//TODO: Handle exceptions
@Service
@RequiredArgsConstructor
public class DefaultReplicationEngine implements ReplicationEngine {
    private final ProductReplicaRepository productReplicaRepository;
    private final ProductRepository productRepository;

    @Override
    public void replicateProductEntities() {
        replicateProducts(extractOriginalProducts());
    }

    @Transactional("dbTransactionManager")
    private List<Product> extractOriginalProducts() {
        return productRepository.findAll();
    }

    @Transactional("replicaTransactionManager")
    private void replicateProducts(List<Product> products) {
        List<ProductReplica> replicas = new LinkedList<>();

        for (Product prod : products) {
            //TODO: Migrate to a mapping framework. Example Mapstruct
            ProductReplica replica = new ProductReplica(prod.getWarehouse(),
                    prod.getBarCode(),
                    prod.getDesignation(),
                    prod.getCategory(),
                    prod.getSubCategory(),
                    prod.getPrice(),
                    prod.getStock(),
                    prod.getRefs()
            );
            replicas.add(replica);
        }

        productReplicaRepository.saveAll(replicas);
    }
}
