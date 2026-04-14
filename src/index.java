import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
public class index extends JFrame {
    JPanel centerSection;
    Color darkBlue = new Color(33, 45, 62);
    Color lightBlue = new Color(52, 152, 219);
    Color sidebarColor = new Color(44, 62, 80);
    Color backgroundColor = new Color(245, 247, 250);
    public index() {
        setTitle("Online Course Registration System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // =========================
        // Menu Bar
        // =========================
        JMenuBar menuBar = new JMenuBar();
        JMenu homeMenu = new JMenu("Home");
        JMenu aboutMenu = new JMenu("About Us");
        JMenu contactMenu = new JMenu("Contact Us");
        menuBar.add(homeMenu);
        menuBar.add(aboutMenu);
        menuBar.add(contactMenu);
        setJMenuBar(menuBar);
        menuBar.setBackground(new Color(44, 62, 80));
        menuBar.setForeground(Color.WHITE);
        // =========================
        // Sidebar
        // =========================
        JPanel sideBar = new JPanel();
        sideBar.setBackground(sidebarColor);
        sideBar.setPreferredSize(new Dimension(220, getHeight()));
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel sideTitle = new JLabel("Course System");
        sideTitle.setForeground(Color.WHITE);
        sideTitle.setFont(new Font("Arial", Font.BOLD, 22));
        sideTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Registration");
        styleButton(loginButton);
        styleButton(registerButton);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sideBar.add(sideTitle);
        sideBar.add(Box.createRigidArea(new Dimension(0, 40)));
        sideBar.add(loginButton);
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));
        sideBar.add(registerButton);
        // =========================
        // Main Panel
        // =========================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        // Header Section
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(new Color(44, 62, 80));
        topSection.setPreferredSize(new Dimension(1000, 80));
        JLabel welcomeLabel = new JLabel("Welcome to Online Course Registration System");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topSection.add(welcomeLabel, BorderLayout.CENTER);
        // Center Section
        centerSection = new JPanel();
        centerSection.setBackground(backgroundColor);
        centerSection.setLayout(new BoxLayout(centerSection, BoxLayout.Y_AXIS));
        centerSection.setBorder(new EmptyBorder(30, 40, 30, 40));
        showHomeContent();
        // Footer Section
        JPanel footer = new JPanel();
        footer.setBackground(darkBlue);
        footer.setPreferredSize(new Dimension(700, 50));
        JLabel footerLabel = new JLabel("© 2026 Online Course Registration System");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        footer.add(footerLabel);
        // Add to Main Panel
        mainPanel.add(topSection, BorderLayout.NORTH);
        mainPanel.add(centerSection, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);
        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        // =========================
        // Button Actions
        // =========================
        loginButton.addActionListener(e -> {
            centerSection.removeAll();
            centerSection.setLayout(new BorderLayout());
            centerSection.add(new LoginPage(), BorderLayout.CENTER);
            centerSection.revalidate();
            centerSection.repaint();
        });

