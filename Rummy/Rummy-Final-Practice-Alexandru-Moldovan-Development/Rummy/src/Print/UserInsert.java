package Print;

import CardModels.CardNumber;
import GameVariants.Games;
import Logic.Actions;

import java.util.Scanner;

public class UserInsert {
    private final Scanner in = new Scanner(System.in);

    public int playersQuantity(){
        int MAX_PLAY_QUANT = 7;
        int MIN_PLAY_QUANT = 2;
        System.out.println("Cuantos jugadores habra, de 2 a 7 jugadores");
        int playersNumb = in.nextInt();
        while (playersNumb < MIN_PLAY_QUANT || playersNumb > MAX_PLAY_QUANT) {
            System.out.println("Es una cantidad de jugadores invalida, tiene que ser minimo 2 y como maximo 7");
            playersNumb = in.nextInt();
        }
        return playersNumb;
    }

    public void badOptionSelected(){
        System.out.println("Elegiste la opcion mala/inexistente, prueba de nuevo!");
    }

    public Actions beginTurnOption(){
        System.out.println("1: Si no tienes combinaciones puedes coger una carta de la baraja y acabar el turno");
        System.out.println("2: Si quieres jugar tus cartas");
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("1: Si no tienes combinaciones puedes coger una carta de la baraja y acabar el turno");
            System.out.println("2: Si quieres jugar tus cartas");
            option = in.nextInt();
        }
        return switch (option) {
            case 1 -> Actions.STOP;
            case 2 -> Actions.CONTINUE;
            default -> Actions.INVALID_ACTION;
        };
    }

    public Actions turnOption(){
        System.out.println("1: Si quieres acabar el turno");
        System.out.println("2: Si quieres seguir jugando las cartas pulsa");
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("1: Si quieres acabar el turno");
            System.out.println("2: Si quieres seguir jugando las cartas pulsa");
            option = in.nextInt();
        }
        return switch (option) {
            case 1 -> Actions.STOP;
            case 2 -> Actions.CONTINUE;
            default -> Actions.INVALID_ACTION;
        };
    }

    public Actions badTurnDone(){
        System.out.println("1: Has hecho mal la combinacion, para probar de nuevo");
        System.out.println("2: Si no tienes combinaciones puedes coger una carta de la baraja y acabar el turno");
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("1: Has hecho mal la combinacion, para probar de nuevo");
            System.out.println("2: Si no tienes combinaciones puedes coger una carta de la baraja y acabar el turno");
            option = in.nextInt();
        }
        return switch (option) {
            case 1 -> Actions.CONTINUE;
            case 2 -> Actions.STOP;
            default -> Actions.INVALID_ACTION;
        };
    }

    public Actions takeCardOptions(){
        System.out.println("""
                1: Si quieres coger la carta volteada, tendras que cambiar-la por otra carta que tienes
                2: Si quieres coger una carta ocultada, tendras que descartar una carta cual quieres(incluido la que cogiste)
                """);
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("""
                1: Si quieres coger la carta volteada, tendras que cambiar-la por otra carta que tienes
                2: Si quieres coger una carta ocultada, tendras que descartar una carta cual quieres(incluido la que cogiste)
                """);
            option = in.nextInt();
        }

        return switch (option) {
            case 1 -> Actions.SWAP_SHOWEN_CARD;
            case 2 -> Actions.TAKE_HIDDEN_CARD;
            default -> Actions.INVALID_ACTION;
        };
    }

    public int cardToBeRemoved(){
        System.out.println("Que carta quieres descartar?");
        return in.nextInt();
    }

    public Actions ginAction(int cardsQuantity){
        System.out.println("Tendras que jugar todas las cartas, para jugar te quedan: " + cardsQuantity);
        System.out.println("1: Hacer la jugada");
        System.out.println("2: Deshacer todo y acabar el turno");
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("Tendras que jugar todas las cartas, para jugar te quedan: " + cardsQuantity);
            System.out.println("1: Hacer la jugada");
            System.out.println("2: Deshacer todo y acabar el turno");
            option = in.nextInt();
        }

        return switch (option) {
            case 1 -> Actions.CONTINUE;
            case 2 -> Actions.STOP;
            default -> Actions.INVALID_ACTION;
        };
    }

    public Actions knockAction(){
        System.out.println("Tendras que jugar todas las cartas posibles para que te quede maximo 10 puntos en la mano");
        System.out.println("1: Hacer la jugada");
        System.out.println("2: Acabar el turno");
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("Tendras que jugar todas las cartas posibles para que te quede maximo 10 puntos en la mano");
            System.out.println("1: Hacer la jugada");
            System.out.println("2: Acabar el turno");
            option = in.nextInt();
        }

        return switch (option) {
            case 1 -> Actions.CONTINUE;
            case 2 -> Actions.STOP;
            default -> Actions.INVALID_ACTION;
        };
    }

    public String userName(int playerNumber){
        System.out.println("Que nombre tiene el jugador " + playerNumber);
        return in.nextLine();
    }

    public CardNumber chooseJokerValue(){
        System.out.println("Que valor le quieres dar al joker/comodin de 1 a 13?");
        int numb = in.nextInt();
        while (true){
            switch (numb) {
                case 1:
                    return CardNumber.ONE;
                case 2:
                    return CardNumber.TWO;
                case 3:
                    return CardNumber.THREE;
                case 4:
                    return CardNumber.FOUR;
                case 5:
                    return CardNumber.FIVE;
                case 6:
                    return CardNumber.SIX;
                case 7:
                    return CardNumber.SEVEN;
                case 8:
                    return CardNumber.EIGHT;
                case 9:
                    return CardNumber.NINE;
                case 10:
                    return CardNumber.TEN;
                case 11:
                    return CardNumber.ELEVEN;
                case 12:
                    return CardNumber.TWELVE;
                case 13:
                    return CardNumber.THIRTEEN;
                default:
                    System.out.println("Elegiste un numero incorrecto, prueba de nuevo");
            }
            System.out.println("Introduciste un numero incorrecto, elige otro numero o pulsa 0 para salir");
            numb = in.nextInt();
            if (numb == 0){
                return null;
            }
        }
    }

    public Integer cardChoice(){
        System.out.println("Que carta quieres elegir?");
        return in.nextInt();
    }

    public int combinationChoice(){
        System.out.println("En que combinacion quieres meter la carta");
        return in.nextInt();
    }

    public Actions moveChoice(){
        System.out.println("1: Si quieres crear una combinacion nueva");
        System.out.println("2: Si quieres añadir una carta a una combinacion existente");
        System.out.println("""
                3: Si quieres cambiar una carta de la taula por una que tienes en la mano
                La carta cambiada la tendras que jugar en el mismo turno!!!""");
        int option = in.nextInt();
        while (option != 1 && option != 2 && option != 3){
            System.out.println("1: Si quieres crear una combinacion nueva");
            System.out.println("2: Si quieres añadir una carta a una combinacion existente");
            System.out.println("""
                3: Si quieres cambiar una carta de la taula por una que tienes en la mano
                La carta cambiada la tendras que jugar en el mismo turno!!!""");
            option = in.nextInt();
        }

        return switch (option) {
            case 1 -> Actions.CREATE_NEW_COMBO;
            case 2 -> Actions.ADD_CARD_TO_COMBO;
            case 3 -> Actions.SWAP_CARD;
            default -> Actions.INVALID_ACTION;
        };
    }

    public Actions moveChoiceGinRummy(){
        System.out.println("1: Gin");
        System.out.println("2: Knock");
        System.out.println("3: Coger/cambiar carta");
        int option = in.nextInt();
        while (option != 1 && option != 2 && option != 3){
            System.out.println("1: Gin");
            System.out.println("2: Knock");
            System.out.println("3: Coger/cambiar carta");
            option = in.nextInt();
        }
        return switch (option) {
            case 1 -> Actions.GIN;
            case 2 -> Actions.KNOCK;
            case 3 -> Actions.STOP;
            default -> Actions.INVALID_ACTION;
        };
    }

    public Actions moveChoiceFinishingKnock(){
        System.out.println("1: Anadir carta");
        System.out.println("2: Parar");
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("1: Anadir carta");
            System.out.println("2: Parar");
            option = in.nextInt();
        }
        return switch (option) {
            case 1 -> Actions.ADD_CARD_TO_COMBO;
            case 2 -> Actions.STOP;
            default -> Actions.INVALID_ACTION;
        };
    }

    public Games typeOfDeckGame(){
        System.out.println("1: Si quieres jugar con una baraja doble de grande");
        System.out.println("2: Si quieres jugar con una baraja normal");
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("1: Si quieres jugar con una baraja doble de grande");
            System.out.println("2: Si quieres jugar con una baraja normal");
            option = in.nextInt();
        }

        if (option == 1){
            return Games.DoubleDeckGame;
       }
        return null;
    }

    public void a(){
        in.nextLine();
    }

    public Actions stopAction(){
        System.out.println("1: Si quieres parar");
        System.out.println("2: Si quieres seguir");
        int option = in.nextInt();
        while (option != 1 && option != 2){
            System.out.println("1: Si quieres parar");
            System.out.println("2: Si quieres seguir");
            option = in.nextInt();
        }
        return switch (option) {
            case 1 -> Actions.STOP;
            case 2 -> Actions.CONTINUE;
            default -> Actions.INVALID_ACTION;
        };
    }
}
