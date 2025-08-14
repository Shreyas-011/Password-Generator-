import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.awt.datatransfer.*;

public class PasswordGeneratorGUI extends JFrame {
    private JTextField lengthField;
    private JTextArea passwordArea;
    private JCheckBox includeUpper, includeLower, includeNumbers, includeSymbols;
    private JButton generateButton, copyButton;

    public PasswordGeneratorGUI() {
        setTitle("Meaningful Password Generator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(5, 2));

        optionPanel.add(new JLabel("Password Length:"));
        lengthField = new JTextField("14");
        optionPanel.add(lengthField);

        includeUpper = new JCheckBox("Include Uppercase Letters (A-Z)", true);
        includeLower = new JCheckBox("Include Lowercase Letters (a-z)", true);
        includeNumbers = new JCheckBox("Include Numbers (0-9)", true);
        includeSymbols = new JCheckBox("Include Symbols (!@#$...)", true);

        optionPanel.add(includeUpper);
        optionPanel.add(includeLower);
        optionPanel.add(includeNumbers);
        optionPanel.add(includeSymbols);

        generateButton = new JButton("Generate Password");
        copyButton = new JButton("Copy to Clipboard");

        optionPanel.add(generateButton);
        optionPanel.add(copyButton);

        add(optionPanel, BorderLayout.NORTH);

        passwordArea = new JTextArea(3, 30);
        passwordArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        passwordArea.setEditable(false);
        add(new JScrollPane(passwordArea), BorderLayout.CENTER);

        generateButton.addActionListener(e -> generatePassword());
        copyButton.addActionListener(e -> copyToClipboard());

        setVisible(true);
    }

    private void generatePassword() {
        int length;
        try {
            length = Integer.parseInt(lengthField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for length.");
            return;
        }

        // Example word list (you can expand this or load from file)
        String[] words = {
            "Sun", "Moon", "Tree", "Blue", "Sky", "Rain", "Cloud", "Fire", "Wind", "Tiger",
            "Rock", "Star", "Wave", "Lion", "Leaf", "Light", "Dark", "Snow", "Dream", "Bright"
        };

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        while (password.length() < length) {
            String word = words[random.nextInt(words.length)];

            // Randomly change case based on checkbox selection
            if (includeUpper.isSelected() && random.nextBoolean()) {
                word = word.toUpperCase();
            } else if (includeLower.isSelected()) {
                word = word.toLowerCase();
            }

            password.append(word);

            // Optionally add a digit
            if (includeNumbers.isSelected() && random.nextBoolean()) {
                password.append(random.nextInt(10)); // 0-9
            }

            // Optionally add a symbol
            if (includeSymbols.isSelected() && random.nextBoolean()) {
                String symbols = "!@#$%^&*?";
                password.append(symbols.charAt(random.nextInt(symbols.length())));
            }
        }

        // Trim to desired length
        if (password.length() > length) {
            password.setLength(length);
        }

        passwordArea.setText(password.toString());
    }

    private void copyToClipboard() {
        String password = passwordArea.getText();
        if (!password.isEmpty()) {
            StringSelection selection = new StringSelection(password);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            JOptionPane.showMessageDialog(this, "Password copied to clipboard!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PasswordGeneratorGUI::new);
    }
}
