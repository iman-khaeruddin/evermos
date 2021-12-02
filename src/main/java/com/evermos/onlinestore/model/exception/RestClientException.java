package com.evermos.onlinestore.model.exception;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;

public class RestClientException extends Exception {

    private static final long serialVersionUID = -594950573892941715L;

    private final int httpStatusCode;
    private final List<String> errors;
    private final String errorCode;

    public RestClientException(int httpStatusCode, String... messages) {
        this(httpStatusCode, Arrays.asList(messages));
    }

    public RestClientException(int httpStatusCode, List<String> messages) {
        super(CollectionUtils.isEmpty(messages) ? "" : messages.get(0));
        this.httpStatusCode = httpStatusCode;
        this.errors = messages;
        this.errorCode = "err";
    }

    public RestClientException(int httpStatusCode, List<String> messages, String errorCode) {
        super(CollectionUtils.isEmpty(messages) ? "" : messages.get(0));
        this.httpStatusCode = httpStatusCode;
        this.errors = messages;
        this.errorCode = errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
