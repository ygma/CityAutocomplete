package com.example.CityAutocomplete.controller;

import com.example.CityAutocomplete.service.search.CitySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Lazy
public class CityAutocompleteController {
    @Autowired
    private CitySearch citySearch;

    @GetMapping("/suggestions")
    public SuggestionResponse getSuggestions(
            @RequestParam(value = "q") String q,
            @RequestParam(value = "latitude", required = false) Float latitude,
            @RequestParam(value = "longitude", required = false) Float longitude) {

        List<SuggestionCity> suggestionCities = citySearch.search(q)
                .stream()
                .map(city -> new SuggestionCity(city.getName(), city.getLatitude(), city.getLongitude(), 1))
                .collect(Collectors.toList());

        return new SuggestionResponse(suggestionCities);
    }

}
