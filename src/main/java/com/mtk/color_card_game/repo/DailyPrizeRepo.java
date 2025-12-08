package com.mtk.color_card_game.repo;

import com.mtk.color_card_game.entity.DailyPrize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DailyPrizeRepo extends JpaRepository<DailyPrize, Long> {

    List<DailyPrize> findByDay(int day);

    @Query("SELECT p FROM DailyPrize p WHERE p.day = :day AND p.prize.id = :prizeId")
    List<DailyPrize> findByDayAndPrizeId(@Param("day") int day, @Param("prizeId") Long prizeId);

    @Query("SELECT p FROM DailyPrize p WHERE p.day = :day AND p.prize.prizeRank = :rank")
    List<DailyPrize> findByDayAndRank(@Param("day") int day, @Param("rank") int rank);
}
