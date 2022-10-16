package com.akgarg.todobackend.service.management;

import com.akgarg.todobackend.model.management.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;

import java.io.File;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@Service
public class ManagementServiceImpl implements ManagementService {

    private final SystemInfo systemInfo;
    private final Runtime runtime;
    private final ConfigurableApplicationContext applicationContext;

    public ManagementServiceImpl(final ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.systemInfo = new SystemInfo();
        this.runtime = Runtime.getRuntime();
    }

    @Override
    public OSInfo getOsInfo() {
        final var operatingSystem = this.systemInfo.getOperatingSystem();

        final String manufacturer = operatingSystem.getManufacturer();
        final String family = operatingSystem.getFamily();
        final long systemUptime = operatingSystem.getSystemUptime();
        final var versionInfo = operatingSystem.getVersionInfo();
        final int threadCount = operatingSystem.getThreadCount();
        final int processCount = operatingSystem.getProcessCount();
        final int bits = operatingSystem.getBitness();
        final int processId = operatingSystem.getProcessId();

        final var osInfo = new OSInfo();
        osInfo.setManufacturer(manufacturer);
        osInfo.setFamily(family);
        osInfo.setSystemUptime(systemUptime);
        osInfo.setVersionInfo(versionInfo.toString());
        osInfo.setThreadCount(threadCount);
        osInfo.setProcessCount(processCount);
        osInfo.setProcessId(processId);
        osInfo.setPlatform(bits + "-bit");

        return osInfo;
    }

    @Override
    public CpuInfo getCpuInfo(final long delay) {
        final var processor = this.systemInfo.getHardware().getProcessor();

        long _delay = delay > 2000 ? 2000 : delay;
        final int logicalProcessorCount = processor.getLogicalProcessorCount();
        final int physicalProcessorCount = processor.getPhysicalProcessorCount();
        final var processorIdentifier = processor.getProcessorIdentifier();
        final double systemCpuLoad = processor.getSystemCpuLoad(_delay) * 100;

        final var cpuInfo = new CpuInfo();
        cpuInfo.setProcessorIdentifier(processorIdentifier.toString());
        cpuInfo.setPhysicalProcessorCount(physicalProcessorCount);
        cpuInfo.setLogicalProcessorCount(logicalProcessorCount);
        cpuInfo.setSystemCpuLoad(String.format("%.2f", systemCpuLoad) + "%");

        return cpuInfo;
    }

    @Override
    public MemoryInfo getMemoryInfo() {
        final var memory = this.systemInfo.getHardware().getMemory();

        final long totalPhysicalMemory = memory.getTotal();
        final long availablePhysicalMemory = memory.getAvailable();
        final long maxJvmMemory = runtime.maxMemory();
        final long totalJvmMemory = runtime.totalMemory();
        final long freeJvmMemory = runtime.freeMemory();

        final var memoryInfo = new MemoryInfo();
        memoryInfo.setTotalPhysicalMemory(totalPhysicalMemory);
        memoryInfo.setAvailablePhysicalMemory(availablePhysicalMemory);
        memoryInfo.setMaxJvmMemory(maxJvmMemory);
        memoryInfo.setTotalJvmMemory(totalJvmMemory);
        memoryInfo.setFreeJvmMemory(freeJvmMemory);

        return memoryInfo;
    }

    @Override
    public DiskInfo getStorageInfo() {
        final var file = new File("/");

        final var diskInfo = new DiskInfo();
        diskInfo.setTotalStorage(file.getTotalSpace());
        diskInfo.setUsableStorage(file.getUsableSpace());
        diskInfo.setFreeStorage(file.getFreeSpace());

        return diskInfo;
    }

    @Override
    public AppInfo getApplicationInfo() {
        final int beanDefinitionCount = this.applicationContext.getBeanDefinitionCount();
        final long startupDate = this.applicationContext.getStartupDate();
        final String applicationName = this.applicationContext.getId();

        final var applicationInfo = new AppInfo();
        applicationInfo.setBeansCount(beanDefinitionCount);
        applicationInfo.setStartupDate(startupDate);
        applicationInfo.setApplicationName(applicationName);

        return applicationInfo;
    }

}
