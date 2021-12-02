package com.evermos.onlinestore.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RestResult<T> {

    public static final String STATUS_SUCCESS = "success".intern();
    public static final String STATUS_ERROR   = "error".intern();

    @JsonView(Object.class)
    private String             status;
    @JsonView(Object.class)
    private List<String>       messages;
    @JsonView(Object.class)
    private T                  data;
    @JsonView(Object.class)
    private Map<String,Object> metaData;

    public RestResult() {
        messages = new ArrayList<String>();
        status = STATUS_SUCCESS;
    }

    public static <T> RestResult<T> create(Class<T> genericType) {
        return new RestResult<T>();
    }

    public static <T> RestResult<T> ok(Class<T> type) {
        RestResult<T> result = new RestResult<T>();
        result.status = STATUS_SUCCESS;
        return result;
    }

    public void ok(String message) {
        this.status = STATUS_SUCCESS;
        if (StringUtils.isNotEmpty(message)) {
            addMessage(message);
        }
    }

    public static <T> RestResult<T> fail(Class<T> type) {
        RestResult<T> result = new RestResult<T>();
        result.status = STATUS_ERROR;
        return result;
    }

    public void fail(String message) {
        this.status = STATUS_ERROR;
        if (StringUtils.isNotEmpty(message)) {
            addMessage(message);
        }
    }

    public String getStatus() {
        return status;
    }

    public RestResult<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public List<String> getMessages() {
        return messages;
    }

    public RestResult<T> setMessages(List<String> messages) {
        for (String message : messages) {
            this.messages.add(message);
        }
        return this;
    }

    public RestResult<T> addMessage(String message) {
        messages.add(message);
        return this;
    }

    public T getData() {
        return data;
    }

    public static <T> T getRestResultData(Object object, Class<T> clazz) {
        return new GsonBuilder().create().fromJson(new GsonBuilder().create().toJson(object), clazz);
    }

    public static <T> List<T> getRestResultListData(Object object, Class<T[]> clazz) {
        return Arrays.asList(new Gson().fromJson(new GsonBuilder().create().toJson(object), clazz));
    }

    public RestResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public RestResult<T> addError(String error) {
        this.status = STATUS_ERROR;
        this.addMessage(error);
        return this;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
    }

    @JsonIgnore
    public boolean isError() {
        return !StringUtils.equals(STATUS_SUCCESS, status);
    }

}
