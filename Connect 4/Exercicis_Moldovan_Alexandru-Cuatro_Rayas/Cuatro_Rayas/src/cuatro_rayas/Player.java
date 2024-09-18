package cuatro_rayas;

public class Player {
    private int id;
    protected int idCounter = 1;
    private String player = "Player One";

    public Player (String name){
        this.player = name;
        this.id = this.idCounter;
        this.idCounter++;
    }

    public int getId() {
        return this.id;
    }
    public String getPlayer() {
        return player;
    }

}
