package com.pack.models;

public class Table{
    private int columns;
    private int rows;
    private Cell[][] table;
    private int mineQuantity = 0;

    public Table(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.table = new Cell[rows][columns];
    }

    public int getMineQuantity() {
        return mineQuantity;
    }

    public void setMineQuantity(int mineQuantity) {
        this.mineQuantity = mineQuantity;
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
}

