package com.example.city.autocomplete.controller;

import com.example.city.autocomplete.service.search.CitySearch;
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

        List<SuggestionCity> suggestionCities = citySearch.search(q, latitude, longitude)
                .stream()
                .map(city -> new SuggestionCity(
                        String.join(", ", city.getName(), city.getCountry()),
                        city.getLatitude(),
                        city.getLongitude(),
                        city.getScore(latitude, longitude)))
                .collect(Collectors.toList());

        return new SuggestionResponse(suggestionCities);
    }

}
