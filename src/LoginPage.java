import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.sql.*;
class LoginPage extends JPanel {
    private JTextField usertext;
    private JPasswordField passwordField;
    public LoginPage() {
        usertext = new JTextField(20);
        passwordField = new JPasswordField(20);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200 , 200 , 200) , 1) ,
            BorderFactory.createEmptyBorder(30 , 40 , 30 , 40)
        ));
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(380 , 480));
        JLabel title = new JLabel("Login" , SwingConstants.CENTER);
        title.setFont(new Font("Arial" , Font.BOLD , 28));
        title.setBounds(0 , 30 , 350 , 40);
        panel.add(title);
        // Username
        JLabel username = new JLabel("Username");
        username.setBounds(40 , 100 , 100 , 20);
        username.setFont(new Font("SansSerif" , Font.PLAIN , 14));
        panel.add(username);
        JTextField usertext = new JTextField();
        usertext.setBounds(40 , 125 , 270 , 35);
        usertext.setBorder(BorderFactory.createLineBorder(new Color(51 , 204 , 255)));
        panel.add(usertext);
        // Password
        JLabel password = new JLabel("Password");
        password.setBounds(40 , 180 , 100 , 20);
        password.setFont(new Font("SansSerif" , Font.PLAIN , 14));
        panel.add(password);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(40 , 205 , 270 , 35);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(51 , 204 , 255)));
        panel.add(passwordField);
        // Login Button
        JButton button = new JButton("Login");
        button.setBounds(40 , 270 , 270 , 40);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(46 , 204 , 113));
        button.setFont(new Font("Segoe UI" , Font.BOLD , 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(button);
        JLabel re = new JLabel("Register Now" , SwingConstants.CENTER);
        re.setBounds(0 , 350 , 350 , 20);
        re.setForeground(new Color(0 , 102 , 204));
        re.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(re);
        add(mainPanel);
        mainPanel.add(panel);
        setVisible(true);
        /// //////////////////////////////////////////////database connectivity start this////////////////////////////////////
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usertext.getText();
                String inputPass = new String(passwordField.getPassword());
               if (username.isEmpty() || inputPass.isEmpty()) {
                    JOptionPane.showMessageDialog(null , "username and password field");
                }else if (username.equalsIgnoreCase("Admin") && inputPass.equalsIgnoreCase("123")) {
                    JOptionPane.showMessageDialog(null , "Welcome Admin");
                    new FullAdminDashboard().setVisible(true);
                    usertext.setText("");
                    passwordField.setText("");
                    //LoginPage.this.dispose();
                } else{
                   fromDatabase(username,inputPass);
               }
            }
        });
    }
        private void fromDatabase (String user,String pass) {
            String url = "jdbc:postgresql://localhost:5432/onlinecourse";
            String dbUser = "postgres";
            String dbPass = "1453";
            String pst = "SELECT * FROM register WHERE username = ? AND password = ?";
            try {
                Class.forName("org.postgresql.Driver");
                try (Connection conn = DriverManager.getConnection(url , dbUser , dbPass)) {
                    PreparedStatement psta = conn.prepareStatement(pst);
                    psta.setString(1 , user);
                    psta.setString(2 , pass);
                    ResultSet rs = psta.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this , "Login successful! Welcome " + user);
                        new StudentDashboard(user).setVisible(true);
                        usertext.setText("");
                        passwordField.setText("");
//                    LoginPage.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this , "Invalid username or password!" , "Error" ,
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this , "PostgreSQL Driver not found!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this , "Database Error: " + e.getMessage());
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////database connectivity end//////////////////////////////////////////
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}