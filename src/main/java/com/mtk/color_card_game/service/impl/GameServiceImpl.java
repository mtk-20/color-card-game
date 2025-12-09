package com.mtk.color_card_game.service.impl;

import com.mtk.color_card_game.common.exception.CommonException;
import com.mtk.color_card_game.dto.GameRequest;
import com.mtk.color_card_game.dto.GameResponse;
import com.mtk.color_card_game.entity.DailyPrize;
import com.mtk.color_card_game.entity.Prize;
import com.mtk.color_card_game.repo.DailyPrizeRepo;
import com.mtk.color_card_game.repo.PrizeRepo;
import com.mtk.color_card_game.repo.UserRepo;
import com.mtk.color_card_game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final UserRepo userRepo;
    private final PrizeRepo prizeRepo;
    private final DailyPrizeRepo dailyPrizeRepo;

    private final Random random = new Random();

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
            throw new CommonException("ERR_404", "No prizes are available for today's game.");
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

        return filtered.get(random.nextInt(filtered.size()));
    }

    private int getEventDay(LocalDate day) {
        LocalDate day1 = LocalDate.of(2025, 12, 8);
        LocalDate day2 = day1.plusDays(1);
        LocalDate day3 = day2.plusDays(1);

        if (day.isEqual(day1))
            return 1;

        if (day.isEqual(day2))
            return 2;

        if (day.isEqual(day3))
            return 3;

        return -1;
    }
}
