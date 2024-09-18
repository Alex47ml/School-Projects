package com.pack.view;

import com.pack.models.Table;

import java.util.Scanner;

public class PrintGame {

    private final Scanner in = new Scanner(System.in);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public int rowUserSelected(){
        System.out.println("Que fila eliges(Lo de izquerda)?");
        return in.nextInt();
    }

    public int colUserSelected(){
        System.out.println("Que columna eliges(Lo de arriba)?");
        return in.nextInt();
    }

    public void loseScenario(){
        System.out.println("Tocaste la mina!");
    }

    public void winScenario(){
        System.out.println("Ganaste!");
    }

    public Table askUserGameOptions(){
        System.out.println("De cuantas filas quieres la taula?");
        int row = in.nextInt();
        System.out.println("De cuantas columnas quieres la taula?");
        int column = in.nextInt();
        return new Table(row,column);
    }

    public int difficultyUserSelected(){
        System.out.println("Que dificultad eliges? 1-10");
        return in.nextInt();
    }

    public int optionSelected() {
        System.out.println("""
                              1. Descubre
                              2. Marca
                              """);
        return in.nextInt();
    }

    public void printTable(Table table){
        System.out.println();
        System.out.print("     ");
        for (int i = 0; i < table.getTableColumns(); i++) {
            if (i > 9){
                System.out.print(i + " ");
            } else {
                System.out.print(i + "  ");
            }
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < table.getTableRows(); i++) {
            if (i > 9){
                System.out.print(i + "   ");
            } else {
                System.out.print(i + "    ");
            }
            for (int j = 0; j < table.getTableColumns(); j++) {
                if (table.getCell(i,j).getIsHidden()){
                    System.out.print("   ");
                } else if (table.getCell(i,j).isMine()) {
                    System.out.print(ANSI_WHITE + "@" + "  " + ANSI_RESET);
                } else if (table.getCell(i,j).getIsFlag()) {
                    System.out.print(ANSI_WHITE + "!" + "  " + ANSI_RESET);
                } else {
                    switch (table.getCell(i,j).getSurroundingMines()){
                        case 0:
                            System.out.print("   ");
                            break;
                        case 1:
                            System.out.print(ANSI_BLUE + "1  " + ANSI_RESET);
                            break;
                        case 2:
                            System.out.print(ANSI_GREEN + "2  " + ANSI_RESET);
                            break;
                        case 3:
                            System.out.print(ANSI_RED + "3  " + ANSI_RESET);
                            break;
                        case 4:
                            System.out.print(ANSI_CYAN + "4  " + ANSI_RESET);
                            break;
                        case 5:
                            System.out.print(ANSI_PURPLE + "5  " + ANSI_RESET);
                            break;
                        case 6:
                            System.out.print(ANSI_YELLOW + "6  " + ANSI_RESET);
                            break;
                        case 7:
                            System.out.print(ANSI_YELLOW + "7  " + ANSI_RESET);
                            break;
                        case 8:
                            System.out.print(ANSI_YELLOW + "8  " + ANSI_RESET);
                            break;
                    }
                }
            }
            System.out.println();
            System.out.println();
        }
    }
}
