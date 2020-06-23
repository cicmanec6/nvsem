package core.game;

import core.checkers.Checkers;
import core.chessboard.ChessBoard;

public class GameCreator {

    private static ChessBoard chessBoard = ChessBoard.getInstance();

    private static Game createGame(Game game) {
        chessBoard.clear();
        game.placePiecesOnChessBoard();
        game.updatePiecesMoves();
        return game;
    }

    public static Game createCheckersGame() {
        return createGame(new Checkers());
    }


}
