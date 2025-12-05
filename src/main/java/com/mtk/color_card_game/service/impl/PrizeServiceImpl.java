package com.mtk.color_card_game.service.impl;

import com.mtk.color_card_game.entity.Prize;
import com.mtk.color_card_game.repo.PrizeRepo;
import com.mtk.color_card_game.service.PrizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrizeServiceImpl implements PrizeService {

    private final PrizeRepo repo;

    @Override
    public List<Prize> getAllPrize() {
        log.info("Getting All Prizes. Prizes {}", repo.findAll().size());
        return repo.findAll();
    }

    @Override
    public List<Prize> getAvailable() {
        log.info("Getting All Prizes Available. Prizes Available {}", repo.findByQuantityGreaterThan(0).size());
        return repo.findByQuantityGreaterThan(0);
    }
}
