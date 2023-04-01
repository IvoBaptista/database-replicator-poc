package com.poc.db.replicator;

import com.poc.db.replicator.service.ReplicationEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ReplicatorApplication implements CommandLineRunner {

    @Autowired
    ReplicationEngine replicationEngine;
    @Autowired
    ApplicationContext ctx;

    public static void main(String[] args) {
        SpringApplication.run(ReplicatorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        long epochStart = System.nanoTime();

        replicationEngine.replicateProductEntities();


        long epochEnd = System.nanoTime();
        System.out.println("Success: " + ((double)epochEnd - (double)epochStart) / 1000000000.0 + "s");
    }


}

