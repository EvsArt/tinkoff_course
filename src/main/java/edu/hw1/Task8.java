package edu.hw1;

public final class Task8 {

    private Task8() {
    }

    @SuppressWarnings("MagicNumber")
    public static boolean knightBoardCapture(int[][] board) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (board[y][x] == 1
                    && (knightCanKillForVertical(x, y, board)
                    || knightCanKillForHorizontal(x, y, board))) {
                    return false;
                }
            }
        }

        return true;

    }

    @SuppressWarnings("MagicNumber")
    private static boolean knightCanKillForVertical(int x, int y, int[][] board) {
        if (y >= 2) {
            if (x >= 1 && board[y - 2][x - 1] == 1
                || x <= 6 && board[y - 2][x + 1] == 1) {
                return true;
            }
        }
        if (y <= 5) {
            if (x >= 1 && board[y + 2][x - 1] == 1
                || x <= 6 && board[y + 2][x + 1] == 1) {
                return true;
            }
        }

        return false;

    }

    @SuppressWarnings("MagicNumber")
    private static boolean knightCanKillForHorizontal(int x, int y, int[][] board) {
        if (x >= 2) {
            if (y >= 1 && board[y - 1][x - 2] == 1
                || y <= 6 && board[y + 1][x - 2] == 1) {
                return true;
            }
        }
        if (x <= 5) {
            if (y >= 1 && board[y - 1][x + 2] == 1
                || y <= 6 && board[y + 1][x + 2] == 1) {
                return true;
            }
        }

        return false;

    }

}
