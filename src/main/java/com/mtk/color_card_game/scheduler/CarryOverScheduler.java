package com.mtk.color_card_game.scheduler;


import com.mtk.color_card_game.entity.DailyPrize;
import com.mtk.color_card_game.repo.DailyPrizeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CarryOverScheduler {

    private final DailyPrizeRepo repo;

    @Scheduled(cron = "0 0 0 * * ?")
    public void carryOverRemainingPrizes() {
        for (int day = 1; day <= 2; day++) {
            List<DailyPrize> todayLimits = repo.findByDay(day);
            List<DailyPrize> nextDayLimits = repo.findByDay(day + 1);
            for (DailyPrize qToday : todayLimits) {
                DailyPrize nextDay = nextDayLimits.stream()
                        .filter(q -> q.getPrize().getId().equals(qToday.getPrize().getId()))
                        .findFirst().orElse(null);
                if (nextDay != null) {
                    nextDay.setAvailableQuantity(nextDay.getAvailableQuantity() + qToday.getAvailableQuantity());
                    repo.save(nextDay);
                }
                qToday.setAvailableQuantity(0);
                repo.save(qToday);
            }
        }
    }
}

