package com.example.city.autocomplete.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionCity {
    private String name;
    private float latitude;
    private float longitude;
    private float score;
}
