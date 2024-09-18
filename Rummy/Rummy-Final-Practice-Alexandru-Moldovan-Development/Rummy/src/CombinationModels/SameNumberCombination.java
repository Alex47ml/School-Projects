package CombinationModels;

import CardModels.Card;
import CardModels.CardSymbol;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

public class SameNumberCombination extends Combination {
    private ArrayList<Card> combination = new ArrayList<Card>();

    @Override
    public ArrayList<Card> getCombination() {
        return combination;
    }

    @Override
    public boolean addCard(Card card) {
        if (card.getCardSymbol() != CardSymbol.JOKER ){
            for (Card c : combination){
                if (c.getCardNumber().getNumber() != card.getCardNumber().getNumber()){
                    return false;
                }
                if (c.getCardSymbol() == card.getCardSymbol() ) {
                    return false;
                }
            }
        }
        combination.add(card);
        return true;
    }
    @Override
    public boolean createCombination(ArrayList<Card> comb) {
    if (comb.size() < 3) {
        return false;
    }
    for (int i = 0; i < comb.size(); i++) {
        if (comb.getFirst().getCardNumber().getNumber() != comb.get(i).getCardNumber().getNumber()){
            return false;
        }
        if (comb.get(i).getCardSymbol() != CardSymbol.JOKER){
            for (int j = 0; j < 3; j++) {
                if (j != i) {
                    if (comb.get(j).getCardSymbol() == comb.get(i).getCardSymbol()) {
                        return false;
                    }
                }
            }
        }
    }
    this.combination = comb;
    return true;
    }

    @Override
    public Card swapCard(Card newCard, Card oldCard) {
        combination.remove(oldCard);
        if(addCard(newCard)){
            return oldCard;
        } else {
            combination.add(oldCard);
            return null;
        }
    }
}



