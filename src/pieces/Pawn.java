package pieces;
import position.Position;



public class Pawn extends Piece{

    public Pawn(String symbol, boolean color) {
        super(symbol, color);

    }

    @Override
    public boolean isLegalMove(Position start, Position end) {

        int x = start.getRow() - end.getRow();
        int y = start.getColumn() - end.getColumn();

        if (y==0) {
            if (getColor())
                return x == -1 || (x == -2 && isFirstMove());

            else
                return x == 1 || (x == 2 && isFirstMove());
        }
        return false;
    }

    @Override
    public boolean canAttackPiece(Position start, Position end) {
        int x = start.getRow() - end.getRow();
        int y = Math.abs( start.getColumn() - end.getColumn());

        if (getColor())
            return x == -1 && y == 1;

        return x == 1 && y == 1;


    }


}
