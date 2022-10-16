package com.akgarg.todobackend.service.management;

import com.akgarg.todobackend.model.management.*;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
public interface ManagementService {

    OSInfo getOsInfo();

    CpuInfo getCpuInfo(final long delay);

    MemoryInfo getMemoryInfo();

    DiskInfo getStorageInfo();

    AppInfo getApplicationInfo();

}
