package spinner.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    private int[][] board;

    public Sudoku(int[][] initialBoard) {
        if (initialBoard.length == 9 && initialBoard[0].length == 9) {
            this.board = initialBoard;
        } else {
            throw new IllegalArgumentException("Invalid board size. It must be 9x9.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public List<SudokuError> getErrors() {
        List<SudokuError> errors = new ArrayList<>();
        checkRows(errors);
        checkColumns(errors);
        checkBox(errors);
        return errors;
    }

    // Check for duplicates in each row
    private void checkRows(List<SudokuError> errors) {
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[9];
            for (int j = 0; j < 9; j++) {
                int num = board[i][j];
                if (num != 0) {
                    if (seen[num - 1]) {
                        errors.add(new SudokuError(i, j, num));
                    }
                    seen[num - 1] = true;
                }
            }
        }
    }

    // Check for duplicates in each column
    private void checkColumns(List<SudokuError> errors) {
        for (int j = 0; j < 9; j++) {
            boolean[] seen = new boolean[9];
            for (int i = 0; i < 9; i++) {
                int num = board[i][j];
                if (num != 0) {
                    if (seen[num - 1]) {
                        errors.add(new SudokuError(i, j, num));
                    }
                    seen[num - 1] = true;
                }
            }
        }
    }

    // Check for duplicates in each box
    private void checkBox(List<SudokuError> errors) {
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                boolean[] seen = new boolean[9];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int num = board[row + i][col + j];
                        if (num != 0) {
                            if (seen[num - 1]) {
                                int boxNumber = (row / 3) * 3 + (col / 3 + 1);
                                errors.add(new SudokuError(row, col, num));
                            }
                            seen[num - 1] = true;
                        }
                    }
                }
            }
        }
    }
}