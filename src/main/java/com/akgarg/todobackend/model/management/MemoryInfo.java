package com.akgarg.todobackend.model.management;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@Data
public class MemoryInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -289463465468L;

    private long totalPhysicalMemory;
    private long availablePhysicalMemory;
    private long totalJvmMemory;
    private long freeJvmMemory;
    private long maxJvmMemory;

}
