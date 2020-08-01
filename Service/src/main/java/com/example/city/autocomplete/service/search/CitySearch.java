package com.example.city.autocomplete.service.search;

import com.example.city.autocomplete.service.City;

import java.util.List;

public interface CitySearch {
    void initialize();

    List<City> search(String query, Float latitude, Float longitude);
}
