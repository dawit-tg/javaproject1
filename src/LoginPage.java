import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

class LoginPage extends JPanel {
    private index parentFrame;
    private JTextField usertext;
    private JPasswordField passwordField;

    public LoginPage(index parent) {
        this.parentFrame = parent;

        usertext = new JTextField(20);
        passwordField = new JPasswordField(20);

        setLayout(new GridBagLayout());
        setBackground(new Color(241, 245, 249));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(380, 480));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(0, 30, 350, 40);
        panel.add(title);

        JLabel username = new JLabel("Username");
        username.setBounds(40, 100, 100, 20);
        username.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(username);

        usertext.setBounds(40, 125, 270, 35);
        usertext.setBorder(BorderFactory.createLineBorder(new Color(51, 204, 255)));
        panel.add(usertext);

        JLabel password = new JLabel("Password");
        password.setBounds(40, 180, 100, 20);
        password.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(password);

        passwordField.setBounds(40, 205, 270, 35);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(51, 204, 255)));
        panel.add(passwordField);

        JButton button = new JButton("Login");
        button.setBounds(40, 270, 270, 40);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(46, 204, 113));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(button);

        JLabel re = new JLabel("<html><u>Don't have an account? Register now</u></html>", SwingConstants.CENTER);
        re.setBounds(0, 350, 350, 20);
        re.setForeground(new Color(0, 102, 204));
        re.setCursor(new Cursor(Cursor.HAND_CURSOR));
        re.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(re);

        add(panel);

        re.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                parentFrame.centerSection.removeAll();
                parentFrame.centerSection.setLayout(new BorderLayout());
                parentFrame.centerSection.add(new RegistrationForm(), BorderLayout.CENTER);
                parentFrame.centerSection.revalidate();
                parentFrame.centerSection.repaint();
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = usertext.getText();
                String inputPass = new String(passwordField.getPassword());

                if (name.isEmpty() || inputPass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both username and password");
                } else if (name.equalsIgnoreCase("Admin") && inputPass.equalsIgnoreCase("123")) {
                    JOptionPane.showMessageDialog(null, "Welcome Admin");
                    new FullAdminDashboard().setVisible(true);
                    usertext.setText("");
                    passwordField.setText("");
                } else {
                    fromDatabase(name, inputPass);
                }
            }
        });
    }

    private void fromDatabase(String user, String pass) {
        String url = "jdbc:postgresql://localhost:5432/javaPro";
        String dbUser = "postgres";
        String dbPass = "devo@123";
        String pst = "SELECT * FROM students WHERE name = ? AND password = ?";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
                PreparedStatement psta = conn.prepareStatement(pst);
                psta.setString(1, user);
                psta.setString(2, pass);
                ResultSet rs = psta.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user);

                    // Update State
                    parentFrame.isLoggedIn = true;
                    parentFrame.currentStudent = user;
                    parentFrame.studentName = user;

                    // LOGIC: Check if user clicked Enroll before logging in
                    if (parentFrame.pendingEnrollTitle != null) {
                        // 1. Trigger Payment (using 3 arguments as per your Payment code)
                        Payment.showPaymentGateway(parentFrame.currentStudent, parentFrame.pendingEnrollTitle, parentFrame.pendingEnrollPrice);

                        // 2. After Payment closes, open Dashboard
                        new StudentDashboard(parentFrame.currentStudent).setVisible(true);
                        parentFrame.dispose();

                        // 3. Clear pending
                        parentFrame.pendingEnrollTitle = null;
                        parentFrame.pendingEnrollPrice = 0.0;
                    } else {
                        // Just go to Home
                        parentFrame.showHomeContent();
                    }

                    usertext.setText("");
                    passwordField.setText("");

                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "PostgreSQL Driver not found!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}