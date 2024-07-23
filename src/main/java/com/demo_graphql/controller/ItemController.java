package com.demo_graphql.controller;

import com.demo_graphql.Item;
import com.demo_graphql.Location;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    @QueryMapping
    public List<Item> getAllItems() {
        return Item.items;
    };

    @QueryMapping
    public Optional<Item> getItemById(@Argument Integer id) {
        return Item.getItemById(id);
    }

    @SchemaMapping
    public Optional<Location> location(Item item) {
        return Location.getLocationById(item.locationId());
    }
}
