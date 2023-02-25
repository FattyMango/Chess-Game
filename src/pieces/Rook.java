package pieces;
import movement.LinearMovement;

public class Rook extends Piece{

    public Rook(String symbol, boolean color) {
        super(symbol, color);
        setMovement(new LinearMovement());
    }

}
