package com.mtk.color_card_game.controller;

import com.mtk.color_card_game.common.response.ResponseFactory;
import com.mtk.color_card_game.entity.NormalDailyPrize;
import com.mtk.color_card_game.entity.VipDailyPrize;
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

    @GetMapping("/normal/by-prize-id")
    public ResponseEntity<?> handleGetByPrizeIdNormal(@RequestParam int day, @RequestParam long id) {
        List<NormalDailyPrize> prizeIdNormal = service.getByDayAndPrizeIdNormal(day, id);

        return factory.buildSuccess(
                HttpStatus.OK,
                prizeIdNormal,
                "200",
                "Day [" + day + "] Normal Prizes With Id " + id
        );
    }

    @GetMapping("/normal/by-rank")
    public ResponseEntity<?> handleGetByRankNormal(@RequestParam int day, @RequestParam int rank) {
        List<NormalDailyPrize> prizeRankNormal = service.getByDayAndPrizeRankNormal(day, rank);

        return factory.buildSuccess(
                HttpStatus.OK,
                prizeRankNormal,
                "200",
                "Day [" + day + "] Normal Prizes With Rank " + rank
        );
    }

    @GetMapping("/vip/by-prize-id")
    public ResponseEntity<?> handleGetByPrizeIdVip(@RequestParam int day, @RequestParam long id) {
        List<VipDailyPrize> prizeIdVip = service.getByDayAndPrizeIdVip(day, id);

        return factory.buildSuccess(
                HttpStatus.OK,
                prizeIdVip,
                "200",
                "Day [" + day + "] Vip Prizes With Id " + id
        );
    }

    @GetMapping("/vip/by-rank")
    public ResponseEntity<?> handleGetByRankVip(@RequestParam int day, @RequestParam int rank) {
        List<VipDailyPrize> prizeRankVip = service.getByDayAndPrizeRankVip(day, rank);

        return factory.buildSuccess(
                HttpStatus.OK,
                prizeRankVip,
                "200",
                "Day [" + day + "] Vip Prizes With Rank " + rank
        );
    }
}
