package com.demo_graphql.service;

import com.demo_graphql.dto.ItemDto;
import com.demo_graphql.dto.LocationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class AppServiceImpl implements AppService {
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public ItemDto[] getAllItems() {
        ItemDto[] items = null;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/items"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            items = mapper.readValue(response.body(), ItemDto[].class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return items;

    }

    @Override
    public ItemDto getItemById(int id) {
        ItemDto item = null;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/items/" + id))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            item = mapper.readValue(response.body(), ItemDto.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public ItemDto createItem(int id, String name, String description, int locationId, String state, String address, String phoneNumber) {
        LocationDto location = new LocationDto(locationId, state, address, phoneNumber);
        ItemDto item = new ItemDto(id, name, description, location);
        String body;

        try {
            body = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/items"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            item = mapper.readValue(response.body(), ItemDto.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public void deleteItem(int id) {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:8090/items/" + id))
//                .POST(HttpRequest.BodyPublishers.noBody())
//                .build();
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
