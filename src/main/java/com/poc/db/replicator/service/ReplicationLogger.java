package com.poc.db.replicator.service;

import com.poc.db.replicator.replica.ReplicationLog;
import com.poc.db.replicator.replica.ReplicationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReplicationLogger {
    private final ReplicationLogRepository replicationLogRepository;
    private ReplicationLog currentLog;

    private long epochStart;

    public enum LogStatus{
        STARTED,
        EXTRACTED,
        ABORTED,
        FINISHED,
    }

    public void startLog(){
        epochStart = System.nanoTime();

        currentLog = new ReplicationLog();
        currentLog.setStartDate(this.getCurrentDate());
        currentLog.setStatus(LogStatus.STARTED.name());
        replicationLogRepository.save(currentLog);
    }
    public void updateStatus(LogStatus status){
        currentLog.setStatus(status.name());
        replicationLogRepository.save(currentLog);
    }

    public void finishLog() {
        if(currentLog == null) {
            throw new RuntimeException("Initialize log first.");
        }

        currentLog.setEndDate(this.getCurrentDate());
        currentLog.setStatus(LogStatus.FINISHED.name());
        currentLog.setResult("Replication Ended Successfully.");
        currentLog.setElapsedTimeMs(calculateElapsedTime());
        replicationLogRepository.save(currentLog);

    }

    public void abortLog(String message) {
        if(currentLog == null) {
            throw new RuntimeException("Initialize log first.");
        }

        currentLog.setEndDate(this.getCurrentDate());
        currentLog.setStatus(LogStatus.ABORTED.name());
        currentLog.setResult("Replication was aborted: " + message.substring(0,200));
        currentLog.setElapsedTimeMs(calculateElapsedTime());
        replicationLogRepository.save(currentLog);
    }

    private Date getCurrentDate() {
        return new Date();
    }

    private long calculateElapsedTime() {
        return System.nanoTime() - epochStart;
    }

}
