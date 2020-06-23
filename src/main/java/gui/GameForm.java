package gui;

import core.game.Game;
import core.game.GameCreator;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameForm extends MouseAdapter {

    private final Game game = GameCreator.createCheckersGame();
    private final JFrame frame;
    private final JPanel gamePanel;

    public GameForm() {
        this.frame = new JFrame();
        this.gamePanel = new GamePanel(game.getChessBoard());
        this.frame.setSize(GamePanel.TILE_DIMENSION * 8, GamePanel.TILE_DIMENSION * 8 + 20);
        this.frame.setTitle("Dama");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.add(gamePanel);
        this.frame.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int row = (y - GamePanel.MARGIN - 24) / GamePanel.TILE_DIMENSION + 1;
        int column = ((x - GamePanel.MARGIN - 0) / GamePanel.TILE_DIMENSION) + 1;


        if (this.game.pieceIsSelected()) {
            String ret = this.game.moveSelectedPieceTo(row, column);
            if (ret != null) {
                JOptionPane.showMessageDialog(frame, ret);
            }
            this.game.deselectPiece();
        } else {
            this.game.selectPiece(row, column);
        }

        this.gamePanel.repaint();
    }

    public void show() {
        this.frame.setVisible(true);
    }

}
