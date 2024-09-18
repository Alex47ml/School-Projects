package Model;

public class Table{
    private int columns;
    private int rows;
    private Cell[][] table;
    private Ship[] ships = new Ship[10];

    public Table(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.table = new Cell[rows][columns];
    }

    public int getTableRows() {
        return this.rows;
    }

    public int getTableColumns () {
        return this.columns;
    }

    public Cell getCell(int row, int column){
        return this.table[row][column];
    }

    public Cell[][] getTable(){
        return table;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship [] ships){
        this.ships = ships;
    }
}