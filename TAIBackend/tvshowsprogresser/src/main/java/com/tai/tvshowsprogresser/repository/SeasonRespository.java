package com.tai.tvshowsprogresser.repository;

import com.tai.tvshowsprogresser.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SeasonRespository extends JpaRepository<Season,Long> {
   // Page<Season> findByTVShowId(Long tvshowId, Pageable pageable);

}
