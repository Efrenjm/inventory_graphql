package com.demo_graphql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record Location(Integer id, String state, String address, String phoneNumber) {
    public static List<Location> locations = Arrays.asList(
            new Location(1, "location 1", "address 1", "1234567891"),
            new Location(2, "location 2", "address 2", "1234567892"),
            new Location(3, "location 3", "address 3", "1234567893")
    );

    public static Optional<Location> getLocationById(Integer id) {
        return locations.stream()
                .filter(location -> location.id.equals(id))
                .findFirst();
    }
}
