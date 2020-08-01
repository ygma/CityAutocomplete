package com.example.CityAutocomplete.service;

import com.example.CityAutocomplete.util.GeoUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class City {
    private final int MAX_DISTANCE_ON_EARTH = 20015;
    private String name;
    private String asciiName;
    private float latitude;
    private float longitude;

    public float getScore(Float latitude2, Float longitude2) {
        if (latitude2 == null || longitude2 == null) {
            return 1;
        }
        double distance = GeoUtils.distance(latitude, longitude, latitude2, longitude2, "K");

        return (float) (MAX_DISTANCE_ON_EARTH - distance) / MAX_DISTANCE_ON_EARTH;
    }

}
