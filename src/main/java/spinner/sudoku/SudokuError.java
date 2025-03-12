package spinner.sudoku;


/** Plan for SudokuError:
 * error details for row, column, and number
 * have a toString() method for error representation
 * When the user fills a cell or clicks "check errors",
 *                              program will check the board
 *                              highlight errors in red
 * */

public record SudokuError (int row, int col, int num){

}
