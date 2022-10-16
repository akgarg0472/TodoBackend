package com.akgarg.todobackend.constants;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
public enum KafkaEnums {

    EMAIL_TOPIC("todo-email-kafka-topic");

    private final String value;

    KafkaEnums(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
