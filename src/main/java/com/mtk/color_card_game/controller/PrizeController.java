package com.mtk.color_card_game.controller;

import com.mtk.color_card_game.common.response.ResponseFactory;
import com.mtk.color_card_game.entity.Prize;
import com.mtk.color_card_game.service.PrizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prize")
public class PrizeController {

    private final PrizeService service;
    private final ResponseFactory factory;

    @GetMapping("/all")
    public ResponseEntity<?> handleGetAll() {
        List<Prize> prizes = service.getAllPrize();

        return factory.buildSuccess(
                HttpStatus.OK,
                prizes,
                "200",
                "All Prizes Listed."
        );
    }

    @GetMapping("/available")
    public ResponseEntity<?> handleGetAvailable() {
        List<Prize> available = service.getAvailable();

        return factory.buildSuccess(
                HttpStatus.OK,
                available,
                "200",
                "Available Prizes Listed."
        );
    }
}
