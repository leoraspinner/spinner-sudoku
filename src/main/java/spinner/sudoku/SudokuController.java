package spinner.sudoku;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class SudokuController {
    private Sudoku model;
    private JTextField[][] cells;

    public SudokuController(Sudoku model, JTextField[][] cells) {
        this.model = model;
        this.cells = cells;
        setupController();
    }

    private void setupController() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].isEditable()) {
                    addListenerToCell(i, j);
                }
            }
        }
    }

    private void addListenerToCell(int row, int col) {
        cells[row][col].getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleCellUpdate(row, col);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleCellUpdate(row, col);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    void handleCellUpdate(int row, int col) {
        try {
            int[][] currentBoard = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String text = cells[i][j].getText().trim();
                    currentBoard[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }
            model.setBoard(currentBoard);
            List<SudokuError> errors = model.getErrors();
            highlightErrors(errors);
        } catch (NumberFormatException ex) {
            showError("Invalid input: Please enter a number between 1 and 9");
        }
    }

    private void highlightErrors(List<SudokuError> errors) {
        clearHighlights();
        for (SudokuError error : errors) {
            JTextField cell = cells[error.row()][error.col()];
            if (cell.isEditable()) {
                cell.setBackground(Color.RED);
            }
        }
    }

    private void clearHighlights() {
        for (JTextField[] row : cells) {
            for (JTextField cell : row) {
                if (cell.isEditable()) {
                    cell.setBackground(Color.WHITE);
                }
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
