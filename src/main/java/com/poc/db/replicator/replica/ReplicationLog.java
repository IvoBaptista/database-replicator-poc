package com.poc.db.replicator.replica;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.Date;

@Entity
@Data
@Table(name="REPLICATION_LOG")
public class ReplicationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name="START_DATE", columnDefinition = "TIME")
    Date startDate;

    @Column(name="END_DATE", columnDefinition = "TIME")
    Date endDate;

    @Column(name="ELAPSED_MS")
    Long elapsedTimeMs;

    @Column(name="RESULT")
    String result;

    @Column(name="STATUS")
    String status;
}
