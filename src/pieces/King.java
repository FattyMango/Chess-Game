package pieces;
import movement.DiagonalMovement;
import movement.LinearMovement;
import position.*;

import java.util.Stack;

public class King extends Piece{
    public King(String symbol, boolean color) {
        super(symbol, color);
        setMovement(new DiagonalMovement());
        setMovement(new LinearMovement());
    }

    @Override
    public boolean isLegalMove(Position start, Position end) {
        int x = Math.abs(start.getRow() - end.getRow());
        int y = Math.abs(start.getColumn() - end.getColumn());
        if((x>=0 && y>=0 && x<2 && y<2))
            return super.isLegalMove(start, end);
        return false;
    }
    public Stack<Position> possibleMoves(Position start){

        int x = start.getRow()+1;
        int y = start.getColumn()+1;
        Stack<Position> stack = new Stack<Position>();
        for (int i = x-1; i<=x+1; i++){
            if(i<1 || i>8) continue;

            for (int j = y-1; j<=y+1; j++){
                if(j<1 || j>8) continue;
                Position p = new ChessPosition(i,j);
                if(!start.equals(p)) {
                    stack.push(p);

                }
            }
        }
        return stack;
    }

}
