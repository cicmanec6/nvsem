package core.checkers.possible_moves_servants;

import core.checkers.pieces.Pawn;
import core.chessboard.ChessBoard;
import core.common.Validator;
import core.game.possible_moves_servants.IPossibleMoveServant;
import core.game.pieces.Piece;

public class PawnPossibleMoveServant implements IPossibleMoveServant {

    public PawnPossibleMoveServant() {
    }

    @Override
    public void setPossibleMoves(Piece piece) {
        Pawn pawn = (Pawn) piece;

        int yDirection = pawn.getYDirection();
        ChessBoard chessBoard = ChessBoard.getInstance();
        int rowPosition = piece.getRowPosition();
        int columnPosition = piece.getColumnPosition();
        int team = pawn.getTeam();

        pawn.setCanJump(false);
        if (chessBoard.getPieceAt(rowPosition + yDirection, columnPosition + 1) != null &&
                chessBoard.getPieceAt(rowPosition + yDirection, columnPosition + 1).getTeam() != team &&
                Validator.isGoodCoordination(rowPosition + 2 * yDirection, columnPosition + 2) &&
                chessBoard.getPieceAt(rowPosition + 2 * yDirection, columnPosition + 2) == null) {
            pawn.setPossibleMoveIfIsEmpty(rowPosition + 2 * yDirection, columnPosition + 2);
            pawn.setCanJump(true);
        }

        if (chessBoard.getPieceAt(rowPosition + yDirection, columnPosition - 1) != null &&
                chessBoard.getPieceAt(rowPosition + yDirection, columnPosition - 1).getTeam() != team &&
                Validator.isGoodCoordination(rowPosition + 2 * yDirection, columnPosition - 2) &&
                chessBoard.getPieceAt(rowPosition + 2 * yDirection, columnPosition - 2) == null) {
            pawn.setPossibleMoveIfIsEmpty(rowPosition + 2 * yDirection, columnPosition - 2);
            pawn.setCanJump(true);
        }

        if (!pawn.isCanJump()) {
            pawn.setPossibleMoveIfIsEmpty(rowPosition + yDirection, columnPosition + 1);
            pawn.setPossibleMoveIfIsEmpty(rowPosition + yDirection, columnPosition - 1);
        }
    }
}
