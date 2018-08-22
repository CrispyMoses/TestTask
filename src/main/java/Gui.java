import com.toedter.calendar.JDateChooser;
import service.CalculationService;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.Date;

public class Gui extends JFrame {
    private JButton recalculateButton = new JButton("Recalculate");
    private JTextField amount = new JTextField("", 5);
    private JTextField result = new JTextField("", 5);
    private JLabel dateLabel = new JLabel("Date:");
    private JLabel amountLabel = new JLabel("Amount USD:");
    private JLabel resultLabel = new JLabel("Result:");
    private JDateChooser chooser = new JDateChooser(new Date());

    public Gui() {
        super("Simple Example");
        this.setBounds(0,0,275,125);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        PlainDocument doc = (PlainDocument) amount.getDocument();
        doc.setDocumentFilter(new DigitFilter());

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4,2,2,2));
        container.add(dateLabel);
        container.add(chooser);
        container.add(amountLabel);
        container.add(amount);
        container.add(resultLabel);

        result.setEditable(false);
        container.add(result);

        recalculateButton.addActionListener(e -> {
            if (amount.getText().isEmpty()) return;
            recalculateButton.setEnabled(false);
            try {
                CalculationService calculationService = new CalculationService();
                result.setText(calculationService.calculateDifference(chooser.getDate(), amount.getText()));
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            recalculateButton.setEnabled(true);
        });

        container.add(recalculateButton);
    }

    public static void main(String[] args) {
        Gui app = new Gui();
        app.setVisible(true);
    }
}