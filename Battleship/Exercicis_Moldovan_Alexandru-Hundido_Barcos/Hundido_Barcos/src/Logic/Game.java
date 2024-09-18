package Logic;

import Model.*;
import View.PrintGame;

import java.util.Random;

public class Game {
    
    private final Player[] players = new Player[2];
    private final PrintGame view = new PrintGame();
    private int turn = 0;
    private final PrintGame print = new PrintGame();

    private final ShipEnum[][] availableShips = {
            {ShipEnum.BUQUE},
            {ShipEnum.SUBMARINO, ShipEnum.SUBMARINO},
            {ShipEnum.CRUCERO, ShipEnum.CRUCERO, ShipEnum.CRUCERO},
            {ShipEnum.LANCHA, ShipEnum.LANCHA, ShipEnum.LANCHA, ShipEnum.LANCHA}
    };

    public void init() {
        players[0] = new Player(view.askPlayerName(1),new Table(10,10));
        players[1] = new Player(view.askPlayerName(2),new Table(10,10));
        for (int i = 0; i < 2; i++) {
            fillTableWithCells(players[i].getTable());
            view.turnOfPlayer(players[i]);
            generateShipsForEachPlayer(i);
            view.printTable(players[i].getTable(),true);

        }

        boolean gameFinished = false;
        while (!gameFinished) {
            int playerAttacker = turn%2;
            int playerDefender = (turn + 1) % 2;
            view.printTable(players[playerAttacker].getTable(),true);
            view.printTable(players[playerDefender].getTable(),false);
            view.turnOfPlayer(players[playerAttacker]);
            if (!shotCell(players[playerDefender])){
                turn++;
            }

            int shipsDestroyed = 0;

            for (int i = 0; i < players[playerDefender].getTable().getShips().length; i++) {
                if (players[playerDefender].getTable().getShips()[i].isDestroyed()){
                    shipsDestroyed++;
                }
            }
            if (shipsDestroyed == players[playerDefender].getTable().getShips().length){
                gameFinished = true;
                view.winScenario(players[playerAttacker].getName());
            }
        }
    }

    private void generateShipsForEachPlayer(int playerNumber) {
        int generateOption = view.generateOption();
        int shipsColocated = 0;
        for (int availableShipsRow = 0; availableShipsRow < availableShips.length; availableShipsRow++) {
            for (int availableShipColumn = 0; availableShipColumn < availableShips[availableShipsRow].length; availableShipColumn++) {
                players[playerNumber].getTable().getShips()[shipsColocated] = new Ship(availableShips[availableShipsRow][availableShipColumn]);
                fillShipWithParts(players[playerNumber].getTable().getShips()[shipsColocated]);

                if (generateOption == 2){
                    print.printTable(players[playerNumber].getTable(),true);
                    view.colocateShip(availableShips[availableShipsRow][availableShipColumn],availableShipColumn + 1);
                }
                boolean shipGenerated = false;
                while(!shipGenerated){
                    int [] coordenades = new int [2];
                    switch (generateOption){
                        case 1:
                            coordenades = randomCoords(availableShips[availableShipsRow][availableShipColumn].getShipLength());
                            break;
                        case 2: coordenades = print.askCoordenadesToUser();
                            break;
                    }
                    if (coordenades[0] == coordenades[2] // COLOCAR POR FILA&&
                        && ((coordenades[1] - coordenades[3] + 1) == availableShips[availableShipsRow][availableShipColumn].getShipLength()
                        || (coordenades[3] - coordenades[1] + 1) == availableShips[availableShipsRow][availableShipColumn].getShipLength())
                        && checkIfCellIsFree(players[playerNumber],coordenades)
                        && coordenades[0] >= 0 && coordenades[1] >= 0 && coordenades[2] >= 0 && coordenades[3] >= 0)
                    {
                        int l = Math.min(coordenades[1], coordenades[3]);
                        for (int i = 0; i < availableShips[availableShipsRow][availableShipColumn].getShipLength(); i++) {
                            players[playerNumber].getTable().getCell(coordenades[0],i + l).setShipPart(
                                players[playerNumber].getTable().getShips()[shipsColocated].getShipParts()[i]);
                            players[playerNumber].getTable().getShips()[shipsColocated].getShipParts()[i].setCoordRow(coordenades[0]);
                            players[playerNumber].getTable().getShips()[shipsColocated].getShipParts()[i].setCoordColumn(i+l);
                        }
                        shipGenerated = true;
                    }
                    if (coordenades[1] == coordenades[3] // COLOCAR POR COLUMNA
                        && ((coordenades[0] - coordenades[2] + 1) == availableShips[availableShipsRow][availableShipColumn].getShipLength()
                        || (coordenades[2] - coordenades[0] + 1) == availableShips[availableShipsRow][availableShipColumn].getShipLength())
                        && checkIfCellIsFree(players[playerNumber],coordenades)
                        && coordenades[0] >= 0 && coordenades[1] >= 0 && coordenades[2] >= 0 && coordenades[3] >= 0
                        && coordenades[0] != coordenades[2])
                    {
                        int l = Math.min(coordenades[0], coordenades[2]);
                        for (int i = 0; i < availableShips[availableShipsRow][availableShipColumn].getShipLength(); i++) {
                            players[playerNumber].getTable().getCell(i + l, coordenades[1]).setShipPart(
                                players[playerNumber].getTable().getShips()[shipsColocated].getShipParts()[i]);
                            players[playerNumber].getTable().getShips()[shipsColocated].getShipParts()[i].setCoordRow(i+l);
                            players[playerNumber].getTable().getShips()[shipsColocated].getShipParts()[i].setCoordColumn(coordenades[1]);
                        }
                        shipGenerated = true;
                    }
                    if (!shipGenerated && generateOption != 1){
                        view.failureInsert();
                    }
                }
                shipsColocated++;
            }
        }
    }

