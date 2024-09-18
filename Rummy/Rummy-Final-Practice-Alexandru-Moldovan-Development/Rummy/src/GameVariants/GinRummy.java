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

public class GinRummy implements Rummy{
    private ArrayList<Player> players = new ArrayList<Player>();
    private Table table = new Table();
    private Deck mainDeck = new Deck();
    private Deck discardedCards = new Deck();
    private int turn = 0;
    private final View vista = new View();
    private final UserInsert insert = new UserInsert();

    public void setSetup() {
        DeckCreation deckCreation = new DeckCreation();
        boolean doubleDeck = insert.typeOfDeckGame() == Games.DoubleDeckGame;
        mainDeck = deckCreation.create(Games.GinRummy,doubleDeck);
        int BEGINNING_CARDS_QUANT = 10;
        for (int i = 0; i < players.size(); i++) {
            while (players.get(i).getHand().getDeck().size() < BEGINNING_CARDS_QUANT) {
                giveCardsToPlayer(i);
            }
        }
    }

    public void gameSet() {
        setSetup();
        boolean gameFinishedViaKnock = false;
        boolean gameFinishedViaGin = false;
        while (!gameFinishedViaGin && !gameFinishedViaKnock) {
            vista.printCards(players.get(turn % players.size()).getHand());
            boolean turnDone = false;
            while (!turnDone){
                turnDone = switch (insert.moveChoiceGinRummy()) {
                    case Actions.GIN -> {
                        gameFinishedViaGin = ginMove();
                        yield gameFinishedViaGin;
                    }
                    case Actions.KNOCK -> {
                        gameFinishedViaKnock = knockMove();
                        yield gameFinishedViaKnock;
                    }
                    case Actions.STOP -> {
                        giveCardsToPlayer(turn % players.size());
                        yield true;
                    }
                    default -> turnDone;
                };
                if (!turnDone){
                    vista.badTurn();
                }
            }
            turn++;
        }
        turn--;
        Player winner = players.get(turn % players.size());
        if (gameFinishedViaKnock){
            Player playerKnockDone = players.get(turn % players.size());
            vista.knockFinished(playerKnockDone);
            int playersFinished = 1;
            while (playersFinished != players.size()){
                turn++;
                while (insert.moveChoiceFinishingKnock() == Actions.ADD_CARD_TO_COMBO){
                    vista.printTable(table);
                    addCards();
                }
                playersFinished++;
            }
            winner = checkWhoWonKnock();
        }
        vista.gameWinScenario(winner.getName());
    }

    public void startGame() {
        int playQuant = insert.playersQuantity();
        insert.a();
        for (int i = 0; i < playQuant; i++) {
            String playName = insert.userName(i + 1);
            players.add(new Player(playName));
        }
        gameSet();
    }

    public void giveCardsToPlayer(int playerNumber) {
        Player player = players.get(playerNumber);
        int SHOWEN_CARD_POS = mainDeck.getDeck().size() - 1;
        int HIDDEN_CARD_POS = mainDeck.getDeck().size() - 2;
        Card showenCard = mainDeck.getDeck().get(SHOWEN_CARD_POS);
        vista.printCard(showenCard);
        Actions takeCardOption = insert.takeCardOptions();

        Card cardToBeRemoved = null;

        if (takeCardOption == Actions.SWAP_SHOWEN_CARD){
            cardToBeRemoved = player.getHand().getDeck().get(insert.cardToBeRemoved());
            player.getHand().addCard(mainDeck.removeCard(SHOWEN_CARD_POS));
            while (cardToBeRemoved == showenCard){
                vista.invalidCard();
                cardToBeRemoved = player.getHand().getDeck().get(insert.cardToBeRemoved());
            }
            mainDeck.addCard(cardToBeRemoved);
        }

        if (takeCardOption == Actions.TAKE_HIDDEN_CARD){
            cardToBeRemoved = player.getHand().getDeck().get(insert.cardToBeRemoved());
            player.getHand().addCard(mainDeck.removeCard(HIDDEN_CARD_POS));
            discardedCards.addCard(cardToBeRemoved);
        }
        player.getHand().getDeck().remove(cardToBeRemoved);
    }

    public boolean ginMove(){
        Table clonedTable = cloneTable(table);
        Player clonedPlayer = clonePlayer(players.get(turn % players.size()));
        while (!players.get(turn % players.size()).getHand().getDeck().isEmpty()){
            switch (insert.ginAction(players.get(turn % players.size()).getHand().getDeck().size())){
                case CONTINUE:
                    createCombination();
                    break;
                case STOP:
                    table = clonedTable;
                    players.set(turn % players.size(),clonedPlayer);
                    return false;
            }
        }
        return true;
    }

    public boolean knockMove(){
        Table clonedTable = cloneTable(table);
        Player clonedPlayer = clonePlayer(players.get(turn % players.size()));
        while (insert.knockAction() == Actions.CONTINUE){
            createCombination();
        }
        if (countPointsOfPlayer(players.get(turn % players.size())) > 10){
            table = clonedTable;
            players.set(turn % players.size(),clonedPlayer);
            return false;
        }
        return true;
    }

    public void createCombination(){
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
                vista.invalidCard();
                return;
            }
        }
        for (Card card : cardsToBeRemoved) {
            player.getHand().getDeck().remove(card);
        }
        table.addCombination(comb);
    }

    public void addCards(){
        Player player = players.get(turn% players.size());
        vista.printPlayerDeckAndTable(table,player);
        int cardToBeRemoved = insert.cardChoice();
        Combination comb = table.getTable().get(insert.combinationChoice());
        if (comb.addCard(player.getHand().getDeck().get(cardToBeRemoved))){
            player.getHand().removeCard(cardToBeRemoved);
            return;
        }
        vista.invalidCard();
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

    public int countPoints(Card card){
        int COURT_POINTS = 10;
        int COURT_MIN_NUMBER= 11;
        if (card.getCardNumber().getNumber() <= COURT_MIN_NUMBER) {
            return card.getCardNumber().getNumber();
        }
        return COURT_POINTS;
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

    public Player checkWhoWonKnock(){
        Player winner = players.get(0);
        for (Player player: players) {
            if (countPointsOfPlayer(player) < countPointsOfPlayer(winner)){
                winner = player;
            }
        }
        return winner;
    }

    public Player clonePlayer(Player player){
        Player clonedPlayer = new Player(player.getName());
        for (Card card : player.getHand().getDeck()){
            clonedPlayer.getHand().addCard(card);
        }
        return clonedPlayer;
    }
}
