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
        menuBar.setBackground(Color.WHITE);

        JMenu homeMenu = new JMenu("Home");
        JMenu aboutMenu = new JMenu("About Us");
        JMenu contactMenu = new JMenu("Contact Us");

        menuBar.add(homeMenu);
        menuBar.add(aboutMenu);
        menuBar.add(contactMenu);

        setJMenuBar(menuBar);

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
        topSection.setBackground(lightBlue);
        topSection.setPreferredSize(new Dimension(700, 180));

        JLabel welcomeLabel = new JLabel("Welcome to Online Course Registration System");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
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
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 14));

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
            centerSection.add(new LoginForm(), BorderLayout.CENTER);
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
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(darkBlue);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(backgroundColor);
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Load images
        ImageIcon bookIcon = new ImageIcon("C:/Users/hp/IdeaProjects/devo/JavaProject/src/images/s.jpeg");
        JLabel bookLabel = new JLabel(bookIcon);

        ImageIcon studentIcon = new ImageIcon("C:/Users/hp/IdeaProjects/devo/JavaProject/src/images/b.png");

        JLabel studentLabel = new JLabel(studentIcon);

        imagePanel.add(bookLabel);
        imagePanel.add(studentLabel);

        // Description text
        JTextArea description = new JTextArea();
        description.setText(
                "Welcome to our Online Course Registration System! We provide high-quality courses for all learners.\n\n"
                        + "📘 Browse and register for courses\n"
                        + "🎓 Learn from expert instructors\n"
                        + "👩‍🎓 Join a community of engaged students\n"
                        + "🚀 Grow your skills with us!"
        );
        styleTextArea(description);

        centerSection.add(title);
        centerSection.add(Box.createRigidArea(new Dimension(0, 15)));
        centerSection.add(imagePanel);
        centerSection.add(Box.createRigidArea(new Dimension(0, 15)));
        centerSection.add(description);

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
                "Email: support@coursesystem.com\n\n"
                        + "Phone: +251 900 000 000\n\n"
                        + "Address: Addis Ababa, Ethiopia\n\n"
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new index());
    }
}