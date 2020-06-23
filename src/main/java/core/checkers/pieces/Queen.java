package core.checkers.pieces;

import core.game.possible_moves_servants.IPossibleMoveServant;
import core.game.pieces.Piece;
import core.game.pieces.PieceType;

public class Queen extends Piece {

    private boolean canJump;

    public Queen(int rowPosition, int columnPosition, int team, IPossibleMoveServant possibleMoveServant) {
        super(rowPosition, columnPosition, team, possibleMoveServant);
    }

    public Queen(Pawn pawn, IPossibleMoveServant possibleMoveServant) {
        super(pawn.getRowPosition(), pawn.getColumnPosition(), pawn.getTeam(), possibleMoveServant);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

}
