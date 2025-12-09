package com.mtk.color_card_game.repo;

import com.mtk.color_card_game.entity.NormalDailyPrize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NormalDailyPrizeRepo extends JpaRepository<NormalDailyPrize, Long> {

    List<NormalDailyPrize> findByDay(int day);

    @Query("SELECT p FROM NormalDailyPrize p WHERE p.day = :day AND p.prize.id = :prizeId")
    List<NormalDailyPrize> findByDayAndPrizeId(@Param("day") int day, @Param("prizeId") Long prizeId);

    @Query("SELECT p FROM NormalDailyPrize p WHERE p.day = :day AND p.prize.prizeRank = :rank")
    List<NormalDailyPrize> findByDayAndRank(@Param("day") int day, @Param("rank") int rank);
}
