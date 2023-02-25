package engine;

import board.Board;
import pieces.Piece;
import position.Position;

public interface Engine {
    public boolean makeMove(Position start, Position end);
    public boolean isGameOver();
    public Piece getPiece (Position position);
    public boolean getTurn ();
    public String getWinner ();
    public void setBoard(Board board);



}
