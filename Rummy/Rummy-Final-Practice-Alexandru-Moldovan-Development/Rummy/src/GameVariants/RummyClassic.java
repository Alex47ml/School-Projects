package GameVariants;

import CardModels.Card;
import CardModels.CardSymbol;
import Models.Player;

public class RummyClassic extends RummyStandard {

    @Override
    public void addPointsToPlayers(Player roundWinner){
        int totalPoints = 0;
        for (Player player: players) {
            if (player != roundWinner) {
                int points = countPointsOfPlayer(player);
                totalPoints += points;
            }
        }
        roundWinner.addPoints(totalPoints);
    }

    @Override
    public int countPoints(Card card){
        int LOW_POINTS = 5;
        int MIN_CARD_HIGH_POINTS = 8;
        int JOKER_POINTS = 15;
        int HIGH_POINTS = 10;

        if (card.getCardSymbol() != CardSymbol.JOKER) {
            if (card.getCardNumber().getNumber() < MIN_CARD_HIGH_POINTS) {
                return LOW_POINTS;
            } else {
                return HIGH_POINTS;
            }
        } else {
            return JOKER_POINTS;
        }
    }
}
