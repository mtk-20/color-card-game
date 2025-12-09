package com.mtk.color_card_game.service.impl;

import com.mtk.color_card_game.common.exception.CommonException;
import com.mtk.color_card_game.dto.GameRequest;
import com.mtk.color_card_game.dto.GameResponse;
import com.mtk.color_card_game.entity.DailyPrize;
import com.mtk.color_card_game.entity.Prize;
import com.mtk.color_card_game.repo.DailyPrizeRepo;
import com.mtk.color_card_game.repo.PrizeRepo;
import com.mtk.color_card_game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    @Value("${event.start.date}")
    private String eventStartDateString;

    @Value("${event.duration.days}")
    private int eventDuration;

    private final PrizeRepo prizeRepo;
    private final DailyPrizeRepo dailyPrizeRepo;

    private final Random random = new Random();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private enum Color {
        ORANGE, GREEN, BLUE
    }

    @Override
    @Transactional
    public GameResponse playGame(GameRequest request) {

        LocalDate day = LocalDate.now();
        int dayNo = getEventDay(day);

        if (dayNo == -1) {
            log.info("Event Completed.");
            throw new CommonException("ERR_404", "The event is currently not running.");
        }

        List<DailyPrize> prizes = dailyPrizeRepo.findByDay(dayNo);
        List<DailyPrize> availablePrizes = prizes.stream().filter(a -> a.getAvailableQuantity() > 0).toList();

        if (availablePrizes.isEmpty()) {
            log.info("No Prizes For Today.");
            throw new CommonException("ERR_404", "No prizes available for today.");
        }

        Color userColor;
        try {
            userColor = Color.valueOf(request.getColor().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid color. Choose ORANGE, GREEN, or BLUE.");
        }

        boolean is311 = random.nextBoolean();
        String patternType;

        List<Color> showCards = new ArrayList<>();
        List<Color> allColorCards = new ArrayList<>(Arrays.asList(Color.ORANGE, Color.BLUE, Color.GREEN));

        Collections.shuffle(allColorCards, random);

        if (is311) {
            patternType = "3-1-1";

            Color three = allColorCards.get(0);
            Color one = allColorCards.get(1);
            Color otherOne = allColorCards.get(2);

            showCards.addAll(Collections.nCopies(3, three));
            showCards.add(one);
            showCards.add(otherOne);
        } else {
            patternType = "2-2-1";

            Color one = allColorCards.get(0);
            Color two = allColorCards.get(1);
            Color otherTwo = allColorCards.get(2);

            showCards.add(one);
            showCards.addAll(Collections.nCopies(2, two));
            showCards.addAll(Collections.nCopies(2, otherTwo));
        }
        Collections.shuffle(showCards, random);

        long count = showCards.stream().filter(s -> s == userColor).count();
        int prizeRank;

        if (count == 3) {
            prizeRank = 1;
        } else if (count == 2) {
            prizeRank = 2;
        } else {
            prizeRank = 3;
        }

        DailyPrize chosenPrize = pickRandomPrize(availablePrizes, prizeRank);
        chosenPrize.setAvailableQuantity(chosenPrize.getAvailableQuantity() - 1);

        Prize prize = chosenPrize.getPrize();
        prize.setQuantity(prize.getQuantity() - 1);

        prizeRepo.save(prize);
        dailyPrizeRepo.save(chosenPrize);

        String description = "Congratulations! You won Rank [" + prizeRank + "] Prize. Prize ID: ["
                + chosenPrize.getPrize().getId() + "]. " + "Prize Name: "
                + chosenPrize.getPrize().getPrizeName();

        return new GameResponse(
                patternType,
                showCards.stream().map(Enum::name).toList(),
                prizeRank,
                description
        );
    }

    private DailyPrize pickRandomPrize(List<DailyPrize> prizes, int rank) {

        List<DailyPrize> filtered = prizes.stream()
                .filter(p -> p.getPrize().getPrizeRank() == rank)
                .filter(p -> p.getAvailableQuantity() > 0).toList();

        if (filtered.isEmpty()) {
            throw new CommonException("ERR_500", "No Prize Left For Rank " + rank);
        }

        int dropRate = filtered.stream().mapToInt(DailyPrize::getAvailableQuantity).sum();
        int randomNumber = random.nextInt(dropRate) + 1;
        int cumulative = 0;

        for (DailyPrize prize : filtered) {
            cumulative += prize.getAvailableQuantity();
            if (randomNumber <= cumulative) {
                return prize;
            }
        }
        throw new RuntimeException("Random Number Generator Failed.");
    }

    private int getEventDay(LocalDate day) {
        LocalDate eventStartDate = LocalDate.parse(eventStartDateString, DATE_FORMATTER);
        if (day.isBefore(eventStartDate)) {
            return -1;
        }

        // 0
        long daysBetween = ChronoUnit.DAYS.between(eventStartDate, day);

        int currentEventDay = (int) daysBetween + 1;

        if (currentEventDay >= 1 && currentEventDay <= eventDuration) {
            return currentEventDay;
        }

        return -1;
    }
}
