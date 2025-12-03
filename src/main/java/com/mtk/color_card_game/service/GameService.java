package com.mtk.color_card_game.service;

import com.mtk.color_card_game.dto.GameResponse;

public interface GameService {

    GameResponse playGame(String colorStr);
}