    public int[] randomCoords(int length) {
        int [] coordenades = new int[4];
        Random randomNumbers = new Random();
        if (randomNumbers.nextInt(2) == 0){
            coordenades[0] = randomNumbers.nextInt(9);
            coordenades[2] = coordenades[0];
            if (coordenades[1] > 4){
                coordenades[3] = coordenades[1] - (length - 1);
            } else {
                coordenades[3] = coordenades[1] + (length - 1);
            }
        } else {
            coordenades[1] = randomNumbers.nextInt(9);
            coordenades[3] = coordenades[1];
            coordenades[0] = randomNumbers.nextInt(9);
            if (coordenades[0] > 4) {
                coordenades[2] = coordenades[0] - (length - 1);
            } else {
                coordenades[2] = coordenades[0] + (length - 1);
            }

        }
        return coordenades;
    }

    private boolean checkIfCellIsFree(Player player,int[] coordenades) {
        int coordenadesColumn1 = coordenades[1];
        int coordenadesColumn2 = coordenades[3];
        int coordenadesRow1 = coordenades[0];
        int coordenadesRow2 = coordenades[2];
        for (int tableRow = (Math.min(coordenadesRow1,coordenadesRow2) - 1); tableRow < (Math.max(coordenadesRow1,coordenadesRow2) + 2); tableRow++) {
            for (int tableColumn = (Math.min(coordenadesColumn1,coordenadesColumn2) - 1); tableColumn < (Math.max(coordenadesColumn1,coordenadesColumn2) + 2);tableColumn++) {
                if (tableRow > -1 && tableRow < (player.getTable().getTableRows() - 1)
                        && tableColumn > -1 && tableColumn < (player.getTable().getTableColumns() - 1)){
                    if (player.getTable().getCell(tableRow,tableColumn).getShipPart() != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean shipIsDestroyed(Ship ship) {
        int partsDestroyed = 0;
        for (int i = 0; i < ship.getShip().getShipLength(); i++) {
            if (ship.getShipParts()[i].isDestroyed()){
                partsDestroyed++;

            }
        }
        if (partsDestroyed == ship.getShip().getShipLength()){
            ship.setDestroyed(true);
            return true;
        }
        return false;
    }

    private void revealCellsAroundShip(Player player,Ship ship) {
        for (int i = 0; i < ship.getShip().getShipLength(); i++) {
            for (int tableRow = ship.getShipParts()[i].getCoordRow() - 1; tableRow < ship.getShipParts()[i].getCoordRow() + 2; tableRow++) {
                for (int tableColumn = ship.getShipParts()[i].getCoordColumn() - 1; tableColumn < ship.getShipParts()[i].getCoordColumn() + 2; tableColumn++) {
                    if (tableRow > -1 && tableRow < player.getTable().getTableRows()
                     && tableColumn > -1 && tableColumn < player.getTable().getTableColumns()){
                        player.getTable().getCell(tableRow, tableColumn).setShot(true);
                    }
                }
            }
        }
    }

    private boolean shotCell(Player player) {
        int [] coordenades;
        try{
            coordenades = view.askCoordenadesToShot();
            while (player.getTable().getCell(coordenades[0],coordenades[1]).isShot()) {
                view.repeatedShot();
                coordenades = view.askCoordenadesToShot();
            }
        } catch (Exception e) {
            view.coordsOutOfTable();
            return shotCell(player);
        }
        player.getTable().getCell(coordenades[0],coordenades[1]).setShot(true);

        if (player.getTable().getCell(coordenades[0],coordenades[1]).getShipPart() != null){
            player.getTable().getCell(coordenades[0],coordenades[1]).getShipPart().setDestroyed();
            Ship ship = player.getTable().getCell(coordenades[0],coordenades[1]).getShipPart().getShip();
            if (shipIsDestroyed(ship)){
                revealCellsAroundShip(player,ship);
            }
            return true;
        }
        return false;
    }

    private void fillTableWithCells(Table game) {
        for (int i = 0; i < game.getTableRows(); i++) {
            for (int j = 0; j < game.getTableColumns(); j++) {
                game.getTable()[i][j] = new Cell();
            }
        }
    }

    private void fillShipWithParts(Ship ship) {
        for (int i = 0; i < ship.getShip().getShipLength(); i++) {
            ship.getShipParts()[i] = new ShipPart(ship);
        }
    }

}
