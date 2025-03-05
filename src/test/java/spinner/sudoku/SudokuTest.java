package spinner.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
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

    private int[][] boardWithoutErrors = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    @Test
    public void verifyBoardErrors() {
        Sudoku sudoku = new Sudoku(exampleBoard);
        List<String> errors = sudoku.getErrors();

        List<String> expectedErrors = Arrays.asList(
                "column 9 row 1 duplicate 1",
                "column 7 row 2 duplicate 1",
                "column 2 row 4 duplicate 1",
                "column 9 row 8 duplicate 1",
                "Box 1 duplicate 1",
                "Box 3 duplicate 1"
        );

        assertEquals(expectedErrors.size(), errors.size(), "Number of errors doesn't match expected");

        for (int i = 0; i < expectedErrors.size(); i++) {
            assertEquals(expectedErrors.get(i), errors.get(i), "Error at index " + i + " doesn't match expected");
        }

        System.out.println("Errors found:");
        for (String error : errors) {
            System.out.println(error);
        }

        assertTrue(errors.size() > 0, "No errors were detected in the board");
    }
    @Test
    public void verifyNoErrors() {
        Sudoku sudoku = new Sudoku(boardWithoutErrors);
        List<String> errors = sudoku.getErrors();

        assertTrue(errors.isEmpty(), "Errors were found in a correct Sudoku board");

        System.out.println("No errors found, as expected.");
    }
}