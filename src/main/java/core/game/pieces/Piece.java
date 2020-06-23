package core.game.pieces;

import core.chessboard.ChessBoard;
import core.common.Validator;
import core.game.possible_moves_servants.IPossibleMoveServant;

public abstract class Piece {

    protected ChessBoard chessBoard = ChessBoard.getInstance();
    protected int rowPosition;
    protected int columnPosition;
    private boolean[][] possibleMoves;
    protected final int team;
    private IPossibleMoveServant possibleMoveServant;

    public Piece(int rowPosition, int columnPosition, int team, IPossibleMoveServant possibleMoveServant) {
        Validator.checkCoordinations(rowPosition, columnPosition);
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
        this.team = team;
        this.possibleMoves = new boolean[ChessBoard.rowsCount][ChessBoard.columnsCount];
        this.chessBoard.placePieceOnBoard(this);
        this.possibleMoveServant = possibleMoveServant;
    }

    public abstract PieceType getPieceType();
    private void fetchPossibleMoves() {
        this.possibleMoveServant.setPossibleMoves(this);
    }

    public final int getTeam() {
        return team;
    }

    public final void updatePossibleMoves() {
        this.resetMoves();
        this.fetchPossibleMoves();
    }

    private final void resetMoves() {
        for (int i = 0; i < this.possibleMoves.length; i++) {
            for (int j = 0; j < this.possibleMoves[i].length; j++) {
                this.possibleMoves[i][j] = false;
            }
        }
    }

    public final boolean move(int row, int column) {
        if (Validator.isGoodCoordination(row, column) && this.canMoveTo(row, column)) {
            this.chessBoard.removePieceFromBoard(this.rowPosition, this.columnPosition);
            this.rowPosition = row;
            this.columnPosition = column;
            this.chessBoard.placePieceOnBoard(this);
            return true;
        }
        return false;
    }

    protected final void setPossibleMove(int row, int column, boolean possible) {
        if (Validator.isGoodCoordination(row, column)) {
            this.possibleMoves[row - 1][column - 1] = possible;
        }
    }

    public final void checkDirections(int vectX, int vectY, boolean canReplaceEnemy) {
        int dirX = vectX;
        int dirY = vectY;

        while (Validator.isGoodCoordination(this.rowPosition + vectX, this.columnPosition + vectY) &&
                this.chessBoard.getPieceAt(this.rowPosition + vectX, this.columnPosition + vectY) == null) {
            this.setPossibleMove(this.rowPosition + vectX, this.columnPosition + vectY, true);
            vectX += dirX;
            vectY += dirY;
        }
        if (Validator.isGoodCoordination(this.rowPosition + vectX, this.columnPosition + vectY) &&
            this.chessBoard.getPieceAt(this.rowPosition + vectX, this.columnPosition + vectY).getTeam() != this.team &&
                canReplaceEnemy) {
            this.setPossibleMove(this.rowPosition + vectX, this.columnPosition + vectY, true);
        }
    }

    public final boolean canJumpInDirection(int vectX, int vectY) {
        int dirX = vectX;
        int dirY = vectY;

        while (Validator.isGoodCoordination(this.rowPosition + vectX, this.columnPosition + vectY) &&
                this.chessBoard.getPieceAt(this.rowPosition + vectX, this.columnPosition + vectY) == null) {
            vectX += dirX;
            vectY += dirY;
        }
        if (Validator.isGoodCoordination(this.rowPosition + vectX, this.columnPosition + vectY) &&
            this.chessBoard.getPieceAt(this.rowPosition + vectX, this.columnPosition + vectY).getTeam() != this.team &&
            Validator.isGoodCoordination(this.rowPosition + vectX + dirX, this.columnPosition + vectY + dirY) &&
            this.chessBoard.getPieceAt(this.rowPosition + vectX + dirX, this.columnPosition + vectY + dirY) == null
                ) {
            this.setPossibleMove(this.rowPosition + vectX + dirX, this.columnPosition + vectY + dirY, true);
            return true;
        }
        return false;
    }


    public final boolean hasMove() {
        for (int i = 0; i < this.possibleMoves.length; i++) {
            for (int j = 0; j < this.possibleMoves[i].length; j++) {
                if (this.possibleMoves[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }


    public final void setPossibleMoveIfIsEmpty(int row, int column) {
        if (Validator.isGoodCoordination(row, column)) {
            if (this.chessBoard.getPieceAt(row, column) == null) {
                this.setPossibleMove(row, column, true);
            }
        }
    }

    public final boolean canMoveTo(int row, int column) {
        if (Validator.isGoodCoordination(row, column)) {
            return this.possibleMoves[row - 1][column - 1];
        }
        return false;
    }

    public final int getRowPosition() {
        return rowPosition;
    }

    public final int getColumnPosition() {
        return columnPosition;
    }

    //public abstract void afterPieceWasMoved(int prevRow, int prevColumn);
}
