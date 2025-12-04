package com.mtk.color_card_game.service.impl;

import com.mtk.color_card_game.dto.GameRequest;
import com.mtk.color_card_game.dto.GameResponse;
import com.mtk.color_card_game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    /*private final UserRepo userRepo;
    private final PrizeRepo prizeRepo;
    private final DailyPrizeRepo dailyPrizeRepo;*/

    private final SecureRandom random = new SecureRandom();

    private enum Color {
        ORANGE, GREEN, BLUE
    }

    @Override
    public GameResponse playGame(GameRequest request) {

        Color userColor;
        try {
            userColor = Color.valueOf(request.color().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid color. Choose ORANGE, GREEN, or BLUE.");
        }

        boolean is311 = random.nextBoolean();
        String patternTYpe;

        List<Color> showCards = new ArrayList<>();
        List<Color> allColorCards = new ArrayList<>(Arrays.asList(Color.ORANGE, Color.BLUE, Color.GREEN));

        Collections.shuffle(allColorCards, random);

        if (is311) {
            patternTYpe = "3-1-1";

            Color three = allColorCards.get(0);
            Color one = allColorCards.get(1);
            Color otherOne = allColorCards.get(2);

            showCards.addAll(Collections.nCopies(3, three));
            showCards.add(one);
            showCards.add(otherOne);
        } else {
            patternTYpe = "2-1-1";

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
        String description;

        if (count == 3) {
            prizeRank = 1;
            description = "Congratulation. You Have Won A RANK [1] Prize.";
        } else if (count == 2) {
            prizeRank = 2;
            description = "Congratulation. You Have Won A RANK [2] Prize.";
        } else {
            prizeRank = 3;
            description = "Congratulation. You Have Won A RANK [3] Prize.";
        }

        return new GameResponse(
                patternTYpe,
                showCards.stream().map(Enum::name).collect(Collectors.toList()),
                prizeRank,
                description);
    }
}
