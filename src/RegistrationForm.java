import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.border.TitledBorder;

public class RegistrationForm extends JPanel {
    private JTextField usernameField, emailField, phoneField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> genderBox, courseBox;
    private Color lightBlue = new Color(52, 152, 219);
    private Color backgroundColor = new Color(245, 247, 250);
    public RegistrationForm() {
        setLayout(new GridBagLayout());
        setBackground(backgroundColor);
        setVisible(true);
        JPanel mainPanel = new JPanel();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Registration Form"
        );
        mainPanel.setBorder(titledBorder);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usernme
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(userLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(emailLabel, gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);
        // Password
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 2;
        add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Confirm Password
        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0; gbc.gridy = 3;
        add(confirmPassLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0; gbc.gridy = 4;
        add(genderLabel, gbc);

        genderBox = new JComboBox<>(new String[]{"Male", "Female"});
        gbc.gridx = 1;
        add(genderBox, gbc);
        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 0; gbc.gridy = 5;
        add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        add(phoneField, gbc);

        // Course
        JLabel courseLabel = new JLabel("Course:");
        gbc.gridx = 0; gbc.gridy = 6;
        add(courseLabel, gbc);

        courseBox = new JComboBox<>(new String[]{"Java", "Web Dev", "Database", "Networking"});
        gbc.gridx = 1;
        add(courseBox, gbc);

        // Submit Button
        JButton submitButton = new JButton("Register");
        submitButton.setBackground(lightBlue);
        submitButton.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(submitButton, gbc);
        JButton reset = new JButton("Reset");
        reset.setBackground(Color.RED);
        reset.setForeground(Color.WHITE);
        gbc.gridx=50;gbc.gridy=7;
        gbc.gridwidth= 1;
        add(reset,gbc);
reset.addActionListener(event->{
        usernameField.setText("");
        emailField.setText("");
        genderBox.setSelectedIndex(0);
        confirmPasswordField.setText("");
         passwordField.setText("");
             courseBox.setSelectedIndex(0);
            phoneField.setText("");
            });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
                String phone = phoneField.getText().trim();
                if(username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                        confirmPassword.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled!");
                    return;
                }
                if(!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Registration Successful!");
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->new RegistrationForm());
    }
}
