package com.tai.tvshowsprogresser.repository;

import com.tai.tvshowsprogresser.model.Season;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRespository extends JpaRepository<Season,Long> {
   // Page<Season> findByTVShowId(Long tvshowId, Pageable pageable);

}
