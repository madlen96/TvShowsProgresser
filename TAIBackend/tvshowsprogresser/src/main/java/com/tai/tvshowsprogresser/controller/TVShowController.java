package com.tai.tvshowsprogresser.controller;

import com.tai.tvshowsprogresser.model.TVShow;
import com.tai.tvshowsprogresser.repository.TVShowRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;

public class TVShowController {
    private TVShowRepository repository;

    public TVShowController(TVShowRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tvshows")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    public Collection<TVShow> tvShows() {
        return new ArrayList<>(repository.findAll());
    }


}
