package com.mtk.color_card_game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GameResponse {

   private String pattern;
   private List<String> colorCards;
   private int rank;
   private String description;

}
