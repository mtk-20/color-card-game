package com.mtk.color_card_game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameRequest {

    private String userId;
    private String color;
}
