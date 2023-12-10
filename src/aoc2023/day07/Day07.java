package aoc2023.day07;

import aoc2023.Day;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07 extends Day {
    public Day07() {
        super("src/aoc2023/Day07/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        List<CardHand> hands = lines.map(line -> {
            String[] parts = line.split(" ");
            List<Card> hand = Arrays.stream(parts[0].split("")).map(Card::fromString).toList();
            int bid = Integer.parseInt(parts[1]);

            return new CardHand(bid, hand);
        }).sorted().toList();

        int totalWinnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            totalWinnings += hands.get(i).bid * (i + 1);
        }

        return totalWinnings;
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }

    enum Card {
        ACE(14),
        KING(13),
        QUEEN(12),
        JACK(11),
        TEN(10),
        NINE(9),
        EIGHT(8),
        SEVEN(7),
        SIX(6),
        FIVE(5),
        FOUR(4),
        THREE(3),
        TWO(2);

        private Integer value;

        Card(int value) {
            this.value = value;
        }

        static Card fromString(String string){
            switch (string) {
                case "A" -> {
                    return ACE;
                }
                case "K" -> {
                    return KING;
                }
                case "Q" ->  {
                    return QUEEN;
                } case "J" -> {
                    return JACK;
                } case "T"-> {
                    return TEN;
                } case "9"-> {
                    return NINE;
                } case "8"-> {
                    return EIGHT;
                } case "7"-> {
                    return SEVEN;
                } case "6"-> {
                    return SIX;
                } case "5"-> {
                    return FIVE;
                } case "4"-> {
                    return FOUR;
                } case "3"-> {
                    return THREE;
                } case "2"-> {
                    return TWO;
                }
            }

            return null;
        }
    }

    enum HandType {
        HIGH_CARD(1),
        ONE_PAIR(2),
        TWO_PAIR(3),
        THREE_OF_A_KIND(4),
        FULL_HOUSE(5),
        FOUR_OF_A_KIND(6),
        FIVE_OF_A_KIND(7);

        private final int mRank;

        HandType(int rank) {
            mRank = rank;
        }

        public int getRank() {
            return mRank;
        }
    }

    static class CardHand implements Comparable<CardHand> {
        private final int bid;

        private final List<Card> cards;


        CardHand(int bid, List<Card> cards) {
            this.bid = bid;
            this.cards = cards;
        }

        public Card getCard(int i) {
            return cards.get(i);
        }

        public List<Card> getCards() {
            return cards;
        }

        HandType getType() {
            Map<Card, Integer> counts = new HashMap<>();
            for (Card card :
                    cards) {
                counts.merge(card, 1, Integer::sum);
            }

            List<Integer> values = counts.values().stream().toList();

            if (values.contains(5)) {
                return HandType.FIVE_OF_A_KIND;
            } else if (values.contains(4)) {
                return HandType.FOUR_OF_A_KIND;
            } else if (values.size() == 2 && values.contains(3) && values.contains(2)) {
                return HandType.FULL_HOUSE;
            } else if (values.size() == 3 &&  values.contains(3)) {
                return HandType.THREE_OF_A_KIND;
            } else if (values.size() == 3 && values.contains(2)) {
                return HandType.TWO_PAIR;
            } else if (values.size() == 4 && values.contains(2)) {
                return HandType.ONE_PAIR;
            } else {
              return HandType.HIGH_CARD;
            }
        }

        @Override
        public String toString() {
            return "CardHand{" +
                    "bid=" + bid +
                    ", cards=" + cards +
                    ", type= " + getType() +
                    '}';
        }

        @Override
        public int compareTo(CardHand o) {
            HandType hand1 = getType();
            HandType hand2 = o.getType();

            if (hand1 != hand2) {
                return hand1.compareTo(hand2);
            }

            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).equals(o.getCards().get(i))) {
                    continue;
                }

                return getCard(i).value.compareTo(o.getCard(i).value);
            }

            return 0;
        }
    }
}
