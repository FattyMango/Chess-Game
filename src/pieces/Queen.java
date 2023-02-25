package pieces;
import movement.DiagonalMovement;
import movement.LinearMovement;

public class Queen extends Piece{
    public Queen(String symbol, boolean color) {
        super(
                symbol, color);
        setMovement(new DiagonalMovement());
        setMovement(new LinearMovement());
    }





}