        registerButton.addActionListener(e -> {
            centerSection.removeAll();
            centerSection.setLayout(new BorderLayout());
            centerSection.add(new RegistrationForm(), BorderLayout.CENTER);
            centerSection.revalidate();
            centerSection.repaint();
        });
        // =========================
        // Menu Actions
        // =========================
        homeMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showHomeContent();
            }
        });
        aboutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showAboutContent();
            }
        });
        contactMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showContactContent();
            }
        });
        setVisible(true);
    }
    // =========================
    // Home Content
    // =========================
    public void showHomeContent() {
        centerSection.removeAll();
        centerSection.setLayout(new BoxLayout(centerSection, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Learn & Grow with Our Courses");
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(darkBlue);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(backgroundColor);
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
            // --- 1. Hero & Search Section ---
            JPanel heroPanel = new JPanel();
            heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
            heroPanel.setOpaque(false);
            // Search Bar Panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            searchPanel.setOpaque(false);
            JTextField searchField = new JTextField("Search for courses...", 25);
            searchField.setPreferredSize(new Dimension(300, 40));
            searchField.setFont(new Font("Arial", Font.PLAIN, 14));
            JButton btnSearch = new JButton("Search");
            btnSearch.setBackground(lightBlue);
            btnSearch.setForeground(Color.WHITE);
            btnSearch.setPreferredSize(new Dimension(100, 40));
            btnSearch.setFocusPainted(false);
            searchPanel.add(searchField);
            searchPanel.add(btnSearch);
            heroPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            heroPanel.add(title);
            heroPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            heroPanel.add(searchPanel);
            // --- 2. Course Cards Section ---
            JPanel cardContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
            cardContainer.setOpaque(false);
            // ካርዶቹን እዚህ ጋር እንጨምራለን
            cardContainer.add(createModernCard("Java Programming", "C:\\Users\\HP\\Pictures\\Screenshots\\photo.png", "Master Java from basics to advanced."));
            cardContainer.add(createModernCard("Web Development", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 131303.png", "Build responsive websites using React."));
            cardContainer.add(createModernCard("UI/UX Design", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 131406.png", "Learn modern design principles."));
          cardContainer.add(createModernCard("C++ Programming", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 225230.png", "Master C++ from basics to advanced."));
          cardContainer.add(createModernCard("Database System", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 225945.png", "Master database from basics to advanced."));
         cardContainer.add(createModernCard("NestJs Programming", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 225345.png", "Master nestjs from basic to advanced"));

        centerSection.add(heroPanel, BorderLayout.NORTH);
            centerSection.add(cardContainer, BorderLayout.CENTER);
            centerSection.revalidate();
            centerSection.repaint();
        }
    // =========================
    // About Us Content
    // =========================
    public void showAboutContent() {
        centerSection.removeAll();
        JLabel aboutTitle = new JLabel("About Us");
        aboutTitle.setFont(new Font("Arial", Font.BOLD, 24));
        aboutTitle.setForeground(darkBlue);

        JTextArea aboutText = new JTextArea();
        aboutText.setText(
                "Our Online Course Registration System is designed to help students register for courses easily and quickly.\n\n"
                        + "This system allows students to explore available courses, create accounts, login securely, and manage their registration process.\n\n"
                        + "Our mission is to provide a modern, simple, and user-friendly platform for all students."
        );
        styleTextArea(aboutText);
        centerSection.add(aboutTitle);
        centerSection.add(Box.createRigidArea(new Dimension(0, 20)));
        centerSection.add(aboutText);
        centerSection.revalidate();
        centerSection.repaint();
    }

    // =========================
    // Contact Us Content
    // =========================
    public void showContactContent() {
        centerSection.removeAll();
        JLabel contactTitle = new JLabel("Contact Us");
        contactTitle.setFont(new Font("Arial", Font.BOLD, 24));
        contactTitle.setForeground(darkBlue);
        JTextArea contactText = new JTextArea();
        contactText.setText(
                "Email: deva@gmail.com.com\n\n"+
                "Email: belaya@gmail.com.com\n\n"+
                    "Email: belaynew@gmail.com.com\n\n"+
                      "Email: biruke@gmail.com.com\n\n"
                        + "Phone: +251 900 000 000\n\n"
                        + "Address: Woldia, Ethiopia\n\n"
                        + "We are available Monday to Friday from 8:00 AM to 5:00 PM."
        );
        styleTextArea(contactText);
        centerSection.add(contactTitle);
        centerSection.add(Box.createRigidArea(new Dimension(0, 20)));
        centerSection.add(contactText);
        centerSection.revalidate();
        centerSection.repaint();
    }
    // =========================
    // Button Style
    // =========================
    public void styleButton(JButton button) {
        button.setBackground(lightBlue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setMaximumSize(new Dimension(180, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    // =========================
    // TextArea Style
    // =========================
    public void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(backgroundColor);
        textArea.setForeground(Color.DARK_GRAY);
    }
    private JPanel createModernCard(String title, String imagePath, String description) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(220, 300));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        // Image Label
        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath);
        // ምስሉ በካርዱ ልክ እንዲሰፋ (Resize)
        Image img = icon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(img));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Text Content Panel
        JPanel textContent = new JPanel(new GridLayout(3, 2));
        textContent.setOpaque(false);
        textContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblDesc = new JLabel("<html><body style='width: 150px'>" + description + "</body></html>");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDesc.setForeground(Color.GRAY);
        JButton btnEnroll = new JButton("Enroll Now");
        btnEnroll.setBackground(new Color(46, 204, 113));
        btnEnroll.setForeground(Color.WHITE);
        btnEnroll.setFocusPainted(false);
        textContent.add(lblTitle);
        textContent.add(lblDesc);
        textContent.add(btnEnroll);
        card.add(imgLabel, BorderLayout.NORTH);
        card.add(textContent, BorderLayout.CENTER);
        return card;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new index());
    }
}