package pieces;
import movement.KnightMovement;


public class Knight extends Piece{
    public Knight(String symbol, boolean color) {
        super(symbol, color);
        setMovement(new KnightMovement());
    }




}
