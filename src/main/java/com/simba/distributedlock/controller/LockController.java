package com.simba.distributedlock.controller;

import com.simba.distributedlock.service.DistributedLockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lock")
public class LockController {
    private final DistributedLockService lockService;

    public LockController(DistributedLockService lockService) {
        this.lockService = lockService;
    }

    @GetMapping
    public String triggerLock() {
        new Thread(() -> lockService.executeWithLock("testLock")).start();
        new Thread(() -> lockService.executeWithLock("testLock")).start();
        new Thread(() -> lockService.executeWithLock("testLock")).start();
        return "Lock simulation triggered.";
    }
}
