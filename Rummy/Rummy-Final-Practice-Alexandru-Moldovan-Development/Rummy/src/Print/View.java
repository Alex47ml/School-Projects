package Print;

import CardModels.Card;
import CardModels.CardSymbol;
import CombinationModels.Combination;
import Logic.Actions;
import Models.*;

public class View {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String JOKER_VALUE = "\uD83C\uDCCF";
    private static final String CLUBS_VALUE = "♣";
    private static final String HEARTS_VALUE = "♥";
    private static final String SPADES_VALUE = "♠";
    private static final String DIAMONDS_VALUE = "♦";


    public void printPlayerDeckAndTable(Table table, Player player) {
        System.out.println("-----La taula-----");
        System.out.println();
        printTable(table);
        System.out.println("-----La taula-----");
        System.out.println();
        System.out.println("-----La baraja del jugador " + player.getName() + "-----");
        printCards(player.getHand());
    }

    public void printCards(Deck deck) {
        int index = 0;
        for (Card card : deck.getDeck()) {
            System.out.print(index + "[" + printCard(card) + "]");
            if (card != deck.getDeck().getLast()) {
                System.out.print(", ");
            }
            index++;
            if (index % 8 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println();
    }

    public void knockFinished(Player player){
        System.out.println("El jugador " + player.getName() + " finaliza el juego con knock");
        System.out.println("""
                "Los demas jugadores tendran que jugar sus cartas en las combinaciones que hizo el jugador knock"
                "Deberan tener una puntuacion menor al jugador que hizo knock para poder ganar"
                "El jugador que hizo knock no podra hacer nada a partir de este momento"
                "El jugador con menos puntuacion ganara la partida"
                """);
    }

    public void printCards(Combination comb) {
        int index = 0;
        for (Card card : comb.getCombination()) {
            System.out.print(index + "[" + printCard(card) + "]");
            if (card != comb.getCombination().getLast()) {
                System.out.print(", ");
            }
            index++;
            if (index % 8 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println();
    }

    public String printCard(Card card){
        int cardNumber = 0;
        if (card.getCardSymbol() != CardSymbol.JOKER){
            cardNumber = card.getCardNumber().getNumber();
        }
        String cardValue;
        switch (cardNumber){
            case 1:
                cardValue = "A";
                break;
            case 11:
                cardValue = "J";
                break;
            case 12:
                cardValue = "Q";
                break;
            case 13:
                cardValue = "K";
                break;
            default:
                cardValue = String.valueOf(cardNumber);
                break;
        }

        switch (card.getCardSymbol()) {
            case CardSymbol.CLUBS:
                return ANSI_GREEN + cardValue + CLUBS_VALUE + ANSI_RESET;
            case CardSymbol.DIAMONDS:
                return ANSI_YELLOW + cardValue + DIAMONDS_VALUE + ANSI_RESET;
            case CardSymbol.HEARTS:
                return ANSI_RED + cardValue + HEARTS_VALUE + ANSI_RESET;
            case CardSymbol.SPADES:
                return ANSI_BLUE + cardValue + SPADES_VALUE + ANSI_RESET;
            case CardSymbol.JOKER:
                return ANSI_PURPLE + JOKER_VALUE + ANSI_RESET;
            default:
                return null;
        }
    }


    public void printTable(Table table){
        for (int i = 0; i < table.getTable().size(); i++) {
            System.out.println("La combinacion " + i +":");
            printCards(table.getTable().get(i));
        }
    }

    public void invalidCard(){
        System.out.println("Elegiste una carta/combinacion incorrecta");
    }

    public void setWinScenario(String name){
        System.out.println("El set ha sido ganado por " + name);
    }

    public void gameWinScenario(String name){
        System.out.println("Ha ganado " + name + "!");
    }

    public void firstTurnOption(){
        System.out.println(ANSI_RED + "En el primer turno solo puedes crear una combinacion nueva, no tienes otra opcion\n".toUpperCase() + ANSI_RESET);
    }

    public void printPlayerWithPoints(Player player){
        System.out.println("El jugador " + player.getName() + " tiene " + player.getPoints() + " puntos");
    }

    public void firstTurnRulesExplanation(){
        System.out.println("""
                El juego consiste en hacer combinaciones de escaleras o palos, de minimo 3 cartas
                Primera jugada tiene que ser minimo de 30 puntos
                """);
    }

    public void badTurn(){
        System.out.println(ANSI_RED + "TU JUGADA NO ES VALIDA, PRUEBA DE NUEVO" + ANSI_RESET);
    }

    public void firstTurnIllegalPoints(){
        System.out.println("Primero turno tiene que ser de 30 puntos, sino no es legal!");
    }
}
