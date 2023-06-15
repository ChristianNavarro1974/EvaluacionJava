package com.example.evaluacionjava.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("mensaje")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
