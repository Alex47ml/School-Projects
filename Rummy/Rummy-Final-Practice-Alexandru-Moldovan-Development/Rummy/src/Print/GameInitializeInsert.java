package Print;

import GameVariants.Games;

import java.util.Scanner;

public class GameInitializeInsert {
    private final Scanner in = new Scanner(System.in);

    public Games typeOfGameOption(){
        while (true){
            System.out.println("""
            Si quieres jugar Rummy normal pulsa 1
            Si quieres jugar Rummikub pulsa 2
            Si quieres jugar Gin Rummy pulsa 3
            Si quieres jugar Rummy Argentino pulsa 4
            """);
            switch (in.nextInt()){
                case 1:
                    return Games.RummyClassic;
                case 2:
                    return Games.Rummikub;
                case 3:
                    return Games.GinRummy;
                case 4:
                    return Games.RummyArgentino;
                default:
                    System.out.println("Algo ha ido mal, prueba de nuevo");
            }
        }
    }
}
