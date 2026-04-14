import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
class LoginPage extends JFrame {
    public LoginPage() {
//        String photo = "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-03-17 152841.png";
//        ImageIcon originalIcon = new ImageIcon(getClass().getResource(photo));
        ImageIcon bt= new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-03-17 152841.png");
        setIconImage(bt.getImage());
        setTitle("User Login System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(400 , 500));
        JPanel mainPanel = new JPanel(new GridBagLayout());
//        mainPanel.add(headerpanel);
        //mainPanel.setBackground( new Color(44, 62, 80));
        mainPanel.setBackground(new Color(173, 216, 230));
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
        JButton forget= new JButton("forgotPassword");
        forget.setContentAreaFilled(false);
        forget.setBorderPainted(false);
        forget.setForeground(Color.yellow);
        forget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(forget);
        // Login Button
        JButton button = new JButton("Login");
        button.setBounds(40 , 270 , 270 , 40);
        button.setBackground(new Color(70 , 190 , 230));
        button.setBackground(new Color(0 , 204 , 255));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial" , Font.BOLD , 14));
        panel.add(button);
        JLabel re = new JLabel("Register Now" , SwingConstants.CENTER);
        re.setBounds(0 , 350 , 350 , 20);
        re.setForeground(new Color(0 , 102 , 204));
        re.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(re);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0 , 0 , 20 , 0);
        JLabel topTitle = new JLabel("OnlineCOurse Registration");
        topTitle.setFont(new Font("Arial" , Font.BOLD , 50));
        topTitle.setForeground(Color.RED);
        mainPanel.add(topTitle , gbc);
        gbc.gridy = 1;
        mainPanel.add(panel , gbc);
        add(mainPanel);
        setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usertext.getText();
                String inputPass = new String(passwordField.getPassword());
                if (username.equals("Admin") && inputPass.equals("123")) {
                    JOptionPane.showMessageDialog(null , "Welcome Admin");
                    FullAdminDashboard dash1=new FullAdminDashboard();
                    dash1.setVisible(true);
                    LoginPage.this.dispose();
                } else if (username.equals("Biruk") && inputPass.equals("b123")) {
                    JOptionPane.showMessageDialog(null , "welcame User");
                    StudentDashboard dash = new StudentDashboard(username);
                    dash.setVisible(true);
                    LoginPage.this.dispose();
                } else if(username.isEmpty() || inputPass.isEmpty()) {
                    JOptionPane.showMessageDialog(null , "username and password field");
                }else{
                    JOptionPane.showMessageDialog(null , "Invalid USername and password");
                }
            }
        });
    }
//      forget.addActionListener(e->{
//          @Override
//    private void handleForgotPassword() {
//        String user = JOptionPane.showInputDialog(this, "Enter Username:");
//
//        if (user != null && !user.isEmpty()) {
//            // 1. እዚህ ጋር ከDatabase ጥያቄውን ማምጣት (ምሳሌ፡ "ከተማ?")
//            String answer = JOptionPane.showInputDialog(this, "Security Question: What is your birth city?");
//
//            // 2. መልሱ ትክክል መሆኑን ማረጋገጥ (ይህ ከDB ጋር መመሳከር አለበት)
//            if ("Addis".equalsIgnoreCase(answer)) {
//                String newPass = JOptionPane.showInputDialog(this, "Enter New Password:");
//
//                if (newPass != null && !newPass.isEmpty()) {
//                    // 3. እዚህ ጋር SQL UPDATE በመጠቀም ፓስዎርዱን መቀየር
//                    // UPDATE users SET password = newPass WHERE username = user;
//                    JOptionPane.showMessageDialog(this, "Password updated successfully!");
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Incorrect answer!", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//        });
//    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}


















