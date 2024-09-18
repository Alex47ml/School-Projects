package GameVariants;

import CardModels.Card;
import Models.Player;

public interface Rummy {
    void setSetup();
    void gameSet();
    void startGame();
    void giveCardsToPlayer(int playerNumber);
    int countPointsOfPlayer(Player player);
    int countPoints(Card card);
}
