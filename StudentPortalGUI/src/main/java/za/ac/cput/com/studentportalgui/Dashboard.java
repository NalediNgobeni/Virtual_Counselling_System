package za.ac.cput.com.studentportalgui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    public Dashboard(String firstName, String lastName) {
        setTitle("Dashboard");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + firstName + " " + lastName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.CENTER);

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        signOutButton.addActionListener(e -> {
            new StudentPortalGUI().setVisible(true);
            this.dispose(); // close dashboard
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(signOutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
