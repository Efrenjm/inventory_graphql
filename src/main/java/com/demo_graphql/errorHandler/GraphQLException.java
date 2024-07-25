package com.demo_graphql.errorHandler;

import org.springframework.graphql.execution.ErrorType;

public class GraphQLException extends RuntimeException {
    private ErrorType reason;

    public GraphQLException(String message, ErrorType reason) {
        super(message);
        this.reason = reason;
    }

    public ErrorType getReason() {
        return reason;
    }

    public void setReason(ErrorType reason) {
        this.reason = reason;
    }
}
