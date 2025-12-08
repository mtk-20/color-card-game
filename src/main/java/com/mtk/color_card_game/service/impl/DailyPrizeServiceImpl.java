package com.mtk.color_card_game.service.impl;

import com.mtk.color_card_game.entity.DailyPrize;
import com.mtk.color_card_game.repo.DailyPrizeRepo;
import com.mtk.color_card_game.service.DailyPrizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyPrizeServiceImpl implements DailyPrizeService {

    private final DailyPrizeRepo repo;

    @Override
    public List<DailyPrize> getByDayAndPrizeId(int day, long id) {
        log.info("Day {} And Prize Id {}", day, id);
        log.info("Daily Prize By Id : {}", repo.findByDayAndPrizeId(day, id).size());
        return repo.findByDayAndPrizeId(day, id);
    }

    @Override
    public List<DailyPrize> getByDayAndPrizeRank(int day, int rank) {
        log.info("Day {} And Rank {}", day, rank);
        log.info("Daily Prize By Rank : {}", repo.findByDayAndRank(day, rank).size());
        return repo.findByDayAndRank(day, rank);
    }
}
