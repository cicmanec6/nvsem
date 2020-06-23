package core.chessboard;

import core.game.pieces.PieceType;

public class SquareState {

    private PieceType occupancy;
    private boolean isLitUp;
    private int team;

    public SquareState() {
        this.occupancy = null;
        this.isLitUp = false;
        this.team = -1;
    }

    public PieceType getOccupancy() {
        return occupancy;
    }

    public void setOccupancyAndTeam(PieceType occupancy, int team) {
        this.occupancy = occupancy;
        this.team = team;
    }

    public boolean isLitUp() {
        return isLitUp;
    }

    public void setLitUp(boolean litUp) {
        isLitUp = litUp;
    }

    public int getTeam() {
        return team;
    }

    public void clear() {
        this.occupancy = null;
        this.isLitUp = false;
    }

}
