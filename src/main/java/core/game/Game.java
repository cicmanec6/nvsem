package core.game;

import core.chessboard.ChessBoard;
import core.common.Validator;
import core.game.pieces.Piece;

public abstract class Game {

    protected ChessBoard chessBoard = ChessBoard.getInstance();
    protected Piece selectedPiece;
    protected boolean selectionLocked;

    public Game() {
        this.selectionLocked = false;
    }

    public void selectPiece(int row, int column) {
        if (Validator.isGoodCoordination(row, column)) {
            Piece piece = this.chessBoard.getPieceAt(row, column);
            if (piece != null && this.checkSelectionCondition(piece)) {
                this.selectedPiece = this.chessBoard.selectPiece(row, column);
            }
        } else {
            this.deselectPiece();
        }
    }

    public void deselectPiece() {
        if (!this.selectionLocked) {
            this.chessBoard.dim();
            this.selectedPiece = null;
        }
    }

    public String moveSelectedPieceTo(int row, int column) {
        int prevRow = this.selectedPiece != null ? this.selectedPiece.getRowPosition() : -1;
        int prevColumn = this.selectedPiece != null ? this.selectedPiece.getColumnPosition() : -1;
        boolean moved = this.selectedPiece != null && this.selectedPiece.move(row, column);
        if (moved) {
            return afterPieceMoved(prevRow, prevColumn);
        }
        return null;
    }

    public ChessBoard.ChessBoardProxy getChessBoard() {
        return chessBoard.getProxy();
    }

    public boolean pieceIsSelected() {
        return this.selectedPiece !=  null;
    }

    /*
     standardne spravanie - zrusi sa vybrana figurka, vsetkym sa prenastavia povolene tahy a skontroluju sa podmienky skoncenia hry
     */
    protected String afterPieceMoved(int previousRow, int previousColumn) {
        this.deselectPiece();
        this.updatePiecesMoves();
        return this.checkGameEndingConditions();

    }

    protected abstract String checkGameEndingConditions();

    public abstract void placePiecesOnChessBoard();

    public final void updatePiecesMoves() {
        for (int i = 0; i < ChessBoard.rowsCount; i++) {
            for (int j = 0; j < ChessBoard.columnsCount; j++) {
                Piece piece = this.chessBoard.getPieceAt(i + 1, j + 1);
                if (piece != null) {
                    piece.updatePossibleMoves();
                }
            }
        }
    }

    protected boolean checkSelectionCondition(Piece piece) {
        return true;
    }

    public boolean isSelectionLocked() {
        return selectionLocked;
    }
}
