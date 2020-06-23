package core.checkers.pieces;

import core.game.possible_moves_servants.IPossibleMoveServant;
import core.game.pieces.Piece;
import core.game.pieces.PieceType;

public class Pawn extends Piece {

    private int yDirection;
    private boolean canJump;

    public Pawn(int rowPosition, int columnPosition, int team, IPossibleMoveServant possibleMoveServant, int yDirection) {
        super(rowPosition, columnPosition, team, possibleMoveServant);
        this.yDirection = yDirection;
        this.canJump = false;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public int getYDirection() {
        return yDirection;
    }
}
