package GameVariants;

import CardModels.Card;
import CardModels.CardSymbol;
import Models.Player;

public class RummyArgentino extends RummyStandard {

    @Override
    public int countPoints(Card card){
        int LOW_POINTS = 5;
        int A_POINTS = 15;
        int MIN_CARD_HIGH_POINTS = 8;
        int JOKER_POINTS = 20;
        int HIGH_POINTS = 10;

        if (card.getCardSymbol() != CardSymbol.JOKER) {
            if (card.getCardNumber().getNumber() < MIN_CARD_HIGH_POINTS) {
                if (card.getCardNumber().getNumber() == 1){
                    return A_POINTS;
                }
                return LOW_POINTS;
            }
            return HIGH_POINTS;
        }
        return JOKER_POINTS;
    }

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
}
