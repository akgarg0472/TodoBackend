package com.akgarg.todobackend.model.management;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@Data
public class DiskInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 263527648645L;

    private long totalStorage;
    private long usableStorage;
    private long freeStorage;

}
