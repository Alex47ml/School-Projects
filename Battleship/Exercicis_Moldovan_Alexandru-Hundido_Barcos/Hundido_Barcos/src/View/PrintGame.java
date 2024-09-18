package View;

import Model.Player;
import Model.Ship;
import Model.ShipEnum;
import Model.Table;

import java.util.Scanner;

public class PrintGame {

    private final Scanner in = new Scanner(System.in);

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";


    public int[] askCoordenadesToUser(){
        int[] coordenades = new int[4];
        System.out.println("Elige donde quieres colocar el barco(dime la fila de la coordenada 1)");
        coordenades[0] = in.nextInt() - 1;

        System.out.println("Elige donde quieres colocar el barco(dime la columna de la coordenada 1)");
        coordenades[1] = in.nextInt() - 1;

        System.out.println("Elige donde quieres colocar el barco(dime la fila de la coordenada 2)");
        coordenades[2] = in.nextInt() - 1;

        System.out.println("Elige donde quieres colocar el barco(dime la columna de la coordenada 2)");
        coordenades[3] = in.nextInt() - 1;

        return coordenades;
    }

    public int[] askCoordenadesToShot(){
        int[] coordenades = new int[2];
        System.out.println("Elige donde quieres disparar(dime la fila)");
        coordenades[0] = in.nextInt() - 1;

        System.out.println("Elige donde quieres disparar(dime la columna)");
        coordenades[1] = in.nextInt() - 1;

        return coordenades;
    }

    public void failureInsert(){
        System.out.println("Ha ido mal, prueba de nuevo");
    }

    public void colocateShip(ShipEnum ship, int shipNumber){
        System.out.println("Coloca el barco: " + ship + " " + shipNumber);
    }

    public boolean satisfied(){
        System.out.println("Estas satisfecho con los barcos colocados? Si: 1 | No: 2");
        return in.nextInt() == 1;
    }

    public int generateOption(){
        System.out.println("""
                Para generar los barcos automaticamente pulsa 1.
                Para generar los barcos manualmente pulsa 2.
                """);
        return in.nextInt();
    }

    public void winScenario(String playerName){
        System.out.println("Ha ganado " + playerName + "!");
    }

    public void turnOfPlayer(Player player){
        System.out.println("Le toca a " + player.getName());
    }

    public void repeatedShot(){
        System.out.println();
        System.out.println("Disparaste en la misma casilla, tienes que volver a disparar!");
    }

    public void coordsOutOfTable(){
        System.out.println("Disparaste fuera de la taula, vuelva a introducir las coordenadas");
    }

    public String askPlayerName(int playerNumber){
        System.out.println("Dime el nombre del jugador " + playerNumber);
        return in.nextLine();
    }

    public void printTable(Table table,boolean mostrarTodo){
        System.out.println();
        System.out.print("     ");
        for (int i = 1; i < table.getTableColumns() + 1; i++) {
            if (i > 9){
                System.out.print(i + "   ");
            } else {
                System.out.print(i + "    ");
            }
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < table.getTableRows(); i++) {
            if (i > 8){
                System.out.print(i + 1 + "   ");
            } else {
                System.out.print(i + 1 + "    ");
            }
            for (int j = 0; j < table.getTableColumns(); j++) {
                if (!table.getCell(i,j).isShot() && !mostrarTodo){
                    System.out.print("     ");
                } else {
                    if (table.getCell(i,j).getShipPart() != null && table.getCell(i,j).isShot()){
                        System.out.print("☒    ");
                    } else if (table.getCell(i,j).getShipPart() == null && table.getCell(i,j).isShot()) {
                        System.out.print("∅    ");;
                    } else if (table.getCell(i,j).getShipPart() != null) {
                        System.out.print("☐    ");
                    } else if (table.getCell(i,j).getShipPart() == null) {
                        System.out.print("     ");
                    }
                }
            }
            System.out.println();
            System.out.println();
        }
    }
}