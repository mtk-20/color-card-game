package com.mtk.color_card_game.service;

import com.mtk.color_card_game.dto.GameRequest;
import com.mtk.color_card_game.dto.GameResponse;

public interface GameService {

    GameResponse vipMode(GameRequest request);

    GameResponse normalMode(GameRequest request);
}
