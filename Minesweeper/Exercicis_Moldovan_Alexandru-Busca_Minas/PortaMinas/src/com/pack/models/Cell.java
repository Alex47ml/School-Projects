package com.pack.models;

public class Cell {

    private boolean isMine = false;
    private int surroundingMines = 0;
    private boolean isHidden = true;
    private boolean isFlag = false;

    public Cell(){}

    public boolean isMine() {
        return isMine;
    }

    public void setMine(){
        this.isMine = true;
    }

    public void open() {
        this.isHidden = false;
    }

    public boolean IsFlag() {
        return isFlag;
    }

    public void setFlag(){
        this.isFlag = true;
    }

    public void hide(){
        this.isHidden = true;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }
}
