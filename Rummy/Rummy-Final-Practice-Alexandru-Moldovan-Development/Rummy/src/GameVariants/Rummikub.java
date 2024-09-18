package GameVariants;

import CardModels.Card;
import CardModels.CardSymbol;
import Models.Player;

public class Rummikub extends RummyStandard {

    @Override
    public int countPoints(Card card){
        int COURT_POINTS = 10;
        int JOKER_POINTS = 15;
        int COURT_MIN_NUMBER= 11;
        if (card.getCardSymbol() != CardSymbol.JOKER) {
            if (card.getCardNumber().getNumber() <= COURT_MIN_NUMBER) {
                return card.getCardNumber().getNumber();
            } else {
                return COURT_POINTS;
            }
        } else {
            return JOKER_POINTS;
        }
    }

    public void addPointsToPlayers(Player roundWinner){
        int totalPoints = 0;
        for (Player player: players) {
            if (player != roundWinner) {
                int points = countPointsOfPlayer(player);
                player.addPoints(-1 * points);
                totalPoints += points;
            }
        }
        roundWinner.addPoints(totalPoints);
    }
}
