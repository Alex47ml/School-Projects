package Model;

public enum ShipEnum {
    PORTAAVIONES("PORTAAVIONES",5),
    BUQUE("BUQUE",4),
    SUBMARINO("SUBMARINO",3),
    CRUCERO("CRUCERO",2),
    LANCHA("LANCHA",1);

    private String name;
    private int length;

    ShipEnum(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getShipName(){
        return name;
    }

    public int getShipLength(){
        return length;
    }
}
