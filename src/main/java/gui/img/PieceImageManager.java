package gui.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import core.game.pieces.PieceType;
import gui.GamePanel;
import net.coobird.thumbnailator.Thumbnails;

/**
 * Tato trieda zabezpecuje nacitavanie obrazkov figuriek.
 * @version 15.05.2016
 * @author Jakub Cicmanec cicmanec6@stud.uniza.sk
 */
public class PieceImageManager {
    private BufferedImage obrazokVsetkychFiguriek;

    public PieceImageManager() {
        try {
            this.obrazokVsetkychFiguriek = ImageIO.read(new File("img/figurky.png"));
        } catch (IOException ex) {
            System.out.println("Subor sa nenasiel");
        }
    }

    public BufferedImage getPieceImage(boolean isBlack, PieceType pieceType) {
        BufferedImage vratenyObrazok = null;
        int y;
        if (!isBlack) {
            y = 0;
        } else {
            y = 100;
        }
        if (pieceType == PieceType.KING) {
            vratenyObrazok = this.obrazokVsetkychFiguriek.getSubimage(0, y, 100, 100);
        } else if (pieceType == PieceType.QUEEN) {
            vratenyObrazok = this.obrazokVsetkychFiguriek.getSubimage(100, y, 100, 100);
        } else if (pieceType == PieceType.BISHOP) {
            vratenyObrazok = this.obrazokVsetkychFiguriek.getSubimage(200, y, 100, 100);
        } else if (pieceType == PieceType.KNIGHT) {
            vratenyObrazok = this.obrazokVsetkychFiguriek.getSubimage(300, y, 100, 100);
        } else if (pieceType == PieceType.ROOK) {
            vratenyObrazok = this.obrazokVsetkychFiguriek.getSubimage(400, y, 100, 100);
        } else if (pieceType == PieceType.PAWN) {
            vratenyObrazok = this.obrazokVsetkychFiguriek.getSubimage(500, y, 100, 100);
        }
        BufferedImage retImage = null;
        try {
            retImage = Thumbnails.of(vratenyObrazok)
                    .size(GamePanel.TILE_DIMENSION, GamePanel.TILE_DIMENSION)
                    .asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retImage;
    }





}
