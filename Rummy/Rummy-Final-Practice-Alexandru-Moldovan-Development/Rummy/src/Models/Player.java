package Models;

public class Player {
    private String name;
    private Deck hand = new Deck();
    private int points = 0;
    private boolean hasMadeFirstTurn = false;

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Deck getHand(){
        return hand;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void setHasMadeFirstTurn(boolean hasMadeFirstTurn) {
        this.hasMadeFirstTurn = hasMadeFirstTurn;
    }

    public boolean getHasMadeFirstTurn() {
        return hasMadeFirstTurn;
    }
}
