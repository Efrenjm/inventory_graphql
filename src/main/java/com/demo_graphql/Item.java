package com.demo_graphql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record Item(Integer id, String name, String description, Integer locationId) {
    public static List<Item> items = Arrays.asList(
            new Item(1, "name 1", "description 1", 1),
            new Item(2, "name 2", "description 2", 2),
            new Item(3, "name 3", "description 3", 3)
    );

    public static Optional<Item> getItemById(Integer id) {
        return items.stream()
                .filter(item -> item.id.equals(id))
                .findFirst();
    }
}