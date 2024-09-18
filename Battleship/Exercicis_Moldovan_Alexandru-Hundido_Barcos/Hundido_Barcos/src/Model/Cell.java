package Model;

public class Cell {

    private ShipPart shipPart;
    private boolean shot = false;

    public Cell(){
    }

    public void setShipPart(ShipPart hasShipPart) {
        this.shipPart = hasShipPart;
    }

    public ShipPart getShipPart() {
        return shipPart;
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }
}