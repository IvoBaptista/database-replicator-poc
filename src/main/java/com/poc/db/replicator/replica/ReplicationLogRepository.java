package com.poc.db.replicator.replica;

import org.springframework.data.repository.CrudRepository;

public interface ReplicationLogRepository extends CrudRepository<ReplicationLog, String> {
}
