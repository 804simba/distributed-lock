package com.simba.distributedlock.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DistributedLockService {
    private final Logger log = LoggerFactory.getLogger(DistributedLockService.class);
    private final RedissonClient redissonClient;

    public DistributedLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void executeWithLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (isLocked) {
                log.info("Lock acquired, executing task...");
                Thread.sleep(5000); // Simulate processing
                log.info("Task completed, releasing lock...");
            } else {
                log.info("Could not acquire lock, skipping execution.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (isLocked) {
                lock.unlock();
                log.info("Lock released.");
            }
        }
    }
}
