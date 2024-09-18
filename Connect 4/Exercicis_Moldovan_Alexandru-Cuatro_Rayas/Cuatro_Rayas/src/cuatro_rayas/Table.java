package cuatro_rayas;

import java.util.ArrayList;

public class Table {
    private static final int columns = 7;
    private static final int rows = 6;
    private final int[][] table = new int[rows][columns];
    private final Player [] playersAtTable = new Player[2];
    private int lastRowIns;
    private int lastColIns;


    public void drawTable2 () {
        System.out.println();
        for (int i = 0; i < rows; i++) {
            printContent(i);
        }
        printBottomLine();
        printInstruction();
    }

    public void printContent (int row) {
        System.out.print("║");
        for (int i = 0; i < columns; i++) {
            System.out.print(table[row][i]);
            System.out.print("║");
        }
        System.out.println();
    }

    public void printBottomLine () {
        System.out.print("╚");
        System.out.print("═");
        for (int i = 0; i < columns - 1; i++) {
            System.out.print("╩═");
        }
        System.out.print("╝");
        System.out.println();
    }

    public void printInstruction () {
        for (int i = 1; i < columns + 1; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
    }

    public void addToken (int column,int token){
        int row = findWhereToPut(column);
        if (row == -1) {
            System.out.println("That's a full column! Choose another one please.");
        } else {
            this.lastRowIns = row;
            this.lastColIns = column;
            this.table [row][column] = token;
        }
    }

    public int findWhereToPut (int column){
        int y = rows - 1;
        while (y != -1){
            if (checkIfEmpty(y,column)){
                return y;
            }
            y--;
        }
        return y;
    }

    public boolean checkIfEmpty (int y, int x) {
        if (table[y][x] == 0){
            return true;
        }
        return false;
    }

    public void addPlayers (Player player) {
        for (int i = 0; i < playersAtTable.length; i++) {
            if (playersAtTable[i] == null){
                playersAtTable[i] = player;
                return;
            }
        }
        System.out.println("Table is full");
    }

    public String checkPlayer(int id) {
        for (int i = 0; i < playersAtTable.length; i++) {
            if (playersAtTable[i].getId() == id % 2){
                return playersAtTable[i].getPlayer();
            }
        }
        return null;
    }

    public boolean checkIfWin () {
        if (rowWin()){
            return true;
        } if (columnWin()){
            return true;
        } if (diagonalWinAsc()){
            return true;
        } else {
            return diagonalWinDesc();
        }
    }

    public boolean rowWin (){
        int counterDirection1 = 1;
        int counterDirection2 = 0;
        for (int i = 1; i < columns; i++) {
            if (lastColIns - i == -1) {
                break;
            }
            if (table[lastRowIns][lastColIns] == table[lastRowIns][lastColIns - i]){
                counterDirection1++;
            } else {
                break;
            }
        }
        for (int i = 1; i < columns; i++) {
            if (lastColIns + i == 7) {
                break;
            }
            if (table[lastRowIns][lastColIns] == table[lastRowIns][lastColIns + i]){
                counterDirection2++;
            } else {
                break;
            }
        }
        if (counterDirection1 + counterDirection2 >= 4){
            return true;
        }
        return false;
    }
    public boolean diagonalWinAsc (){
        int counterDirection1 = 1;
        int counterDirection2 = 0;
        for (int i = 1; i < columns; i++) {
            if (lastRowIns - i == -1 || lastColIns + i == 7) {
                break;
            }
            if (table[lastRowIns][lastColIns] == table[lastRowIns - i][lastColIns + i]){
                counterDirection1++;
            } else {
                break;
            }
        }
        for (int i = 1; i < columns; i++) {
            if (lastRowIns + i == 6 || lastColIns - i == -1) {
                break;
            }
            if (table[lastRowIns][lastColIns] == table[lastRowIns + i][lastColIns - i]){
                counterDirection2++;
            } else {
                break;
            }
        }
        if (counterDirection1 + counterDirection2 >= 4){
            return true;
        }
        return false;
    }
    public boolean diagonalWinDesc (){
        int counterDirection1 = 1;
        int counterDirection2 = 0;
        for (int i = 1; i < columns; i++) {
            if (lastRowIns - i == -1 || lastColIns - i == -1) {
                break;
            }
            if (table[lastRowIns][lastColIns] == table[lastRowIns - i][lastColIns - i]){
                counterDirection1++;
            } else {
                break;
            }
        }
        for (int i = 1; i < columns; i++) {
            if (lastRowIns + i == 6 || lastColIns + i == 7) {
                break;
            }
            if (table[lastRowIns][lastColIns] == table[lastRowIns + i][lastColIns + i]){
                counterDirection2++;
            } else {
                break;
            }
        }
        if (counterDirection1 + counterDirection2 >= 4){
            return true;
        }
        return false;
    }
    public boolean columnWin (){
        int counterDirection1 = 1;
        int counterDirection2 = 0;
        for (int i = 1; i < columns; i++) {
            if (lastRowIns - i == -1) {
                break;
            }
            if (table[lastRowIns][lastColIns] == table[lastRowIns - i][lastColIns]){
                counterDirection1++;
            } else {
                break;
            }
        }
        for (int i = 1; i < columns; i++) {
            if (lastRowIns + i == 6) {
                break;
            }
            if (table[lastRowIns][lastColIns] == table[lastRowIns + i][lastColIns]){
                counterDirection2++;
            } else {
                break;
            }
        }
        if (counterDirection1 + counterDirection2 >= 4){
            return true;
        }
        return false;
    }

}
