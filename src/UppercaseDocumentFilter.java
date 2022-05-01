import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class UppercaseDocumentFilter extends DocumentFilter {
    public void insertString(DocumentFilter.FilterBypass filterBypass, int offset, String text, AttributeSet attributeSet) throws BadLocationException {
        filterBypass.insertString(offset, text.toUpperCase(), attributeSet);
        System.out.println("111");
    }

    public void replace(DocumentFilter.FilterBypass filterBypass, int offset, int length, String text, AttributeSet attributeSet) throws BadLocationException {
        filterBypass.replace(offset, length, text.toUpperCase(), attributeSet);
    }
}