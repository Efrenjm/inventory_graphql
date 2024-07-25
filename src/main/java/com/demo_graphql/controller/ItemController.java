package com.demo_graphql.controller;

import com.demo_graphql.dto.ItemDto;
import com.demo_graphql.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    public ItemDto createItem(@Argument ItemDto item) {
        return appService.createItem(item);
    }

    @MutationMapping
    public void deleteItem(@Argument Integer id) {
        appService.deleteItem(id);
    }

    @MutationMapping
    public ItemDto updateItem(@Argument ItemDto item) {
        return appService.updateItem(item);
    }
}
