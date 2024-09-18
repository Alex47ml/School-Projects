package com.pack.logic;

import com.pack.models.Table;
import com.pack.models.Cell;
import com.pack.view.PrintGame;

public class Game {
    private final PrintGame view = new PrintGame();
    private final boolean development = true;
    private int cellsRevealed = 0;
    private Table game;

    public Game () {
        init();
    }

    public void init(){
        if (development){
            game = new Table(5,5);
            fillTableWithCells();
            randomizeMineLocation(1);
            changeTiles();
            printRevealedTable(game);
        } else {
            game = view.askUserGameOptions();
            fillTableWithCells();
            randomizeMineLocation(view.difficultyUserSelected());
            changeTiles();
        }
        view.printTable(game);
        while (true){
            int row = view.rowUserSelected();
            int column = view.colUserSelected();
            int option = view.optionSelected();
            if (option == 2){
                game.getCell(row,column).setFlag();
            } else if (option == 1) {
                if (revealUserSelectedCell(row,column)){
                    view.loseScenario();
                    break;
                }
                view.printTable(game);
                if (game.getTableRows() * game.getTableColumns() == cellsRevealed + game.getMineQuantity()){
                    view.winScenario();
                    break;
                }
            }
        }
    }

    public void printRevealedTable(Table table){
        for (int i = 0; i < table.getTableRows(); i++) {
            for (int j = 0; j < table.getTableColumns(); j++) {
                table.getTable()[i][j].open();
            }
        }
        PrintGame revealedView = new PrintGame();
        revealedView.printTable(table);
        for (int i = 0; i < table.getTableRows(); i++) {
            for (int j = 0; j < table.getTableColumns(); j++) {
                table.getTable()[i][j].hide();
            }
        }
    }

    public boolean revealUserSelectedCell(int row, int column){
        if (!game.getCell(row, column).getIsHidden()) {
            return false;
        }
        game.getCell(row,column).open();
        cellsRevealed++;
        Cell revealedCell = game.getCell(row,column);

        if (revealedCell.isMine()){
            return true;
        }

        if (revealedCell.getSurroundingMines() != 0){
            return false;
        }

        int beginningPosX = row-1;
        int beginningPosY = column-1;
        for (int i = beginningPosX; i < beginningPosX + 3; i++) {
            for (int j = beginningPosY; j < beginningPosY + 3; j++) {
                if ( ( i >= 0 && j >= 0 ) && ( i <= game.getTableRows() - 1 && j <= game.getTableColumns() - 1) ){
                    if (!game.getCell(i,j).isMine() && revealedCell.getSurroundingMines() == 0){
                        revealUserSelectedCell(i,j);
                    }
                }
            }
        }
        return false;
    }

    public void randomizeMineLocation(int difficulty){
        if (difficulty > 10){
            difficulty = 10;
        }
        int min = 10;
        int max = 1000 / difficulty;
        for (int i = 0; i < game.getTableRows(); i++) {
            for (int j = 0; j < game.getTableColumns(); j++) {
                int random = (int) (Math.random() * (max - min) + 1);
                if (random < 50){
                    game.getTable()[i][j].setMine();
                    game.setMineQuantity(game.getMineQuantity() + 1);
                }
            }
        }
    }

    public void changeTiles(){
        for (int i = 0; i < game.getTableRows(); i++) {
            for (int j = 0; j < game.getTableColumns(); j++) {
                if (game.getCell(i,j).isMine()){
                    changeSurroundingOfMine(i,j);
                }
            }
        }
    }

    public void changeSurroundingOfMine(int row, int column){
        int beginningPosX = row-1;
        int beginningPosY = column-1;

        for (int i = beginningPosX; i < beginningPosX + 3; i++) {
            for (int j = beginningPosY; j < beginningPosY + 3; j++) {
                if ( ( i >= 0 && j >= 0 ) && ( i <= game.getTableRows() - 1 && j <= game.getTableColumns() - 1) ){
                    if (!game.getCell(i,j).isMine() ){
                        game.getCell(i,j).setSurroundingMines(game.getCell(i,j).getSurroundingMines() + 1);
                    }
                }
            }
        }
    }

    private void fillTableWithCells(){
        for (int i = 0; i < game.getTableRows(); i++) {
            for (int j = 0; j < game.getTableColumns(); j++) {
                game.getTable()[i][j] = new Cell();
            }
        }
    }
}
