package com.akgarg.todobackend.model.response;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 28-09-2022
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CacheKVResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -327486257386754646L;

    private String key;
    private Object value;

}
