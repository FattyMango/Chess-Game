package pieces;

import movement.Movement;
import position.Position;

import java.util.ArrayList;

public abstract class Piece {
    private final String symbol;
    private final boolean color;
    private boolean firstMove;
    private ArrayList<Movement> movements;

    public Piece (String symbol, boolean color){
        this.symbol = symbol;
        this.color = color;
        this.movements = new ArrayList<Movement>();
        this.firstMove = true;
    }
    public void setMovement(Movement movement) {
        this.movements.add(movement);
    }
    public boolean isLegalMove(Position start, Position end){
        if(!movements.isEmpty())
            for(Movement movement : movements)
                if (movement.isLegalMove(start, end))
                    return true;

        return false;
    }
    public  boolean canAttackPiece(Position start, Position end){
        return isLegalMove(start , end);};
    public void setFirstMove(boolean firstMove){
        this.firstMove = firstMove;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public boolean getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }






    @Override
    public String toString() {
        if(getColor())
            return "W".concat(symbol);
        return "B".concat(symbol);
    }
}
