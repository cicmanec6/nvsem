package gui;

import core.chessboard.ChessBoard;
import core.game.pieces.PieceType;
import gui.img.PieceImageManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private ChessBoard.ChessBoardProxy chessboard;
    private PieceImageManager pieceImageManager;
    public static final int MARGIN = 0;
    public static final int TILE_DIMENSION = 70;

    public GamePanel(ChessBoard.ChessBoardProxy chessboard) {
        this.chessboard = chessboard;
        this.pieceImageManager = new PieceImageManager();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!this.chessboard.isLitUpAt(row + 1, column + 1)) {
                    if ((column + row) % 2 != 0) {
                        g2.setColor(Color.decode("#B58863"));
                    } else {
                        g2.setColor(Color.decode("#F0D9B5"));
                    }
                } else {
                    if ((column + row) % 2 != 0) {
                        g2.setColor(Color.decode("#DAC44B"));
                    } else {
                        g2.setColor(Color.decode("#F8EC74"));
                    }
                }
                g2.fillRect(MARGIN + column  * TILE_DIMENSION,
                        MARGIN + row * TILE_DIMENSION,
                        MARGIN + column  * TILE_DIMENSION + TILE_DIMENSION ,
                        MARGIN + row * TILE_DIMENSION + TILE_DIMENSION);
                PieceType pieceType = this.chessboard.getPieceTypeAt(row + 1, column + 1);
                int pieceTeam = this.chessboard.getPieceTeamAt(row + 1, column + 1);
                if (pieceType != null) {
                    g2.drawImage(this.pieceImageManager.getPieceImage(pieceTeam == 1, pieceType),
                            MARGIN + (column) * TILE_DIMENSION,
                            MARGIN + (row) * TILE_DIMENSION, null);
                }
            }
        }
        /*
        g2.drawImage(this.pozadie.getPozadieHore(), 0, 0, 800 + GrafikaPozadie.MARGIN * 2 + 20, GrafikaPozadie.MARGIN, null);
        g2.drawImage(this.pozadie.getPozadieDole(), 0, GrafikaPozadie.MARGIN + 800, 800 + GrafikaPozadie.MARGIN * 2, 640 + GrafikaPozadie.MARGIN * 2, null);
        g2.drawImage(this.pozadie.getPozadieVlavo(), 0, GrafikaPozadie.MARGIN, GrafikaPozadie.MARGIN, GrafikaPozadie.MARGIN + 800, null);
        g2.drawImage(this.pozadie.getPozadieVpravo(), GrafikaPozadie.MARGIN + 800, GrafikaPozadie.MARGIN, 800 + GrafikaPozadie.MARGIN * 2, 800 + GrafikaPozadie.MARGIN, null);
        */
    }

}
