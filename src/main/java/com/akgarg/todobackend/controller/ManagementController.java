package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.model.management.*;
import com.akgarg.todobackend.service.management.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@RestController
@RequestMapping("/api/v1/admin/management")
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;

    @GetMapping("/cpu-info")
    public CpuInfo getCpuInfo(@RequestParam(value = "delay", defaultValue = "1000") final long delay) {
        return this.managementService.getCpuInfo(delay);
    }

    @GetMapping("/os-info")
    public OSInfo getOsInfo() {
        return this.managementService.getOsInfo();
    }

    @GetMapping("/disk-info")
    public DiskInfo getDiskInfo() {
        return this.managementService.getStorageInfo();
    }

    @GetMapping("/memory-info")
    public MemoryInfo getMemoryInfo() {
        return this.managementService.getMemoryInfo();
    }

    @GetMapping("/application-info")
    public AppInfo getApplicationInfo() {
        return this.managementService.getApplicationInfo();
    }

}
