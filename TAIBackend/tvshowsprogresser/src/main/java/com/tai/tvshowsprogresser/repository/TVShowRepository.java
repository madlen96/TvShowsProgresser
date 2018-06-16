package com.tai.tvshowsprogresser.repository;

import com.tai.tvshowsprogresser.model.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TVShowRepository extends JpaRepository<TVShow, Long> {
}
