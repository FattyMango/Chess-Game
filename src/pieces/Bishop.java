package pieces;

import movement.DiagonalMovement;

public class Bishop extends Piece {
    public Bishop(String symbol, boolean color) {

        super(symbol, color);
        setMovement(new DiagonalMovement());
    }





}
