package com.mtk.color_card_game.service.impl;

import com.mtk.color_card_game.entity.NormalDailyPrize;
import com.mtk.color_card_game.entity.VipDailyPrize;
import com.mtk.color_card_game.repo.NormalDailyPrizeRepo;
import com.mtk.color_card_game.repo.VipDailyPrizeRepo;
import com.mtk.color_card_game.service.DailyPrizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyPrizeServiceImpl implements DailyPrizeService {

    private final NormalDailyPrizeRepo normalRepo;
    private final VipDailyPrizeRepo vipRepo;

    @Override
    public List<NormalDailyPrize> getByDayAndPrizeIdNormal(int day, long id) {
        log.info("Normal - Day {} / Prize Id {} / Size {}", day, id, normalRepo.findByDayAndPrizeId(day, id).size());
        return normalRepo.findByDayAndPrizeId(day, id);
    }

    @Override
    public List<NormalDailyPrize> getByDayAndPrizeRankNormal(int day, int rank) {
        log.info("Normal - Day {} / Rank {} / Size {}", day, rank, normalRepo.findByDayAndRank(day, rank).size());
        return normalRepo.findByDayAndRank(day, rank);
    }

    @Override
    public List<VipDailyPrize> getByDayAndPrizeIdVip(int day, long id) {
        log.info("Vip - Day {} / Prize Id {} / Size {}", day, id, vipRepo.findByDayAndPrizeId(day, id).size());
        return vipRepo.findByDayAndPrizeId(day, id);
    }

    @Override
    public List<VipDailyPrize> getByDayAndPrizeRankVip(int day, int rank) {
        log.info("Vip - Day {} / Rank {} / Size {}", day, rank, vipRepo.findByDayAndRank(day, rank).size());
        return vipRepo.findByDayAndRank(day, rank);
    }
}
