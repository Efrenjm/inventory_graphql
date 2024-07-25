package com.demo_graphql.service;

import com.demo_graphql.dto.ItemDto;
import com.demo_graphql.errorHandler.GraphQLException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AppServiceImpl implements AppService {
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public ItemDto[] getAllItems() {
        ItemDto[] items;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/items"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                items = mapper.readValue(response.body(), ItemDto[].class);
            } else if (response.statusCode() == 404) {
                throw new GraphQLException("Not found: No items found.",  ErrorType.NOT_FOUND);
            } else {
                throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
            }
        } catch (IOException | InterruptedException e) {
            throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
        }
        return items;
    }

    @Override
    public ItemDto getItemById(int id) {
        ItemDto item;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/items/" + id))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                item = mapper.readValue(response.body(), ItemDto.class);
            } else if (response.statusCode() == 404) {
                throw new GraphQLException("Not found: The item with id " + id + " does not exist.", ErrorType.NOT_FOUND);
            } else {
                throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
            }

        } catch (IOException | InterruptedException e) {
            throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
        }
        return item;
    }

    @Override
    public ItemDto createItem(ItemDto item) {
        String body;
        ItemDto newItem;

        try {
            body = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/items"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                newItem = mapper.readValue(response.body(), ItemDto.class);
            } else if (response.statusCode() == 409) {
                throw new GraphQLException("Conflict: The item id `" + item.getId() + "` is related to a different item.", ErrorType.BAD_REQUEST);
            } else if (response.statusCode() == 400) {
                throw new GraphQLException("Bad request: The request body does not contain the required data or the data is invalid.", ErrorType.BAD_REQUEST);
            } else {
                throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
            }

        } catch (IOException | InterruptedException e) {
            throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
        }

        return newItem;
    }

    @Override
    public void deleteItem(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/items/" + id))
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                return;
            } if (response.statusCode() == 404) {
                throw new GraphQLException("The item with id " + id + " does not exist.", ErrorType.NOT_FOUND);
            } else {
                throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
            }
        } catch (IOException | InterruptedException e) {
            throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
        }
    }

    @Override
    public ItemDto updateItem(ItemDto item) {
        String body;
        ItemDto updatedItem;

        try {
            body = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/items"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                updatedItem = mapper.readValue(response.body(), ItemDto.class);
            } else if (response.statusCode() == 404) {
                throw new GraphQLException("Not found: The item with id `" + item.getId() + "` does not exist.", ErrorType.NOT_FOUND);
            } else if (response.statusCode() == 400) {
                throw new GraphQLException("Bad request: The request body does not contain the required data or the data is invalid.", ErrorType.BAD_REQUEST);
            } else {
                throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
            }

        } catch (IOException | InterruptedException e) {
            throw new GraphQLException("Internal server error.", ErrorType.INTERNAL_ERROR);
        }
        return updatedItem;
    }
}
