package rule;

import board.Board;
import position.Position;

public class EnPassant implements Rule {
    public boolean checkRule(Position start, Position end, Board board) {
            return false;
    }
    @Override
    public boolean applyRule(Position start, Position end, Board board) {
        return false;
    }
}
