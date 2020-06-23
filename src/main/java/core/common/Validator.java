package core.common;

public class Validator {
    public static void checkCoordinations(int row, int column) {
        if ((row > 8 || row < 1) && (column > 8 || column < 1)) {
            throw new Error("Unexpected coordinations, rows and columns must be in <1;8> interval.");
        }
    }

    public static boolean isGoodCoordination(int row, int column) {
        return row < 9 && row > 0 && column < 9 && column > 0;
    }

}
