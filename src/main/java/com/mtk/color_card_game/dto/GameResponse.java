package com.mtk.color_card_game.dto;

import java.util.List;

public record GameResponse(String pattern, List<String> colorCards, int rank) {
}
