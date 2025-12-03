package com.mtk.color_card_game.repo;

import com.mtk.color_card_game.entity.DailyPrize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPrizeRepo extends JpaRepository<DailyPrize, Long> {
}
