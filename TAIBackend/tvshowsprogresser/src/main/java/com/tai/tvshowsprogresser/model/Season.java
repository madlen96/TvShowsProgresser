package com.tai.tvshowsprogresser.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class Season {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TVShow tvShow;

    private int seasonNumber;

    private boolean watched;

    @OneToMany(fetch = FetchType.LAZY,mappedBy="season")
    private List<Episode> episodes;

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWasWatched(boolean wasWatched) {
        this.watched = wasWatched;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {

        this.seasonNumber = seasonNumber;
    }


}
