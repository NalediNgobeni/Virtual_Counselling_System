package za.ac.cput.com.studentportalgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import javax.imageio.ImageIO;
import java.sql.*;

public class StudentPortalGUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;
    private JLabel forgotPassword;
    private BufferedImage backgroundImage;

    public StudentPortalGUI() {
        setTitle("CPUT CareLink – Virtual Counselling Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load and blur background image
        try {
            backgroundImage = ImageIO.read(new File("src/main/java/za/ac/cput/com/studentportalgui/Pic/background_pic1.png"));
            backgroundImage = blurImage(backgroundImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g2d.setColor(new Color(255, 255, 255, 60));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            }
        };

        // Top logo panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel logoLabel = new JLabel(new ImageIcon("src/main/java/za/ac/cput/com/studentportalgui/Pic/CPUT2222.png"));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(logoLabel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        Font titleFont = new Font("SansSerif", Font.BOLD, 45);
        Font labelFont = new Font("DmSarifText", Font.BOLD, 25);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 18);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);
        Color textColor = Color.decode("#259531");
        Color textColor2 = Color.decode("#FFFDD0");

        JLabel title = new JLabel("CareLink Virtual Counselling", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(title, gbc);

        JLabel tagline = new JLabel("Hello Students & Staff – We're Here for You with Confidential Support", SwingConstants.CENTER);
        tagline.setFont(new Font("SansSerif", Font.ITALIC, 20));
        tagline.setForeground(textColor2);
        gbc.gridy = 1;
        formPanel.add(tagline, gbc);

        gbc.gridwidth = 1;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        passLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        forgotPassword = new JLabel("<HTML><U><i>Forgot Password?</i></U></HTML>");
        forgotPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        forgotPassword.setForeground(Color.decode("#99CCCC"));
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(forgotPassword, gbc);

        forgotPassword.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                handleForgotPassword();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            public void setContentAreaFilled(boolean b) {}
        };
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setFont(buttonFont);
        loginButton.setBackground(new Color(46, 125, 50));
        loginButton.setForeground(textColor2);

        signupButton = new JButton("Sign Up") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            public void setContentAreaFilled(boolean b) {}
        };
        signupButton.setOpaque(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setBorderPainted(false);
        signupButton.setFocusPainted(false);
        signupButton.setFont(buttonFont);
        signupButton.setBackground(textColor2);
        signupButton.setForeground(textColor);

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> login());
        signupButton.addActionListener(e -> new SignupGUI(this).setVisible(true));

        mainPanel.add(formPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private BufferedImage blurImage(BufferedImage image) {
        float[] matrix = new float[49];
        for (int i = 0; i < 49; i++) {
            matrix[i] = 1.0f / 49.0f;
        }
        BufferedImage blurred = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        new ConvolveOp(new Kernel(7, 7, matrix)).filter(image, blurred);
        return blurred;
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnector.connect()) {
            String sql = "SELECT FIRSTNAME, LASTNAME, ROLE FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Login successful popup
                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // optional: close login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleForgotPassword() {
        String input = JOptionPane.showInputDialog(this, "Enter your email for password reset:", "Forgot Password", JOptionPane.PLAIN_MESSAGE);
        if (input != null && !input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Reset link sent to: " + input.trim(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid email.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentPortalGUI().setVisible(true);
        });
    }
}
