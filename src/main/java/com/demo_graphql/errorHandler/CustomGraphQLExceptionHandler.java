package com.demo_graphql.errorHandler;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomGraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof GraphQLException) {
            return GraphqlErrorBuilder.newError()
                    .errorType(((GraphQLException) ex).getReason())
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    //.location(env.getField().getSourceLocation())
                    .build();
        } else {
            return null;
        }

    }
}
