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
        log.info("Normal - Day {} And Prize Id {}", day, id);
        log.info("Normal Daily Prize By Id : {}", normalRepo.findByDayAndPrizeId(day, id).size());
        log.info(" --------------------- --------------------- ");
        return normalRepo.findByDayAndPrizeId(day, id);
    }

    @Override
    public List<NormalDailyPrize> getByDayAndPrizeRankNormal(int day, int rank) {
        log.info("Normal - Day {} And Rank {}", day, rank);
        log.info("Normal Daily Prize By Rank : {}", normalRepo.findByDayAndRank(day, rank).size());
        log.info(" ------------------- ----------------------- ");
        return normalRepo.findByDayAndRank(day, rank);
    }

    @Override
    public List<VipDailyPrize> getByDayAndPrizeIdVip(int day, long id) {
        log.info("Vip - Day {} And Prize Id {}", day, id);
        log.info("Vip Daily Prize By Id : {}", vipRepo.findByDayAndPrizeId(day, id).size());
        log.info(" -------------------- - --------------------- ");
        return vipRepo.findByDayAndPrizeId(day, id);
    }

    @Override
    public List<VipDailyPrize> getByDayAndPrizeRankVip(int day, int rank) {
        log.info("Vip - Day {} And Rank {}", day, rank);
        log.info("Vip Daily Prize By Rank : {}", vipRepo.findByDayAndRank(day, rank).size());
        log.info(" ------------------- - ---------------------- ");
        return vipRepo.findByDayAndRank(day, rank);
    }
}
