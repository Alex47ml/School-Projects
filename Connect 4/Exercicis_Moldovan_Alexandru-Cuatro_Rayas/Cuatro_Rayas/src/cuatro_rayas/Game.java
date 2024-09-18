package cuatro_rayas;

import java.util.Scanner;

public class Game {
    private String won;
    private String lost;
    private Scanner input = new Scanner(System.in);
    private int turn = 1;
    Table table = new Table();

    public String getLost() {
        return lost;
    }

    public String getWon() {
        return won;
    }

    public void gameMenu () {
        System.out.println("Welcome to the game connect 4");
        input.nextLine();
        choosingPlayers();
        beginTheGame();

    }

    public void choosingPlayers () {
        System.out.println("First, you will need to choose who will play" );
        for (int i = 1; i < 3; i++) {
            System.out.println("Who will be the player" + i + "?");
            this.table.addPlayers(new Player(input.nextLine()));
        }
    }

    public void beginTheGame() {
        System.out.println("Begins " + table.checkPlayer(this.turn));
        while (this.won == null){
            table.drawTable2();
            System.out.println("Choose the column where you will put the token");
            int x;
            if(this.turn % 2 == 0) {
                x = 2;
            } else {
                x = 1;
            }
            int column = input.nextInt();
            table.addToken(column - 1,x);
            if (table.checkIfWin()) {
                this.won = table.checkPlayer(this.turn);
                table.drawTable2();
                System.out.println("Wins " + getWon());
                break;
            };
            this.turn++;
        }
    }

}
