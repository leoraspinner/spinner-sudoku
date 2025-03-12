package spinner.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/** Plan for sudokuGUI
 * GUI will be a 9x9 grid of JTextField components
 * Will display a partially completed board
 * User can manually enter numbers into the cells
 *
 * GUI will interact with the Sudoku logic to validate the board and retrieve errors.
 */

public class SudokuGui {
    private JFrame frame;
    private JTextField[][] cells;
    private Sudoku sudoku;

    public SudokuGui(Sudoku sudoku) {
        this.sudoku = sudoku;
        initializeGui();
    }

    private void initializeGui() {
        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(9, 9));

        cells = new JTextField[9][9];

        int[][] board = sudoku.getBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);

                if (board[i][j] != 0) { // Pre-filled numbers
                    cells[i][j].setText(String.valueOf(board[i][j]));
                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(Color.LIGHT_GRAY);
                }

                gridPanel.add(cells[i][j]);
            }
        }

        JButton checkButton = new JButton("Check Errors");

        // Updated action listener to process errors programmatically
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<SudokuError> errors = checkErrors();
                if (!errors.isEmpty()) {
                    System.out.println("Errors found:");
                    for (SudokuError error : errors) {
                        System.out.println("Error at row " + error.getRow() + ", column " + error.getColumn() + ": " + error.getNumber());
                    }
                } else {
                    System.out.println("No errors found!");
                }
            }
        });

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(checkButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private List<SudokuError> checkErrors() {
        int[][] currentBoard = new int[9][9];
        List<SudokuError> errors = new ArrayList<>();

        try {
            // Read values from GUI into the board
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String text = cells[i][j].getText().trim();

                    // Validate input before parsing
                    if (text.isEmpty()) {
                        currentBoard[i][j] = 0; // Treat empty cells as 0
                    } else if (!text.matches("[1-9]")) {
                        // Ensure valid numbers between 1 and 9
                        throw new NumberFormatException("Invalid input: " + text);
                    } else {
                        currentBoard[i][j] = Integer.parseInt(text); // Parse valid input
                    }
                }
            }

            sudoku.setBoard(currentBoard); // Update the Sudoku object
            errors = sudoku.getErrors(); // Retrieve errors from Sudoku logic

            clearHighlights(); // Reset cell colors

            for (SudokuError error : errors) {
                int row = error.getRow();
                int col = error.getColumn();

                // Highlight only user-editable cells with errors
                if (cells[row][col].isEditable()) {
                    cells[row][col].setBackground(Color.RED);
                }
            }

        } catch (NumberFormatException ex) {
            // Handle invalid input
            JOptionPane.showMessageDialog(frame, "Invalid input: Please enter a number between 1 and 9.");
        }

        return errors; // Return the list of errors
    }

    /**
     * Clears all error highlights from user-editable cells.
     */
    private void clearHighlights() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].isEditable()) {
                    // Only reset editable cells
                    cells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }
}
