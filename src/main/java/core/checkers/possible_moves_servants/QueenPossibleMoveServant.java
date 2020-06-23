package core.checkers.possible_moves_servants;

import core.checkers.pieces.Queen;
import core.game.possible_moves_servants.IPossibleMoveServant;
import core.game.pieces.Piece;

public class QueenPossibleMoveServant implements IPossibleMoveServant {

    public QueenPossibleMoveServant() {
    }

    @Override
    public void setPossibleMoves(Piece piece) {
        Queen queen = (Queen) piece;

        queen.setCanJump(false);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 || j == 0)) {
                    queen.setCanJump(queen.isCanJump() || queen.canJumpInDirection(i, j));
                }
            }
        }

        if (!queen.isCanJump()) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (!(i == 0 || j == 0)) {
                        queen.checkDirections(i, j, false);
                    }
                }
            }
        }
    }
}
