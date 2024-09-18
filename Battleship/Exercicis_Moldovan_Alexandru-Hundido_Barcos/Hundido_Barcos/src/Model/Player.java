package Model;

public class Player {
    private String name;
    private Table table;

    public Player(String name,Table table) {
        this.name = name;
        this.table = table;
    }

    public String getName(){
        return name;
    }

    public Table getTable(){
        return table;
    }

}
