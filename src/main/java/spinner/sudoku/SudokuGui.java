package spinner.sudoku;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/** Plan for sudokuGUI
 * GUI will be a 9x9 grid of JTextField components
 * Will display a partially completed board
 * User can manually enter numbers into the cells
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
                } else {
                    // Add DocumentListener to editable cells
                    final int row = i;
                    final int col = j;
                    cells[i][j].getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            checkCellAndUpdateErrors(row, col);
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            checkCellAndUpdateErrors(row, col);
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            // Plain text components don't fire these events
                        }
                    });
                }

                gridPanel.add(cells[i][j]);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void checkCellAndUpdateErrors(int row, int col) {
        SwingUtilities.invokeLater(() -> {
            List<SudokuError> errors = checkErrors();
            if (!errors.isEmpty()) {
                System.out.println("Errors found:");
                for (SudokuError error : errors) {
                    System.out.println("Error at row " + error.getRow() +
                            ", column " + error.getColumn() +
                            ": " + error.getNumber());
                }
            } else {
                System.out.println("No errors found!");
            }
        });
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

            sudoku.setBoard(currentBoard);
            errors = sudoku.getErrors();

            clearHighlights();

            for (SudokuError error : errors) {
                int errorRow = error.getRow();
                int errorCol = error.getColumn();

                if (cells[errorRow][errorCol].isEditable()) {
                    cells[errorRow][errorCol].setBackground(Color.RED);
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
