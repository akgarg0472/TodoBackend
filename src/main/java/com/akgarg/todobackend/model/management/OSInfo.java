package com.akgarg.todobackend.model.management;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@Data
public class OSInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -2475234758L;

    private String manufacturer;
    private String family;
    private String versionInfo;
    private String platform;
    private long systemUptime;
    private int threadCount;
    private int processCount;
    private int processId;

}
