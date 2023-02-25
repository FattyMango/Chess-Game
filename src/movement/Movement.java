package movement;

import position.Position;

public interface Movement {
    public boolean isLegalMove(Position start, Position end);
}
