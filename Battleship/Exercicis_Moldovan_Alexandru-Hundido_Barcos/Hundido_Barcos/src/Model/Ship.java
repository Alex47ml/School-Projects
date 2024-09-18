package Model;

public class Ship {

    private ShipEnum ship;
    private ShipPart[] shipParts;
    private boolean isDestroyed = false;

    public Ship(ShipEnum ship){
        this.ship = ship;
        this.shipParts = new ShipPart[ship.getShipLength()];
    }

    public ShipPart[] getShipParts() {
        return shipParts;
    }

    public ShipEnum getShip() {
        return ship;
    }

    public void setDestroyed(boolean isDestroyed){
        this.isDestroyed = isDestroyed;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

}
