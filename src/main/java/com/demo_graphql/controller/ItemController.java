package com.demo_graphql.controller;

import com.demo_graphql.dto.ItemDto;
import com.demo_graphql.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {
    private final AppService appService;

    @Autowired
    public ItemController(AppService appService) {
        this.appService = appService;
    }

    @QueryMapping
    public ItemDto[] getAllItems() {
        return appService.getAllItems();
    };

    @QueryMapping
    public ItemDto getItemById(@Argument Integer id) {
        return appService.getItemById(id);
    }

    @MutationMapping
    public ItemDto createItem(@Argument int id, @Argument String name, @Argument String description, @Argument int locationId, @Argument String state, @Argument String address, @Argument String phoneNumber) {
        return appService.createItem(id, name, description, locationId, state, address, phoneNumber);
    }

//    @MutationMapping
//    public void deleteItem(@Argument Integer id) {
//        appService.deleteItem(id);
//    }
}
