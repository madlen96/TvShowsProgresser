package com.tai.tvshowsprogresser.runner;

import com.google.common.base.Strings;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.MovieDb;
import com.tai.tvshowsprogresser.TmdbConnection;
import com.tai.tvshowsprogresser.model.Movie;
import com.tai.tvshowsprogresser.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MovieCommandLineRunner implements CommandLineRunner {

    private final MovieRepository repository;
    private TmdbConnection tmdbConnection;
    private TheMovieDbApi api;

    public MovieCommandLineRunner(MovieRepository repository) throws MovieDbException {

        this.repository = repository;
        tmdbConnection = new TmdbConnection();
        api =  tmdbConnection.getApi();
    }


    public void getMovies(boolean imdb) throws MovieDbException, InterruptedException {
        //if (!isInit) init();
        List<String> movieIds = new ArrayList<String>();

        for (int x = 1 ; x<10;x++) {

            movieIds.add(String.valueOf(100*x));
        }
        movieIds.add(String.valueOf(990));

        System.out.println("Obtaining Movie data from TMDB...");
        for (String idStr : movieIds) {
            MovieDb movieDb;
            if (imdb) {
                movieDb = api.getMovieInfoImdb(idStr, "EN");
            } else {
                movieDb = api.getMovieInfo(Integer.parseInt(idStr), "EN");
            }

            Movie m = new Movie();
            m.setId(Long.valueOf(movieDb.getId()));
            m.setTitle(movieDb.getTitle());
//            m.setYear(Strings.isNullOrEmpty(movieDb.getReleaseDate()) ? null : movieDb.getReleaseDate().substring(0, 4));
//            m.setStatus(Strings.isNullOrEmpty(movieDb.getStatus()) ? null : movieDb.getStatus());
//            m.setBudget(movieDb.getBudget());
//            m.setRuntime(movieDb.getRuntime());
//            m.setPopularity(movieDb.getPopularity());
            String path = "https://image.tmdb.org/t/p/w185";
            m.setPosterPath(Strings.isNullOrEmpty(movieDb.getPosterPath()) ? "" : path+movieDb.getPosterPath());
//            m.setImdb_id(movieDb.getImdbID());
        //    returnList.add(m);
            repository.save(m);
            Thread.sleep(1000);
        }
        System.out.println("Obtaining Movie data complete.");
    }

    @Override
    public void run(String... strings) throws Exception {
        // Top beers from https://www.beeradvocate.com/lists/top/
//        Stream.of("Kentucky Brunch Brand Stout", "Good Morning", "Very Hazy", "King Julius",
//                "Budweiser", "Coors Light", "PBR").forEach(name ->
//                repository.save(new Movie(name))
//        );
        getMovies(true);
        repository.findAll().forEach(System.out::println);
    }
}