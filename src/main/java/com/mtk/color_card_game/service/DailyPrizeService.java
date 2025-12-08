package com.mtk.color_card_game.service;

import com.mtk.color_card_game.entity.DailyPrize;

import java.util.List;

public interface DailyPrizeService {

    List<DailyPrize> getByDayAndPrizeId(int day, long id);

    List<DailyPrize> getByDayAndPrizeRank(int day, int rank);
}
