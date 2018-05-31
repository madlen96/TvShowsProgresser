package com.tai.tvshowsprogresser.controller;

import com.tai.tvshowsprogresser.model.Movie;
import com.tai.tvshowsprogresser.repository.MovieRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    private MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/good-movies")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    public Collection<Movie> goodMovies() {
        return repository.findAll().stream()
                    .filter(this::isGreat)
                .collect(Collectors.toList());
    }

        private boolean isGreat(Movie movie) {
            return !movie.getTitle().equals("HaryCzary") &&
                    !movie.getTitle().equals("Lala") &&
                    !movie.getTitle().equals("PBS");
        }
}

