package com.demo_graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private int id;
    private String name;
    private String description;
    private LocationDto location;

    public ItemDto(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
