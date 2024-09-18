package CombinationModels;

import CardModels.Card;

import java.util.ArrayList;

public abstract class Combination {
    ArrayList<Card> combination = new ArrayList<Card>();

    public ArrayList<Card> getCombination(){
        return combination;
    };

    public abstract boolean addCard(Card card);

    public abstract boolean createCombination(ArrayList<Card> comb);

    public abstract Card swapCard(Card newCard, Card oldCard);
}
