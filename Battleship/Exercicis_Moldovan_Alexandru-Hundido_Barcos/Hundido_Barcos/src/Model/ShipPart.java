package Model;

public class ShipPart {
    private boolean destroyed = false;
    private Ship ship;
    private int coordRow;
    private int coordColumn;

    public ShipPart(Ship ship){
        this.ship = ship;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed() {
        this.destroyed = true;
    }

    public Ship getShip(){
        return this.ship;
    }

    public void setCoordRow(int coordRow) {
        this.coordRow = coordRow;
    }

    public int getCoordRow() {
        return coordRow;
    }

    public void setCoordColumn(int coordColumn) {
        this.coordColumn = coordColumn;
    }

    public int getCoordColumn() {
         return coordColumn;
    }
}
