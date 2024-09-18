package GameVariants;

import CardModels.Card;
import CardModels.CardSymbol;
import CombinationModels.Combination;
import CombinationModels.SameNumberCombination;
import CombinationModels.StairsCombination;
import DeckFactory.DeckCreation;
import Logic.Actions;
import Models.Deck;
import Models.Player;
import Models.Table;
import Print.UserInsert;
import Print.View;

import java.util.ArrayList;

public abstract class RummyStandard implements Rummy{
    protected ArrayList<Player> players = new ArrayList<Player>();
    private Table table = new Table();
    private Deck mainDeck = new Deck();
    private int turn = 0;
    private final View vista = new View();
    private final UserInsert insert = new UserInsert();
    private static final int POINTS_WIN = 101;

    public void setSetup(){
        DeckCreation deckCreation = new DeckCreation();
        boolean doubleDeck = false;
        if (insert.typeOfDeckGame() == Games.DoubleDeckGame){
            doubleDeck = true;
        }
        mainDeck = deckCreation.create(Games.RummyClassic,doubleDeck);
        table = new Table();
        int BEGINNING_CARDS_QUANT = 14;
        for (int i = 0; i < players.size(); i++) {
            while (players.get(i).getHand().getDeck().size() < BEGINNING_CARDS_QUANT){
                giveCardsToPlayer(i);
            }
        }
    }

    public void gameSet() {
        Table clonedTable = new Table();
        Player clonedPlayer = new Player("");

        setSetup();
        boolean setFinished = false;
        for (Player pl : players) {
            vista.printPlayerWithPoints(pl);
        }
        while (!setFinished) {
            boolean turnFinished = false;
            boolean addedCard = false;
            vista.printPlayerDeckAndTable(table, players.get(turn % players.size()));
            if (insert.beginTurnOption() == Actions.STOP) {
                giveCardsToPlayer(turn % players.size());
            } else {
                while (!turnFinished) {
                    if (!players.get(turn % players.size()).getHasMadeFirstTurn()) {
                        clonedPlayer = clonePlayer(players.get(turn % players.size()));
                        clonedTable = cloneTable(table);
                    }
                    if (table.getTable().isEmpty()) {
                        vista.firstTurnOption();
                        addedCard = addCombinationToTable();
                    } else {
                        switch (insert.moveChoice()) {
                            case Actions.CREATE_NEW_COMBO:
                                addedCard = addCombinationToTable();
                                break;
                            case Actions.ADD_CARD_TO_COMBO:
                                addedCard = addCardsOnTable();
                                break;
                            case Actions.SWAP_CARD:
                                addedCard = swapCardPlayerTable();
                                break;
                        }
                    }
                    if (addedCard) {
                        int MIN_POINTS_FIRST_TURN = 30;
                        if (!players.get(turn % players.size()).getHasMadeFirstTurn()
                                && (countPointsOnTable(this.table) - countPointsOnTable(clonedTable)) < MIN_POINTS_FIRST_TURN) {
                            vista.firstTurnIllegalPoints();
                            table = clonedTable;
                            players.set(turn % players.size(), clonedPlayer);
                        } else if (!players.get(turn % players.size()).getHasMadeFirstTurn()) {
                            players.get(turn % players.size()).setHasMadeFirstTurn(true);
                        }
                        if (insert.turnOption() == Actions.STOP) {
                            turnFinished = true;
                        }
                    } else {
                        if (insert.badTurnDone() == Actions.STOP) {
                            giveCardsToPlayer(turn % players.size());
                        }
                    }
                }
            }
            if (players.get(turn % players.size()).getHand().getDeck().isEmpty()) {
                setFinished = true;
            } else {
                turn++;
            }
        }
        vista.setWinScenario(players.get(turn % players.size()).getName());
        addPointsToPlayers(players.get(turn % players.size()));
    }

    public void startGame(){
        boolean gameFinished = false;
        int playQuant = insert.playersQuantity();
        insert.a();
        for (int i = 0; i < playQuant; i++) {
            String playName = insert.userName(i + 1);
            players.add(new Player(playName));
        }
        while (!gameFinished){
            gameSet();
            for (Player player : players) {
                if (player.getPoints() > POINTS_WIN) {
                    gameFinished = true;
                    break;
                }
            }
        }
        vista.gameWinScenario(players.get(turn%players.size()).getName());
    }

    public void giveCardsToPlayer(int playerNumber){
        Player player = players.get(playerNumber);
        player.getHand().addCard(mainDeck.removeCard(mainDeck.getDeck().size() - 1));
    }

