package com.akgarg.todobackend.model.management;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@Data
public class CpuInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 34785687345867L;

    private int logicalProcessorCount;
    private int physicalProcessorCount;
    private String processorIdentifier;
    private String systemCpuLoad;

}
