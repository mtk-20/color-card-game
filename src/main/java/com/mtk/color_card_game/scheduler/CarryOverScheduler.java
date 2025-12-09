package com.mtk.color_card_game.scheduler;

import com.mtk.color_card_game.entity.NormalDailyPrize;
import com.mtk.color_card_game.entity.VipDailyPrize;
import com.mtk.color_card_game.repo.NormalDailyPrizeRepo;
import com.mtk.color_card_game.repo.VipDailyPrizeRepo;
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

    private final NormalDailyPrizeRepo normalDailyPrizeRepo;
    private final VipDailyPrizeRepo vipDailyPrizeRepo;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void carryOverRemainingNormalPrizes() {

        for (int day = 1; day <= 2; day++) {
            List<NormalDailyPrize> todayLimits = normalDailyPrizeRepo.findByDay(day);
            List<NormalDailyPrize> nextDayLimits = normalDailyPrizeRepo.findByDay(day + 1);

            for (NormalDailyPrize qToday : todayLimits) {
                if (qToday.getAvailableQuantity() <= 0) {
                    continue;
                }

                NormalDailyPrize nextDay = nextDayLimits.stream()
                        .filter(q -> q.getPrize().getId().equals(qToday.getPrize().getId()))
                        .findFirst().orElse(null);

                if (nextDay != null) {
                    nextDay.setAvailableQuantity(nextDay.getAvailableQuantity() + qToday.getAvailableQuantity());
                    normalDailyPrizeRepo.save(nextDay);

                    qToday.setAvailableQuantity(0);
                    normalDailyPrizeRepo.save(qToday);

                    log.info("Carried over {} units of Prize ID {} from Day {} to Day {}",
                            qToday.getAvailableQuantity(), qToday.getPrize().getId(), day, day + 1);
                } else {
                    log.warn("Prize Name {} found on Day {} but not on Day {}",
                            qToday.getPrize().getPrizeName(), day, day + 1);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void carryOverRemainingVipPrizes() {

        for (int day = 1; day <= 2; day++) {
            List<VipDailyPrize> todayLimits = vipDailyPrizeRepo.findByDay(day);
            List<VipDailyPrize> nextDayLimits = vipDailyPrizeRepo.findByDay(day + 1);

            for (VipDailyPrize qToday : todayLimits) {
                if (qToday.getAvailableQuantity() <= 0) {
                    continue;
                }

                VipDailyPrize nextDay = nextDayLimits.stream()
                        .filter(q -> q.getPrize().getId().equals(qToday.getPrize().getId()))
                        .findFirst().orElse(null);

                if (nextDay != null) {
                    nextDay.setAvailableQuantity(nextDay.getAvailableQuantity() + qToday.getAvailableQuantity());
                    vipDailyPrizeRepo.save(nextDay);

                    qToday.setAvailableQuantity(0);
                    vipDailyPrizeRepo.save(qToday);

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

