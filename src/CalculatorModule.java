import java.awt.*;
import javax.swing.*;

public class CalculatorModule extends JPanel {
    @SuppressWarnings("FieldMayBeFinal")
    private JTextField display;

    private double operand1 = 0;
    private String operator = "";
    private boolean waitingForSecond = false;

    public CalculatorModule() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10));
        panel.setBackground(Color.WHITE);

        String[] buttons = {
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "0","C","=","+"
        };

        for (String t : buttons) {
            JButton b = new JButton(t);
            b.setFont(new Font("Arial", Font.BOLD, 24));
            b.setBackground(Color.DARK_GRAY);
            b.setForeground(Color.WHITE);
            b.addActionListener(e -> handle(t));
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);
    }

    private void handle(String input) {

        if (input.equals("C")) {
            display.setText("0");
            operand1 = 0;
            operator = "";
            waitingForSecond = false;
            return;
        }

        if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/")) {
            operand1 = Double.parseDouble(display.getText());
            operator = input;
            waitingForSecond = true;
            return;
        }

        if (input.equals("=")) {
            if (operator.equals("")) return;

            double operand2 = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+" -> result = operand1 + operand2;

                case "-" -> result = operand1 - operand2;

                case "*" -> result = operand1 * operand2;

                case "/" -> result = operand1 / operand2;
            }

            display.setText(format(result));
            operator = "";
            waitingForSecond = false;
            return;
        }

        // angka
        if (waitingForSecond) {
            display.setText(input);
            waitingForSecond = false;
        } else {
            if (display.getText().equals("0"))
                display.setText(input);
            else
                display.setText(display.getText() + input);
        }
    }

    private String format(double num) {
        if (num == (int) num) return String.valueOf((int) num);
        return String.valueOf(num);
    }
}
