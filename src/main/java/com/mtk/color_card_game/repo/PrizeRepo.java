package com.mtk.color_card_game.repo;

import com.mtk.color_card_game.entity.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrizeRepo extends JpaRepository<Prize, Long> {

    List<Prize> findByQuantityGreaterThan(int min);
}
