package com.poc.db.replicator.task;

import com.poc.db.replicator.replica.ReplicationLog;
import com.poc.db.replicator.replica.ReplicationLogRepository;
import com.poc.db.replicator.service.DefaultReplicationEngine;
import com.poc.db.replicator.service.ReplicationEngine;
import com.poc.db.replicator.service.ReplicationLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ReplicatorTask {

    private static final Logger logger = LoggerFactory.getLogger(ReplicatorTask.class);
    private final ReplicationEngine replicationEngine;


    @Scheduled(fixedRateString = "${scheduller.scheduled-rate-ms}")
    public void executeReplication() {


        logger.info("Replication Starting at {}", new Date());

        long epochStart = System.nanoTime();
        try {

            replicationEngine.replicateProductEntities();
            long epochEnd = System.nanoTime();
            logger.info("Replication Ended. Elapsed: {}s", ((double)epochEnd - (double)epochStart) / 1000000000.0);

        } catch (Exception exception) {
            long epochEnd = System.nanoTime();
            logger.info("Replication Aborted. Elapsed: {}s ErrorMessage: {}s",
                    ((double)epochEnd - (double)epochStart) / 1000000000.0,
                    exception.getMessage());
        }
    }
}
