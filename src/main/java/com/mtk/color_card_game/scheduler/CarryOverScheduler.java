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

        for (int day = 1; day <= 3; day++) {
            List<NormalDailyPrize> todayLimitsN = normalDailyPrizeRepo.findByDay(day);
            List<NormalDailyPrize> nextDayLimitsN = normalDailyPrizeRepo.findByDay(day + 1);

            List<VipDailyPrize> todayLimitsV = vipDailyPrizeRepo.findByDay(day);
            List<VipDailyPrize> nextDayLimitsV = vipDailyPrizeRepo.findByDay(day + 1);

            for (NormalDailyPrize todayN : todayLimitsN) {
                if (todayN.getAvailableQuantity() <= 0) {
                    continue;
                }

                NormalDailyPrize nextDayN = nextDayLimitsN.stream()
                        .filter(q -> q.getPrize().getId().equals(todayN.getPrize().getId()))
                        .findFirst().orElse(null);

                if (nextDayN != null) {
                    nextDayN.setAvailableQuantity(nextDayN.getAvailableQuantity() + todayN.getAvailableQuantity());
                    normalDailyPrizeRepo.save(nextDayN);

                    log.info("Carried over {} units of Normal Prize ID {} from Day {} to Day {}", todayN.getAvailableQuantity(), todayN.getPrize().getId(), day, day + 1);

                    todayN.setAvailableQuantity(0);
                    normalDailyPrizeRepo.save(todayN);
                } else {
                    log.warn("Normal Prize Name {} found on Day {} but not on Day {}", todayN.getPrize().getPrizeName(), day, day + 1);
                }
            }

            for (VipDailyPrize todayV : todayLimitsV) {
                if (todayV.getAvailableQuantity() <= 0) {
                    continue;
                }

                VipDailyPrize nextDayV = nextDayLimitsV.stream()
                        .filter(q -> q.getPrize().getId().equals(todayV.getPrize().getId()))
                        .findFirst().orElse(null);

                if (nextDayV != null) {
                    nextDayV.setAvailableQuantity(nextDayV.getAvailableQuantity() + todayV.getAvailableQuantity());
                    vipDailyPrizeRepo.save(nextDayV);

                    log.info("Carried over {} units of Vip Prize ID {} from Day {} to Day {}", todayV.getAvailableQuantity(), todayV.getPrize().getId(), day, day + 1);

                    todayV.setAvailableQuantity(0);
                    vipDailyPrizeRepo.save(todayV);
                } else {
                    log.warn("Vip Prize Name {} found on Day {} but not on Day {}", todayV.getPrize().getPrizeName(), day, day + 1);
                }
            }
        }
    }
}

