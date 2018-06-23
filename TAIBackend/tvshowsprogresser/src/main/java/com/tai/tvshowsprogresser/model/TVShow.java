package com.tai.tvshowsprogresser.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class TVShow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String posterPath;

    @OneToMany(fetch = FetchType.LAZY,mappedBy="tvShow")
    private List<Season> seasons;

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {

        return posterPath;
    }

    public TVShow() {
    }

    public TVShow(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }


    @Override
    public String toString() {
        return "TVShow{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath +  '\'' +
                '}';
    }
}
