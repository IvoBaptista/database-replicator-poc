package com.poc.db.replicator;

import com.poc.db.replicator.db.Product;
import com.poc.db.replicator.db.ProductRepository;
import com.poc.db.replicator.replica.ProductReplica;
import com.poc.db.replicator.replica.ProductReplicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
public class ReplicatorApplication implements CommandLineRunner {
	@Autowired
	ProductRepository repo;

	@Autowired
	ProductReplicaRepository replicaRepo;
	public static void main(String[] args) {
		SpringApplication.run(ReplicatorApplication.class, args);
	}

	@Override
	@Transactional("dbTransactionManager")
	public void run(String... args) throws Exception {

		long epochStart = System.nanoTime();
		System.out.println(repo.toString());

		List<Product> prodList = repo.findAll();
		int i = 0;
		for(Product prod : prodList) {
			++i;
			System.out.println("Prod: " + i + ", name: " + prod.getDesignation() + ", stock: " + prod.getStock());
		}

		replicateProducts(prodList);

		long epochEnd = System.nanoTime();
		System.out.println("Success: " + (epochEnd - epochStart)/1000000000 + "s");
	}


	@Transactional("replicaTransactionManager")
	private void replicateProducts(List<Product> products) {
		List<ProductReplica> replicas = new LinkedList<>();
		for(Product prod : products) {
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

		replicaRepo.saveAll(replicas);
	}

}

