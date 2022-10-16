package com.akgarg.todobackend.model.management;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
@Data
public class AppInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 7563476845679587L;

    private int beansCount;
    private long startupDate;
    private String applicationName;

}
