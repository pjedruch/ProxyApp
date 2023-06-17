package com.example.proxyapp;


public class PostModel {

    private Integer id;

    private String source;

    private int timeout;

    public PostModel(String source, int timeout) {
        this.source = source;
        this.timeout = timeout;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public int getTimeout() {
        return timeout;
    }
}
