package za.ac.cput.com.studentportalgui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignupGUI extends JFrame {

    private JTextField fnameField, lnameField, phoneField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;   // Role selection
    private JButton registerButton, cancelButton;

    private JFrame parent;

    public SignupGUI(JFrame parent) {
        this.parent = parent;
        setTitle("Student Consult Portal - Sign Up");
        setSize(600, 450);  // slightly bigger to fit role field
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#F8F8F8"));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create New Account", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(Color.decode("#2E7D32"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("First Name:", SwingConstants.RIGHT), gbc);

        fnameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(fnameField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Last Name:", SwingConstants.RIGHT), gbc);

        lnameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(lnameField, gbc);

        // Phone Number
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Phone Number:", SwingConstants.RIGHT), gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Email:", SwingConstants.RIGHT), gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Password:", SwingConstants.RIGHT), gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // ✅ Role Dropdown
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Role:", SwingConstants.RIGHT), gbc);

        roleComboBox = new JComboBox<>(new String[]{"Student", "Staff", "Counsellor", "Admin"});
        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);

        // Buttons
        registerButton = new JButton("Register");
        registerButton.setBackground(Color.decode("#2E7D32"));
        registerButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(registerButton, gbc);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        add(panel);

        registerButton.addActionListener(e -> register());
        cancelButton.addActionListener(e -> dispose());
    }

    private void register() {
        String fname = fnameField.getText().trim();
        String lname = lnameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = roleComboBox.getSelectedItem().toString(); //Roles selection

        if (fname.isEmpty() || lname.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!phone.matches("\\d{10,15}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number (10–15 digits).", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Insert into DB
        try (Connection conn = DatabaseConnector.connect()) {
            String sql = "INSERT INTO USERS (EMAIL, PASSWORD, FIRSTNAME, LASTNAME, PHONENUMBER, ROLE) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, fname);
            stmt.setString(4, lname);
            stmt.setString(5, phone);
            stmt.setString(6, role); // Roles types

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful! You can now login.");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
