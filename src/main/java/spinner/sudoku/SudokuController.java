package spinner.sudoku;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class SudokuController {
    private Sudoku model;
    private SudokuGui view;

    public SudokuController(Sudoku model, SudokuGui view) {
        this.model = model;
        this.view = view;
        setupController();
    }

    private void setupController() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (view.getCell(i, j).isEditable()) {
                    addListenerToCell(i, j);
                }
            }
        }
    }

    private void addListenerToCell(int row, int col) {
        view.getCell(row, col).getDocument().addDocumentListener(new DocumentListener() {
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
            int[][] currentBoard = view.getCurrentBoard();
            model.setBoard(currentBoard);
            List<SudokuError> errors = model.getErrors();
            view.highlightErrors(errors);
        } catch (NumberFormatException ex) {
            view.showError("Invalid input: Please enter a number between 1 and 9.");
        }
    }
}
