import org.junit.jupiter.api.Test;

import spinner.sudoku.Sudoku;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SudokuTest {
    private int[][] exampleBoard = {
            {1, 2, 3, 4, 5, 6, 7, 8, 1},
            {4, 1, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 1, 4, 3, 6, 5, 8, 9, 7},
            {3, 6, 5, 8, 9, 7, 2, 1, 4},
            {8, 9, 7, 2, 1, 4, 3, 6, 5},
            {5, 3, 1, 6, 4, 2, 9, 7, 8},
            {6, 4, 2, 9, 7, 8, 5, 3, 1},
            {9, 7, 8, 5, 3, 1, 6, 4, 2}
    };

    @Test
    public void testBoardErrors() {
        Sudoku sudoku = new Sudoku(exampleBoard);
        List<String> errors = sudoku.getErrors();

        for (String error : errors) {
            assertTrue(
                    error.matches("column \\d+ row \\d+ duplicate \\d+") ||
                            error.matches("Box \\d+ duplicate \\d+"),
                    "Error message format is incorrect: " + error
            );
        }

        System.out.println("Errors found:");
        for (String error : errors) {
            System.out.println(error);
        }

        assertTrue(errors.size() > 0, "No errors were detected in the board");
    }
}