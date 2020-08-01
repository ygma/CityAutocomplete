package com.example.CityAutocomplete.service.search;

import com.example.CityAutocomplete.service.City;

import java.util.List;

public interface CitySearch {
    List<City> search(String query);
}
