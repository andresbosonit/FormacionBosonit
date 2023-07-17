package com.example.block6pathvariableheaders;

import org.springframework.stereotype.Component;

import java.util.List;

public class TodosLosDatos {
    private Object body;
    private List<String> headers;
    private List<String> requestParams;

    public TodosLosDatos(Object body, List<String> headers, List<String> requestParams) {
        this.body = body;
        this.headers = headers;
        this.requestParams = requestParams;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<String> requestParams) {
        this.requestParams = requestParams;
    }
}
