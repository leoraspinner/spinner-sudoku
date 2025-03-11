package spinner.sudoku;

/** Plan for SudokuError:
 * error details for row, column, and number
 * have a toString() method for error representation
 * When the user fills a cell or clicks "check errors",
 *                              program will check the board
 *                              highlight errors in red
 * */

public class SudokuError {
    private int row;
    private int column;
    private int number;

    //Fields: store the exact location and duplication value causing the error

    public SudokuError(int row, int column, int number) {
        this.row = row;
        this.column = column;
        this.number = number;
        //constructor: Initializes the error with the detected values
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "column " + (column + 1) + " row " + (row + 1) + " duplicate " + number;
    }

}
