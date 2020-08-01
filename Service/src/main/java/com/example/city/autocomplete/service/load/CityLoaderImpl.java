package com.example.city.autocomplete.service.load;

import com.example.city.autocomplete.service.City;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Lazy
public class CityLoaderImpl implements CityLoader {
    @Override
    @SneakyThrows
    public List<City> load() {
        URL url = CityLoaderImpl.class.getClassLoader().getResource("cities15000.txt");
        Path path = Paths.get(new URI(url.toString()));

        try (Stream<String> stream = Files.lines(path)) {
            return stream.map(line -> parseCity(line))
                    .collect(Collectors.toList());
        }
    }

    private City parseCity(String line) {
        String[] strings = line.split("\t");
        if (strings.length < 9) {
            throw new IllegalArgumentException("Unknown city source");
        }
        return new City(
                strings[1],
                strings[2],
                Float.parseFloat(strings[4]),
                Float.parseFloat(strings[5]),
                strings[8]);
    }
}
