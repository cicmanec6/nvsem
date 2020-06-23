package core.chessboard;

import core.common.Validator;
import core.game.pieces.Piece;
import core.game.pieces.PieceType;

public class ChessBoard {
    private static ChessBoard ourInstance = new ChessBoard();
    public static final int rowsCount = 8;
    public static final int columnsCount = 8;


    public static ChessBoard getInstance() {
        return ourInstance;
    }

    private ChessBoardProxy proxy;
    private Piece[][] boardPieces;

    private ChessBoard() {
        this.proxy = new ChessBoardProxy();
        this.boardPieces = new Piece[rowsCount][columnsCount];
        for (int i = 0; i < this.boardPieces.length; i++) {
            for (int j = 0; j < this.boardPieces[i].length; j++) {
                this.boardPieces[i][j] = null;
            }
        }
    }

    public ChessBoardProxy getProxy() {
        return proxy;
    }

    public Piece getPieceAt(int row, int column) {
        if (!Validator.isGoodCoordination(row, column)) {
            return null;
        }
        return this.boardPieces[row - 1][column - 1];
    }

    public void placePieceOnBoard(Piece piece) {
        Validator.checkCoordinations(piece.getRowPosition(), piece.getColumnPosition());
        this.boardPieces[piece.getRowPosition() - 1][piece.getColumnPosition() - 1] = piece;
        this.proxy.getSquareStateAt(piece.getRowPosition(), piece.getColumnPosition()).setOccupancyAndTeam(piece.getPieceType(), piece.getTeam());
    }

    public void removePieceFromBoard(int row, int column) {
        Validator.checkCoordinations(row, column);
        this.boardPieces[row - 1][column - 1] = null;
        this.proxy.getSquareStateAt(row, column).setOccupancyAndTeam(null, -1);
    }

    public Piece selectPiece(int row, int column) {
        if (Validator.isGoodCoordination(row, column)) {
            Piece piece = this.getPieceAt(row, column);
            if (piece != null) {
                for (int i = 0; i < rowsCount; i++) {
                    for (int j = 0; j < columnsCount; j++) {
                        this.proxy.getSquareStateAt(i + 1, j + 1).
                        setLitUp(piece.canMoveTo(i + 1, j + 1));
                    }
                }
                return piece;
            }
        }
        return null;
    }

    public void dim() {
        this.proxy.dim();
    }

    public void clear() {
        for (int i = 0; i < this.boardPieces.length; i++) {
            for (int j = 0; j < this.boardPieces[i].length; j++) {
                this.boardPieces[i][j] = null;
            }
        }
        this.proxy.clear();
    }

    public class ChessBoardProxy {
        private SquareState[][] states;

        public ChessBoardProxy() {
            this.states = new SquareState[rowsCount][columnsCount];
            for (int i = 0; i < this.states.length; i++) {
                for (int j = 0; j < this.states[i].length; j++) {
                    this.states[i][j] = new SquareState();
                }
            }
        }

        protected void clear() {
            for (SquareState[] squaresRow: this.states) {
                for (SquareState square: squaresRow) {
                    square.clear();
                }
            }
        }

        protected void dim() {
            for (SquareState[] squaresRow: this.states) {
                for (SquareState square: squaresRow) {
                    square.setLitUp(false);
                }
            }
        }

        protected SquareState getSquareStateAt(int row, int column) {
            Validator.checkCoordinations(row, column);
            return this.states[row - 1][column - 1];
        }

        public boolean isLitUpAt(int row, int column) {
            Validator.checkCoordinations(row, column);
            return this.states[row - 1][column - 1].isLitUp();
        }

        public PieceType getPieceTypeAt(int row, int column) {
            Validator.checkCoordinations(row, column);
            return this.states[row - 1][column - 1].getOccupancy();
        }

        public int getPieceTeamAt(int row, int column) {
            Validator.checkCoordinations(row, column);
            return this.states[row - 1][column - 1].getTeam();
        }

    }
}
