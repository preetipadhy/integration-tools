package com.amadeus.imdgenerator.swagger;

public class Method {
    private String method;
    private String operationId;
    private String summary;
    private String produces;
    private String consumes;
    private Node request;
    private Node response;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String operation) {
        this.summary = operation;
    }

    public String getProduces() {
        return produces;
    }

    public void setProduces(String produces) {
        this.produces = produces;
    }

    public String getConsumes() {
        return consumes;
    }

    public void setConsumes(String consumes) {
        this.consumes = consumes;
    }

    public Node getRequest() {
        return request;
    }

    public void setRequest(Node request) {
        this.request = request;
    }

    public Node getResponse() {
        return response;
    }

    public void setResponse(Node response) {
        this.response = response;
    }
}
