package Logic;

import GameVariants.*;
import Print.GameInitializeInsert;

public class Game {
    GameInitializeInsert gameIns = new GameInitializeInsert();

    public void gameInitialize(){
        GameConstructor gameConst = new GameConstructor();
        Games gameVariant = gameIns.typeOfGameOption();
        Rummy game = gameConst.createGame(gameVariant);
        game.startGame();
    }
}
