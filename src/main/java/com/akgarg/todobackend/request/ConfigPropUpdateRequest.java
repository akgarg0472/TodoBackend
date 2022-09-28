package com.akgarg.todobackend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Akhilesh Garg
 * @since 29-09-2022
 */
@Getter
@Setter
@ToString
public class ConfigPropUpdateRequest {

    private String key;
    private String value;

}
