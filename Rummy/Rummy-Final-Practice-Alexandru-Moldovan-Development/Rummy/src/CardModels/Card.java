package CardModels;

public class Card {
    private CardNumber cardNumber;
    private CardSymbol cardSymbol;

    public Card (CardNumber cardNumber, CardSymbol cardSymbol){
        this.cardNumber = cardNumber;
        this.cardSymbol = cardSymbol;
    }

    public Card (CardSymbol cardSymbol){
        this.cardSymbol = cardSymbol;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public CardSymbol getCardSymbol() {
        return cardSymbol;
    }

    public void setCardNumber(CardNumber numb){
        this.cardNumber = numb;
    }
}
