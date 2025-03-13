package spinner.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        List<SudokuError> errors = new ArrayList<>(sudoku.getErrors());

        List<SudokuError> expectedErrors = Arrays.asList(
                new SudokuError(0, 8, 1),
                new SudokuError(1, 6, 1),
                new SudokuError(3, 1, 1),
                new SudokuError(7, 8, 1),
                new SudokuError(1, 1, 1)
        );

        // Check if the number of errors matches
        assertEquals(expectedErrors, errors, "Number of errors doesn't match expected");
    }

    @Test
    public void verifyNoErrors() {
        Sudoku sudoku = new Sudoku(boardWithoutErrors);
        List<SudokuError> errors = sudoku.getErrors();

        assertTrue(errors.isEmpty(), "Errors were found in a correct Sudoku board");
    }
}