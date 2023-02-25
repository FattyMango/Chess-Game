package rule;

import board.Board;
import position.Position;

public interface Rule {

    public boolean applyRule(Position start, Position end, Board board);



}
