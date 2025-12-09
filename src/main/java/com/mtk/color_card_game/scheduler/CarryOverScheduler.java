package com.mtk.color_card_game.scheduler;

import com.mtk.color_card_game.entity.DailyPrize;
import com.mtk.color_card_game.repo.DailyPrizeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarryOverScheduler {

    private final DailyPrizeRepo repo;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void carryOverRemainingPrizes() {

        for (int day = 1; day <= 2; day++) {
            List<DailyPrize> todayLimits = repo.findByDay(day);
            List<DailyPrize> nextDayLimits = repo.findByDay(day + 1);

            for (DailyPrize qToday : todayLimits) {
                if (qToday.getAvailableQuantity() <= 0) {
                    continue;
                }

                DailyPrize nextDay = nextDayLimits.stream()
                        .filter(q -> q.getPrize().getId().equals(qToday.getPrize().getId()))
                        .findFirst().orElse(null);

                if (nextDay != null) {
                    nextDay.setAvailableQuantity(nextDay.getAvailableQuantity() + qToday.getAvailableQuantity());
                    repo.save(nextDay);

                    qToday.setAvailableQuantity(0);
                    repo.save(qToday);

                    log.info("Carried over {} units of Prize ID {} from Day {} to Day {}",
                            qToday.getAvailableQuantity(), qToday.getPrize().getId(), day, day + 1);
                } else {
                    log.warn("Prize Name {} found on Day {} but not on Day {}",
                            qToday.getPrize().getPrizeName(), day, day + 1);
                }
            }
        }
    }
}

