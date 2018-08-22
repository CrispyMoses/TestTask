import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DigitFilter extends DocumentFilter {

    private static final String PATTERN = "\\d|\\.+";

    private JTextField field;

    public DigitFilter(JTextField field) {
        this.field = field;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches(PATTERN)) {
            if (string.equals(".") && field.getText().contains(".")) return;
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
        if (string.matches(PATTERN)) {
            if (string.equals(".") && field.getText().contains(".")) return;
            super.replace(fb, offset, length, string, attrs);
        }
    }
}