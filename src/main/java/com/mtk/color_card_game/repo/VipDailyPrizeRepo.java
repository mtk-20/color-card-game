package com.mtk.color_card_game.repo;

import com.mtk.color_card_game.entity.VipDailyPrize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VipDailyPrizeRepo extends JpaRepository<VipDailyPrize, Long> {

    List<VipDailyPrize> findByDay(int day);

    @Query("SELECT p FROM VipDailyPrize p WHERE p.day = :day AND p.prize.id = :prizeId")
    List<VipDailyPrize> findByDayAndPrizeId(@Param("day") int day, @Param("prizeId") Long prizeId);

    @Query("SELECT p FROM VipDailyPrize p WHERE p.day = :day AND p.prize.prizeRank = :rank")
    List<VipDailyPrize> findByDayAndRank(@Param("day") int day, @Param("rank") int rank);
}
