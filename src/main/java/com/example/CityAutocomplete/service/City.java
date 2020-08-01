package com.example.CityAutocomplete.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class City {
    private String name;
    private String asciiName;
    private float latitude;
    private float longitude;
}
