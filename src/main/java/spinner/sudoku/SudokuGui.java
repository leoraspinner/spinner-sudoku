package spinner.sudoku;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
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

                if (board[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(Color.LIGHT_GRAY);
                }

                gridPanel.add(cells[i][j]);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // New methods for controller interaction
    public JTextField getCell(int row, int col) {
        return cells[row][col];
    }

    public int[][] getCurrentBoard() throws NumberFormatException {
        int[][] currentBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText().trim();
                if (text.isEmpty()) {
                    currentBoard[i][j] = 0;
                } else if (!text.matches("[1-9]")) {
                    throw new NumberFormatException("Invalid input: " + text);
                } else {
                    currentBoard[i][j] = Integer.parseInt(text);
                }
            }
        }
        return currentBoard;
    }

    public void highlightErrors(List<SudokuError> errors) {
        clearHighlights();
        for (SudokuError error : errors) {
            int errorRow = error.row();
            int errorCol = error.col();
            if (cells[errorRow][errorCol].isEditable()) {
                cells[errorRow][errorCol].setBackground(Color.RED);
            }
        }
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    private void clearHighlights() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].isEditable()) {
                    cells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }
}