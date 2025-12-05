package com.mtk.color_card_game.service;

import com.mtk.color_card_game.entity.Prize;

import java.util.List;

public interface PrizeService {

    List<Prize> getAllPrize();

    List<Prize> getAvailable();
}
