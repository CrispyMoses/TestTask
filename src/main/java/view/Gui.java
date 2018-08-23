package view;

import com.toedter.calendar.JDateChooser;
import service.CalculationService;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class Gui extends JFrame {

    private JButton recalculateButton = new JButton("Recalculate");
    private JTextField amountInput = new JTextField("");
    private JTextField resultField = new JTextField("");
    private JLabel dateLabel = new JLabel("Date:");
    private JLabel amountLabel = new JLabel("Amount USD:");
    private JLabel resultLabel = new JLabel("Result:");
    private JDateChooser dateChooser = new JDateChooser(new Date());

    public Gui() {
        super("Test Task");
        this.setBounds(0,0,500,300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        PlainDocument doc = (PlainDocument) amountInput.getDocument();
        doc.setDocumentFilter(new DigitFilter(amountInput));

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(8,1,2,2));
        container.add(dateLabel);
        container.add(dateChooser);
        container.add(amountLabel);
        container.add(amountInput);
        container.add(resultLabel);

        resultField.setEditable(false);
        container.add(resultField);

        recalculateButton.addActionListener(this::event);
        container.add(recalculateButton);
    }

    private void event(ActionEvent e) {
        if (amountInput.getText().isEmpty()) return;
        recalculateButton.setEnabled(false);
        try {
            CalculationService calculationService = new CalculationService();
            resultField.setText(calculationService.calculateDifference(dateChooser.getDate(), amountInput.getText()));
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Произошла ошибка. Попробуйте ещё раз.");
            exc.printStackTrace();
        }
        recalculateButton.setEnabled(true);

    }

    public static void main(String[] args) {
        Gui app = new Gui();
        app.setVisible(true);
    }
}