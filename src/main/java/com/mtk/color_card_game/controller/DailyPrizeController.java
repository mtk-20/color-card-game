package com.mtk.color_card_game.controller;

import com.mtk.color_card_game.common.response.ResponseFactory;
import com.mtk.color_card_game.entity.DailyPrize;
import com.mtk.color_card_game.service.DailyPrizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily-prize")
public class DailyPrizeController {

    private final DailyPrizeService service;
    private final ResponseFactory factory;

    @GetMapping("/by-prize-id")
    public ResponseEntity<?> handleGetByPrizeId(@RequestParam int day, @RequestParam long id) {
        List<DailyPrize> byDayAndPrizeId = service.getByDayAndPrizeId(day, id);

        return factory.buildSuccess(
                HttpStatus.OK,
                byDayAndPrizeId,
                "200",
                "Day [" + day + "] Prizes With Id " + id
        );
    }

    @GetMapping("/by-rank")
    public ResponseEntity<?> handleGetByRank(@RequestParam int day, @RequestParam int rank) {
        List<DailyPrize> byDayAndPrizeRank = service.getByDayAndPrizeRank(day, rank);

        return factory.buildSuccess(
                HttpStatus.OK,
                byDayAndPrizeRank,
                "200",
                "Day [" + day + "] Prizes With Rank " + rank
        );
    }
}
