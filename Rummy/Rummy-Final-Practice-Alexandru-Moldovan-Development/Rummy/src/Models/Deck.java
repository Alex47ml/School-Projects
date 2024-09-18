package Models;

import CardModels.Card;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public Card removeCard(int position){
        Card cardToRemove = deck.get(position);
        deck.remove(position);
        return cardToRemove;
    }
}
