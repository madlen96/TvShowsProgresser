package com.tai.tvshowsprogresser.controller;

import com.google.common.base.Strings;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.MovieDb;
import com.tai.tvshowsprogresser.model.Movie;
import com.tai.tvshowsprogresser.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;
import com.tai.tvshowsprogresser.TmdbConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    private MovieRepository repository;
    TmdbConnection tmdbConnection = new TmdbConnection();
    private TheMovieDbApi api;

    public MovieController(MovieRepository repository) throws MovieDbException {
        this.repository = repository;
        this.api = tmdbConnection.getApi();
    }

    @GetMapping("/good-movies")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    public Collection<Movie> goodMovies() {
        return repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/search", method=RequestMethod.POST, consumes="application/json")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    public @ResponseBody List<Movie> search(@RequestBody String title) throws MovieDbException {
        MovieDb movieDb;
        List<Movie> resultList = new ArrayList<>();
        List<MovieDb> list = api.searchMovie(title, "english", true);
        for (MovieDb m : list) {
            Movie movie = tmdbMovietoMovie(m);
            resultList.add(movie);
        }
        return resultList;
    }

    public Movie tmdbMovietoMovie(MovieDb movieDb) {
        Movie m = new Movie();
        m.setId(Long.valueOf(movieDb.getId()));
        m.setTitle(movieDb.getTitle());
        String path = "https://image.tmdb.org/t/p/w185";
        m.setPosterPath(Strings.isNullOrEmpty(movieDb.getPosterPath()) ? "" : path + movieDb.getPosterPath());
        return m;
    }
}

