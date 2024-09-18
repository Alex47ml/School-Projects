package CombinationModels;

import CardModels.Card;
import CardModels.CardSymbol;

import java.util.ArrayList;

public class StairsCombination extends Combination {
    private ArrayList<Card> combination = new ArrayList<Card>();

    @Override
    public ArrayList<Card> getCombination() {
        return combination;
    }

    @Override
    public boolean addCard(Card card) {
        if (combination.getFirst().getCardNumber().getNumber() - 1 != card.getCardNumber().getNumber()){
            return false;
        }
        if (combination.getFirst().getCardNumber().getNumber() + 1 != card.getCardNumber().getNumber()){
            return false;
        }
        if (combination.getFirst().getCardSymbol() != card.getCardSymbol()
                || card.getCardSymbol() != CardSymbol.JOKER){
            return false;
        }
        combination.add(card);
        sortComb(this.combination);
        return true;
    }

    @Override
    public boolean createCombination(ArrayList<Card> comb) {
        ArrayList<Card> combToAdd = sortComb(comb);
        for (int i = 1; i < combToAdd.size(); i++) {
            if ((combToAdd.get(i).getCardNumber().getNumber() + 1 !=  combToAdd.get(i - 1).getCardNumber().getNumber())
                && (combToAdd.get(i).getCardNumber().getNumber() - 1 !=  combToAdd.get(i - 1).getCardNumber().getNumber())){
                return false;
            }
            if (combToAdd.get(i).getCardSymbol() !=  combToAdd.get(i - 1).getCardSymbol()
                    && combToAdd.get(i).getCardSymbol() != CardSymbol.JOKER){
                return false;
            }
        }
        this.combination = combToAdd;
        return true;
    }

    @Override
    public Card swapCard(Card newCard, Card oldCard) {
        if (newCard.getCardSymbol() != oldCard.getCardSymbol()
            && newCard.getCardSymbol() != CardSymbol.JOKER){
            return null;
        }
        if (newCard.getCardNumber() != oldCard.getCardNumber()){
            return null;
        }
        combination.add(newCard);
        combination.remove(oldCard);
        return oldCard;
    }

    private ArrayList<Card> sortComb(ArrayList<Card> comb){
        for (int i = 0; i < comb.size(); i++) {
            Card minCard = comb.get(i);
            int aux = i;
            for (int j = i; j < comb.size(); j++) {
                if (minCard.getCardNumber().getNumber() > comb.get(j).getCardNumber().getNumber()){
                    minCard = comb.get(j);
                    aux = j;
                }
            }
            comb.set(aux,comb.get(i));
            comb.set(i,minCard);
        }
        return comb;
    }
}
