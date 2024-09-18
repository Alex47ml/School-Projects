package DeckFactory;

import CardModels.Card;
import CardModels.CardNumber;
import CardModels.CardSymbol;
import GameVariants.Games;
import Models.Deck;
import Print.View;

import java.util.Random;

public class DeckCreation {
    public Deck create(Games gameVariant, boolean doubleDeck){
        Deck deck = new Deck();
        deck = createDeck(deck,gameVariant,doubleDeck);
        // deck = randomizeDeck(deck);
        return deck;
    }
    public Deck createDeck(Deck deck, Games gameVariant, boolean doubleDeck){
        int decksQuantity = 1;
        if (doubleDeck){
            decksQuantity = 2;
        }
        for (int i = 0; i < decksQuantity; i++) {
            // Crear todas las cartas menos JOKER
            for (CardSymbol cardSymbol:CardSymbol.values()) {
                for (CardNumber cardNumber:CardNumber.values()) {
                    if (cardSymbol != CardSymbol.JOKER && cardSymbol != CardSymbol.MONO){
                        deck.addCard(new Card(cardNumber,cardSymbol));
                    }
                }
            }
            // Crear los jokers
            if (gameVariant == Games.Rummikub || gameVariant == Games.RummyClassic || gameVariant == Games.RummyArgentino){
                deck.addCard(new Card(CardSymbol.JOKER));
            }
            if (gameVariant == Games.RummyArgentino){
                deck.addCard(new Card(CardSymbol.MONO));
            }
        }
        return deck;
    }
    public Deck randomizeDeck(Deck deck){
        Random rand = new Random();
        Card randomCard;
        Card cardToChangeWith;
        for (int i = 0; i < deck.getDeck().size(); i++) {
            int randomPosition = rand.nextInt(deck.getDeck().size());
            cardToChangeWith = deck.getDeck().get(i);
            randomCard = deck.getDeck().get(randomPosition);
            // Para cambiar las cartas en una arrayList primero tenemos que borrar la carta que tiene que ser cambiada, y despues añadir en su posicion la nueva carta
            deck.getDeck().remove(i);
            deck.getDeck().add(i,randomCard);

            deck.getDeck().remove(randomPosition);
            deck.getDeck().add(randomPosition,cardToChangeWith);
        }
        return deck;
    }
}
