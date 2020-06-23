package core.checkers;

import core.game.Game;
import core.game.pieces.Piece;
import core.checkers.pieces.Pawn;
import core.checkers.pieces.Queen;
import core.checkers.possible_moves_servants.PawnPossibleMoveServant;
import core.checkers.possible_moves_servants.QueenPossibleMoveServant;

import java.util.ArrayList;
import java.util.List;

public class Checkers extends Game {

    private int teamOnTurn;
    private List<Piece> listBlacks;
    private List<Piece> listWhites;

    public Checkers() {
        super();
        this.teamOnTurn = 0;
        this.listBlacks = new ArrayList<>(8);
        this.listWhites = new ArrayList<>(8);
    }

    @Override
    protected String checkGameEndingConditions() {
        if (this.listWhites.isEmpty()) {
            return "Vyhrali cierni.";
        }
        if (this.listBlacks.isEmpty()) {
            return "Vyhrali bieli";
        }
        List<Piece> listOnTurn = this.teamOnTurn == 0 ? this.listWhites : this.listBlacks;
        boolean hasMove = false;
        for (Piece piece: listOnTurn) {
            hasMove = hasMove || piece.hasMove();
        }
        if (!hasMove) {
            String teamOnTurn = this.teamOnTurn == 0 ? "Bieli" : "Cierni";
            return "Na rade su " + teamOnTurn + " a nemaju tahu, remiza!";
        }

        return null;
    }

    @Override
    public void placePiecesOnChessBoard() {
        //0 - bieli 1 - cierni

        for (int i = 0; i < 8; i++) {
            int rowWhite  = i % 2 == 0 ? 8 : 7;
            int rowBlack = i % 2 == 0 ? 2 : 1;
            Pawn blackPawn = new Pawn(rowBlack, i + 1, 1, new PawnPossibleMoveServant(), 1);
            Pawn whitePawn = new Pawn(rowWhite, i + 1, 0, new PawnPossibleMoveServant(), -1);
            this.chessBoard.placePieceOnBoard(whitePawn);
            this.chessBoard.placePieceOnBoard(blackPawn);
            this.listBlacks.add(blackPawn);
            this.listWhites.add(whitePawn);
        }

        /*
        Pawn blackPawn = new Pawn(1, 8, 1, 1);
        Pawn blackPawn2 = new Pawn(1, 6, 1, 1);
        Pawn whitePawn = new Pawn(4, 7, 0, -1);
        this.chessBoard.placePieceOnBoard(whitePawn);
        this.chessBoard.placePieceOnBoard(blackPawn);
        this.chessBoard.placePieceOnBoard(blackPawn2);
        this.listBlacks.add(blackPawn);
        this.listBlacks.add(blackPawn2);
        this.listWhites.add(whitePawn);
        */

    }

    @Override
    protected boolean checkSelectionCondition(Piece piece) {
        boolean basicCondition = piece.getTeam() == this.teamOnTurn;
        List<Piece> listOnTurn = this.teamOnTurn == 0 ? this.listWhites : this.listBlacks;
        boolean hasPawnThatCanJump = false;
        for (Piece loopedPiece: listOnTurn) {
            if (loopedPiece instanceof Queen) {
                Queen queen = (Queen) loopedPiece;
                if (queen.isCanJump()) {
                    return piece instanceof Queen && ((Queen) piece).isCanJump() && basicCondition;
                }
            } else {
                Pawn pawn = (Pawn) loopedPiece;
                hasPawnThatCanJump = hasPawnThatCanJump || pawn.isCanJump();
            }
        }

        if (hasPawnThatCanJump) {
            return piece instanceof Pawn && ((Pawn) piece).isCanJump() && basicCondition;
        }

        return piece.getTeam() == this.teamOnTurn;
    }

    @Override
    protected String afterPieceMoved(int previousRow, int previousColumn) {
        boolean jumped = this.checkPieceRemoval(previousRow, previousColumn);
        this.updatePiecesMoves();
        this.selectedPiece.updatePossibleMoves();
        boolean canJumpAgain =
                this.selectedPiece instanceof Pawn ? ((Pawn) this.selectedPiece).isCanJump() :
                    ((Queen) this.selectedPiece).isCanJump();
        super.selectionLocked = jumped && canJumpAgain;
        this.checkChange();
        if (!super.selectionLocked) {
            this.deselectPiece();
            this.teamOnTurn = this.teamOnTurn == 0 ? 1 : 0;
        } else {
            //svietenie sachovnice
            this.chessBoard.selectPiece(this.selectedPiece.getRowPosition(), this.selectedPiece.getColumnPosition());
        }
        return this.checkGameEndingConditions();

    }

    private boolean checkPieceRemoval(int previousRow, int previousColumn) {
        boolean jumped = false;
        if (this.selectedPiece instanceof Pawn) {
            //este pred updatovanim povolenych tahov takze predtym skocil
            jumped = ((Pawn) this.selectedPiece).isCanJump();
        } else if (this.selectedPiece instanceof Queen) {
            jumped = ((Queen) this.selectedPiece).isCanJump();
        }
        if (jumped) {
            int dirX = this.selectedPiece.getColumnPosition() > previousColumn ? 1 : -1;
            int dirY = this.selectedPiece.getRowPosition() > previousRow ? 1 : -1;

            int nextColumn = previousColumn + dirX;
            int nextRow = previousRow + dirY;
            while (this.selectedPiece.getColumnPosition() != nextColumn) {
                Piece pieceBetween = super.chessBoard.getPieceAt(nextRow, nextColumn);
                if (pieceBetween != null) {
                    super.chessBoard.removePieceFromBoard(nextRow, nextColumn);
                    if (pieceBetween.getTeam() == 0) {
                        this.listWhites.remove(pieceBetween);
                    } else {
                        this.listBlacks.remove(pieceBetween);
                    }

                    return true;
                }
                nextRow += dirY;
                nextColumn += dirX;
            }
        }
        return false;
    }

    private void checkChange() {
        if (this.selectedPiece instanceof Pawn) {
            Pawn selectedPawn = (Pawn)this.selectedPiece;
            int destinationRow = selectedPawn.getYDirection() == 1 ? 8 : 1;
            if (selectedPawn.getRowPosition() == destinationRow) {
                Queen queen = new Queen(selectedPawn, new QueenPossibleMoveServant());
                if (selectedPawn.getTeam() == 0) {
                    this.listWhites.remove(selectedPawn);
                    this.listWhites.add(queen);
                } else {
                    this.listBlacks.remove(selectedPawn);
                    this.listBlacks.add(queen);
                }
            }
        }
    }

}
