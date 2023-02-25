package movement;

import position.Position;

public class LinearMovement implements Movement {
    @Override
    public boolean isLegalMove(Position start, Position end) {
        int x = Math.abs(start.getRow() - end.getRow());
        int y = Math.abs(start.getColumn() - end.getColumn());

        if (x == 0 && y >0 && y<8 )
            return true;
        else
            return !start.equals(end) && (y == 0 && x > 0 && x < 8);
    }
}
