package GameVariants;

public class GameConstructor {

    public Rummy createGame(Games game){
        switch (game){
            case RummyClassic:
                return new RummyClassic();
            case Rummikub:
                return new Rummikub();
            case GinRummy:
                return new GinRummy();
            case RummyArgentino:
                return new RummyArgentino();
            default:
                return null;
        }
    }
}
