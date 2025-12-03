package com.mtk.color_card_game.service.impl;

import com.mtk.color_card_game.dto.GameResponse;
import com.mtk.color_card_game.repo.DailyPrizeRepo;
import com.mtk.color_card_game.repo.PrizeRepo;
import com.mtk.color_card_game.repo.UserRepo;
import com.mtk.color_card_game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final UserRepo userRepo;
    private final PrizeRepo prizeRepo;
    private final DailyPrizeRepo dailyPrizeRepo;

    @Override
    public GameResponse playGame(String colorStr) {
        return null;
    }
}
