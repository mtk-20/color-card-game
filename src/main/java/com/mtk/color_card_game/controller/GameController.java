package com.mtk.color_card_game.controller;

import com.mtk.color_card_game.dto.GameRequest;
import com.mtk.color_card_game.dto.GameResponse;
import com.mtk.color_card_game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/play")
public class GameController {

    private final GameService service;

    @PostMapping("/normal-mode")
    public ResponseEntity<?> handleNormalMode(@RequestBody GameRequest request) {
        GameResponse response = service.normalMode(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/vip-mode")
    public ResponseEntity<?> handleVipMode(@RequestBody GameRequest request) {
        GameResponse response = service.vipMode(request);
        return ResponseEntity.ok(response);
    }
}
