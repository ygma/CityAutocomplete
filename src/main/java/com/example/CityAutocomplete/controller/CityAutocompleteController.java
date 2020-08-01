package com.example.CityAutocomplete.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class CityAutocompleteController {

    @GetMapping("/suggestions")
    public SuggestionResponse getSuggestions(
            @RequestParam(value = "q") String q,
            @RequestParam(value = "latitude", required = false) Float latitude,
            @RequestParam(value = "longitude", required = false) Float longitude) {

        return new SuggestionResponse(Collections.emptyList());
    }

}