    public boolean addCardsOnTable(){
        Player player = players.get(turn% players.size());
        vista.printPlayerDeckAndTable(table,player);
        int cardToBeRemoved = insert.cardChoice();
        Combination comb = table.getTable().get(insert.combinationChoice());
        if (comb.addCard(player.getHand().getDeck().get(cardToBeRemoved))){
            player.getHand().removeCard(cardToBeRemoved);
            return true;
        } else {
            return false;
        }
    }

    public boolean addCombinationToTable(){
        Player player = players.get(turn % players.size());
        vista.printTable(table);
        ArrayList<Card> cardsToBeRemoved = new ArrayList<Card>();
        boolean finishedAdding = false;
        int MINIMAL_CARDS_FOR_COMB = 3;
        for (int i = 0; i < MINIMAL_CARDS_FOR_COMB; i++) {
            vista.printCards(player.getHand());
            cardsToBeRemoved.add(player.getHand().getDeck().get(insert.cardChoice()));
            if (cardsToBeRemoved.getLast().getCardSymbol() == CardSymbol.JOKER) {
                cardsToBeRemoved.getLast().setCardNumber(insert.chooseJokerValue());
            }
        }
        while (!finishedAdding) {
            if (insert.stopAction() == Actions.STOP) {
                finishedAdding = true;
            } else {
                vista.printCards(player.getHand());
                cardsToBeRemoved.add(player.getHand().getDeck().get(insert.cardChoice()));
            }
        }
        Combination comb;
        comb = new SameNumberCombination();
        if (!comb.createCombination(cardsToBeRemoved)){
            comb = new StairsCombination();
            if (!comb.createCombination(cardsToBeRemoved)){
                return false;
            }
        }
        for (Card card : cardsToBeRemoved) {
            player.getHand().getDeck().remove(card);
        }
        table.addCombination(comb);
        return true;
    }

    public boolean swapCardPlayerTable(){
        Player player = players.get(turn % players.size());
        vista.printPlayerDeckAndTable(table,player);

        Table tableReset = cloneTable(table);
        Player playerReset = clonePlayer(player);

        Card playerCardToBeSwap = player.getHand().getDeck().get(insert.cardChoice());
        vista.printTable(table);
        Combination comb = table.getTable().get(insert.combinationChoice());
        Card tableCardToBeSwap = comb.getCombination().get(insert.cardChoice());
        comb.swapCard(playerCardToBeSwap,tableCardToBeSwap);
        player.getHand().getDeck().remove(playerCardToBeSwap);
        player.getHand().addCard(tableCardToBeSwap);

        boolean turnDoneGood = true;

        while (player.getHand().getDeck().contains(tableCardToBeSwap) && turnDoneGood){
            switch (insert.moveChoice()){
                case Actions.CREATE_NEW_COMBO:
                    turnDoneGood = addCombinationToTable();
                    break;
                case Actions.ADD_CARD_TO_COMBO:
                    turnDoneGood = addCardsOnTable();
                    break;
                case Actions.SWAP_CARD:
                    turnDoneGood = swapCardPlayerTable();
                    break;
            }
        }
        if (!turnDoneGood){
            table = tableReset;
            players.set(turn % players.size(),playerReset);
        }

        return turnDoneGood;
    }


    public Table cloneTable(Table table){
        Table clonedTable = new Table();
        for (Combination comb : table.getTable()){
            Combination clonedComb;
            if (comb.getClass() != SameNumberCombination.class){
                clonedComb = new StairsCombination();
            } else {
                clonedComb = new SameNumberCombination();
            }
            ArrayList<Card> cardList = new ArrayList<>(comb.getCombination());
            clonedComb.createCombination(cardList);
            clonedTable.addCombination(clonedComb);
        }
        return clonedTable;
    }

    public Player clonePlayer(Player player){
        Player clonedPlayer = new Player(player.getName());
        for (Card card : player.getHand().getDeck()){
            clonedPlayer.getHand().addCard(card);
        }
        return clonedPlayer;
    }

    public int countPointsOnTable(Table table){
        int points = 0;
        for (Combination comb : table.getTable()){
            for (Card card : comb.getCombination()){
                points += countPoints(card);
            }
        }
        return points;
    }


    public int countPointsOfPlayer(Player player){
        int points = 0;
        if (!player.getHand().getDeck().isEmpty()) {
            for (int j = 0; j < player.getHand().getDeck().size(); j++) {
                points += countPoints(player.getHand().getDeck().get(j));
            }
        }
        return points;
    }

    public abstract void addPointsToPlayers(Player roundWinner);
    public abstract int countPoints(Card card);
}
