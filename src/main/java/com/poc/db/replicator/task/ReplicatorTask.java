package com.poc.db.replicator.task;

import com.poc.db.replicator.service.ReplicationEngine;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ReplicatorTask {

    private static final Logger logger = LoggerFactory.getLogger(ReplicatorTask.class);
    private final ReplicationEngine replicationEngine;

    @Scheduled(fixedRate = 15000)
    public void executeReplication() {
        logger.info("Replication Starting at {{}}", new Date());
        long epochStart = System.nanoTime();
        replicationEngine.replicateProductEntities();
        long epochEnd = System.nanoTime();
        logger.info("Replication Ended. Elapsed: {}s", ((double)epochEnd - (double)epochStart) / 1000000000.0);
    }
}
