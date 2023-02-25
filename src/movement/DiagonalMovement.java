package movement;

import position.Position;

public class DiagonalMovement implements Movement {
    @Override
    public boolean isLegalMove(Position start, Position end) {
        int x = Math.abs(start.getRow() - end.getRow());
        int y = Math.abs(start.getColumn() - end.getColumn());
        return !start.equals(end) && (x !=0  && x == y);

    }
}
