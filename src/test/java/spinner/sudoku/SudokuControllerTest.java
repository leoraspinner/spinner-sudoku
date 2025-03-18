package spinner.sudoku;

import org.junit.jupiter.api.Test;
import javax.swing.*;
import javax.swing.text.Document;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class SudokuControllerTest {

    @Test
    void testValidInputHandling() {
        // Mock dependencies
        Sudoku model = mock(Sudoku.class);
        SudokuGui view = mock(SudokuGui.class);
        JTextField mockTextField = mock(JTextField.class);
        Document mockDocument = mock(Document.class);  // Mock the Document

        // Setup mock behavior
        when(mockTextField.isEditable()).thenReturn(true);
        when(mockTextField.getDocument()).thenReturn(mockDocument); // Mock getDocument()
        when(view.getCell(anyInt(), anyInt())).thenReturn(mockTextField);
        when(view.getCurrentBoard()).thenReturn(new int[9][9]);

        // Setup controller
        SudokuController controller = new SudokuController(model, view);

        // Execute
        controller.handleCellUpdate(0, 0);

        // Verify interactions
        verify(model).setBoard(any());
        verify(view).highlightErrors(any());
    }

    @Test
    void testInvalidInputHandling() {
        // Mock dependencies
        Sudoku model = mock(Sudoku.class);
        SudokuGui view = mock(SudokuGui.class);
        JTextField mockTextField = mock(JTextField.class);
        Document mockDocument = mock(Document.class);  // Mock the Document

        // Setup mock behavior
        when(mockTextField.isEditable()).thenReturn(true);
        when(mockTextField.getDocument()).thenReturn(mockDocument); // Mock getDocument()
        when(view.getCell(anyInt(), anyInt())).thenReturn(mockTextField);
        when(view.getCurrentBoard()).thenThrow(new NumberFormatException());

        // Setup controller
        SudokuController controller = new SudokuController(model, view);

        // Execute
        controller.handleCellUpdate(0, 0);

        // Verify interactions
        verify(view).showError(any());
        verify(model, never()).setBoard(any());
    }

    @Test
    void testErrorHighlighting() {
        // Mock dependencies
        Sudoku model = mock(Sudoku.class);
        SudokuGui view = mock(SudokuGui.class);
        JTextField mockTextField = mock(JTextField.class);
        Document mockDocument = mock(Document.class);  // Mock the Document

        // Setup mock behavior
        when(mockTextField.isEditable()).thenReturn(true);
        when(mockTextField.getDocument()).thenReturn(mockDocument); // Mock getDocument()
        when(view.getCell(anyInt(), anyInt())).thenReturn(mockTextField);
        List<SudokuError> mockErrors = Collections.singletonList(new SudokuError(0, 0, 5));
        when(model.getErrors()).thenReturn(mockErrors);

        // Setup controller
        SudokuController controller = new SudokuController(model, view);

        // Execute
        controller.handleCellUpdate(0, 0);

        // Verify interactions
        verify(view).highlightErrors(mockErrors);
    }
}
