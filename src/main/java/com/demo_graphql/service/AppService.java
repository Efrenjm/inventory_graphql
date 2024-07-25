package com.demo_graphql.service;

import com.demo_graphql.dto.ItemDto;

import java.util.Optional;

public interface AppService {
    ItemDto[] getAllItems();

    ItemDto getItemById(int id);

//    ItemDto createItem(int id, String name, String description, int locationId, String state, String address, String phoneNumber);

    ItemDto createItem(ItemDto item);

    void deleteItem(int id);

    ItemDto updateItem(ItemDto item);
}
