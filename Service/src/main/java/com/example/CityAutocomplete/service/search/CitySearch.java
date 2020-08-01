package com.example.CityAutocomplete.service.search;

import com.example.CityAutocomplete.service.City;

import java.util.List;

public interface CitySearch {
    void initialize();

    List<City> search(String query, Float latitude, Float longitude);
}
