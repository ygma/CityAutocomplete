package com.example.CityAutocomplete.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionResponse {
    private List<SuggestionCity> suggestions;
}