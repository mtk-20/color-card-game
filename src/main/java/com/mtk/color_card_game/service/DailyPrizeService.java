package com.mtk.color_card_game.service;

import com.mtk.color_card_game.entity.NormalDailyPrize;
import com.mtk.color_card_game.entity.VipDailyPrize;

import java.util.List;

public interface DailyPrizeService {

    List<NormalDailyPrize> getByDayAndPrizeIdNormal(int day, long id);

    List<NormalDailyPrize> getByDayAndPrizeRankNormal(int day, int rank);

    List<VipDailyPrize> getByDayAndPrizeIdVip(int day, long id);

    List<VipDailyPrize> getByDayAndPrizeRankVip(int day, int rank);
}
