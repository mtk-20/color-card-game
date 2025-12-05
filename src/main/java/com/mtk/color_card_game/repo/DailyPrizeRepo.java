package com.mtk.color_card_game.repo;

import com.mtk.color_card_game.entity.DailyPrize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DailyPrizeRepo extends JpaRepository<DailyPrize, Long> {

    List<DailyPrize> findByDay(int day);

    @Query("SELECT p FROM DailyPrize p WHERE p.day = :day AND p.prize.id = :prizeId")
    Optional<DailyPrize> findByDayAndPrizeId(@Param("day") int day, @Param("prizeId") Long prizeId);
}
