import javax.swing.*;
import java.awt.*;


// Encapsulation & Abstraction

class CalculatorModel {
    private String firstNumber = "";
    private String operator = "";

    // Encapsulation: private fields with getters and setters
    public void setFirstNumber(String num) { firstNumber = num; }
    public String getFirstNumber() { return firstNumber; }

    public void setOperator(String op) { operator = op; }
    public String getOperator() { return operator; }

    // Abstraction: calculation logic hidden inside model
    public int calculate(String secondNumber) {
        if (firstNumber.isEmpty() || secondNumber.isEmpty()) return 0;
        int num1 = Integer.parseInt(firstNumber, 2);
        int num2 = Integer.parseInt(secondNumber, 2);

        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> (num2 != 0) ? num1 / num2 : 0;
            default -> 0;
        };
    }
}

// Inheritance

class Calculator {
    protected CalculatorModel model = new CalculatorModel();

    // Polymorphism 
    public void displayResult(int result, JTextField display,
                              JTextField bin, JTextField dec,
                              JTextField oct, JTextField hex) {
        display.setText(Integer.toBinaryString(result));
        bin.setText("BINARY: " + Integer.toBinaryString(result));
        dec.setText("DECIMAL: " + result);
        oct.setText("OCTAL: " + Integer.toOctalString(result));
        hex.setText("HEXADECIMAL: " + Integer.toHexString(result).toUpperCase());
    }
}


// Programmer Calculator: Extends Base Calculator

class ProgrammerCalculator extends Calculator {

    public ProgrammerCalculator() {
        createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame("Programmer Calculator");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(450, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // ========================
        // Display fields
        // ========================
        JTextField display = new JTextField();
        display.setBounds(30, 30, 380, 60);
        display.setEditable(false);
        display.setBackground(new Color(128, 0, 128));
        display.setForeground(Color.WHITE);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(display);

        JTextField binaryDisplay = new JTextField("BINARY:");
        binaryDisplay.setBounds(30, 110, 180, 40);
        binaryDisplay.setEditable(false);
        binaryDisplay.setBackground(Color.BLACK);
        binaryDisplay.setForeground(Color.GREEN);
        frame.add(binaryDisplay);

        JTextField decimalDisplay = new JTextField("DECIMAL:");
        decimalDisplay.setBounds(230, 110, 180, 40);
        decimalDisplay.setEditable(false);
        decimalDisplay.setBackground(Color.BLACK);
        decimalDisplay.setForeground(Color.ORANGE);
        frame.add(decimalDisplay);

        JTextField octalDisplay = new JTextField("OCTAL:");
        octalDisplay.setBounds(30, 160, 180, 40);
        octalDisplay.setEditable(false);
        octalDisplay.setBackground(Color.BLACK);
        octalDisplay.setForeground(Color.CYAN);
        frame.add(octalDisplay);

        JTextField hexDisplay = new JTextField("HEXADECIMAL:");
        hexDisplay.setBounds(230, 160, 180, 40);
        hexDisplay.setEditable(false);
        hexDisplay.setBackground(Color.BLACK);
        hexDisplay.setForeground(Color.MAGENTA);
        frame.add(hexDisplay);

        
        // Number Buttons (0 and 1 for binary)
        JButton button0 = new JButton("0");
        JButton button1 = new JButton("1");

        button0.setBounds(30, 220, 110, 50);
        button1.setBounds(150, 220, 110, 50);

        button0.setBackground(new Color(255, 105, 255));
        button1.setBackground(new Color(255, 105, 255));
        button0.setForeground(Color.WHITE);
        button1.setForeground(Color.WHITE);

        frame.add(button0);
        frame.add(button1);

        button0.addActionListener(e -> display.setText(display.getText() + "0"));
        button1.addActionListener(e -> display.setText(display.getText() + "1"));

        
        // Clear Button
        
        JButton clear = new JButton("C");
        clear.setBounds(270, 220, 140, 50);
        clear.setBackground(new Color(255, 105, 255));
        clear.setForeground(Color.WHITE);
        frame.add(clear);

        clear.addActionListener(e -> {
            display.setText("");
            binaryDisplay.setText("BINARY:");
            decimalDisplay.setText("DECIMAL:");
            octalDisplay.setText("OCTAL:");
            hexDisplay.setText("HEXADECIMAL:");
            model.setFirstNumber("");
            model.setOperator("");
        });

        
        // Operator Buttons
        
        JButton add = new JButton("+");
        JButton sub = new JButton("-");
        JButton mul = new JButton("*");
        JButton div = new JButton("/");

        JButton[] ops = {add, sub, mul, div};
        int x = 30;

        for (JButton b : ops) {
            b.setBounds(x, 290, 90, 50);
            b.setBackground(new Color(128, 0, 128));
            b.setForeground(Color.WHITE);
            frame.add(b);
            x += 100;
        }

        add.addActionListener(e -> { model.setFirstNumber(display.getText()); model.setOperator("+"); display.setText(""); });
        sub.addActionListener(e -> { model.setFirstNumber(display.getText()); model.setOperator("-"); display.setText(""); });
        mul.addActionListener(e -> { model.setFirstNumber(display.getText()); model.setOperator("*"); display.setText(""); });
        div.addActionListener(e -> { model.setFirstNumber(display.getText()); model.setOperator("/"); display.setText(""); });

        
        // Equals Button
        
        JButton equals = new JButton("=");
        equals.setBounds(150, 360, 150, 50);
        equals.setBackground(new Color(255, 105, 255));
        equals.setForeground(Color.WHITE);
        frame.add(equals);

        equals.addActionListener(e -> {
            int result = model.calculate(display.getText());
            // Polymorphism in action: displayResult can be overridden
            displayResult(result, display, binaryDisplay, decimalDisplay, octalDisplay, hexDisplay);
        });

        
        // Optional: Backspace Button
        
        JButton backspace = new JButton("←");
        backspace.setBounds(30, 360, 110, 50);
        backspace.setBackground(new Color(255, 105, 255));
        backspace.setForeground(Color.WHITE);
        frame.add(backspace);

        backspace.addActionListener(e -> {
            String text = display.getText();
            if(!text.isEmpty()) display.setText(text.substring(0, text.length()-1));
        });

        frame.setVisible(true);
    }
}
