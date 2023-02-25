package board;

import pieces.Piece;
import position.Position;

import java.util.LinkedList;
import java.util.Stack;

public interface Board {
    public Piece getPiece(Position position);
    public boolean setPiece(Position position, Piece piece);
    public boolean removePiece(Position position);
    public Stack<Position> availablePositions();
    public boolean isPositionOccupied(Position position);
    public Position getBlackKingPosition();
    public Position getWhiteKingPosition();
    public Stack<Position> getWhitePieces();
    public Stack<Position> getBlackPieces();
    public LinkedList<Piece> getCapturedPieces();

}
