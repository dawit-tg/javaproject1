
import javax.swing.RowFilter;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class index extends JFrame {
    JPanel centerSection;
    Color darkBlue = new Color(33 , 45 , 62);
    Color lightBlue = new Color(52 , 152 , 219);
    Color sidebarColor = new Color(44 , 62 , 80);
    Color backgroundColor = new Color(248, 250, 252);
    public index() {
        ImageIcon mm = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\regi.png");
        setIconImage(mm.getImage());
        setTitle("Online Course Registration System");
        setSize(1000 , 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // =========================
        // Menu Bar
        // =========================
//        JMenuBar menuBar = new JMenuBar();
//        JMenu homeMenu = new JMenu("Home");
//        JMenu aboutMenu = new JMenu("About Us");
//        JMenu contactMenu = new JMenu("Contact Us");
//        homeMenu.setForeground(Color.WHITE);
//        aboutMenu.setForeground(Color.WHITE);
//        contactMenu.setForeground(Color.WHITE);
//        menuBar.add(homeMenu);
//        menuBar.add(aboutMenu);
//        menuBar.add(contactMenu);
//        setJMenuBar(menuBar);
//        menuBar.setBackground(new Color(44, 62, 80));
//        menuBar.setForeground(Color.WHITE);
        // =========================
        // Main Panel
        // =========================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        // ------------------------------------------Header-------------------
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(new Color(44, 62, 80));
        topSection.setPreferredSize(new Dimension(0 , 80));
        JLabel welcomeLabel = new JLabel("Welcome to Online Course Registration System");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI" , Font.BOLD , 26));
       topSection.add(welcomeLabel,BorderLayout.CENTER);
        // Center Section
        centerSection = new JPanel();
        centerSection.setBackground(backgroundColor);
        centerSection.setLayout(new BoxLayout(centerSection , BoxLayout.Y_AXIS));
        centerSection.setBorder(new EmptyBorder(30 , 40 , 30 , 40));
        showHomeContent();
        ///logo
        ImageIcon logo = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-21 233222.png");
        JLabel profile = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth() , getHeight());
                g2.setClip(new java.awt.geom.Ellipse2D.Double(0 , 0 , size , size));
                g2.drawImage(logo.getImage() , 0 , 0 , size , size , this);
                g2.dispose();
            }
        };
        profile.setPreferredSize(new Dimension(50 , 50));
        topSection.add(profile , BorderLayout.WEST);
        mainPanel.add(topSection,BorderLayout.NORTH);
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new index().setVisible(true);
            }
        });
        //logo
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS));
        rightSidePanel.setOpaque(false);
        ImageIcon logo1 = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-20 113447.png");
        JLabel profiles = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth(), getHeight());
                g2.setClip(new java.awt.geom.Ellipse2D.Double(0, 0, size, size));
                g2.drawImage(logo1.getImage(), 0, 0, size, size, this);
                g2.dispose();
            }
        };
        profiles.setPreferredSize(new Dimension(50, 50));
        profiles.setMaximumSize(new Dimension(50, 50));
        profiles.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel pro = new JLabel("Guest");
        pro.setForeground(java.awt.Color.WHITE);
        pro.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
        pro.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightSidePanel.add(profiles);
        rightSidePanel.add(Box.createVerticalStrut(5));
        rightSidePanel.add(pro);
        topSection.add(rightSidePanel, BorderLayout.EAST);
        JPopupMenu dropdown = new JPopupMenu();
        JMenuItem contact = new JMenuItem("Contact Us");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem profle = new JMenuItem("Profiles");
        dropdown.add(contact);
        dropdown.add(help);
        dropdown.add(profle);
        profiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dropdown.show(profiles , 0 , profiles.getHeight());
            }
        });
        // =========================================================
        //                        Sidebar                        //
        // ==================================================
        JPanel sideBar = new JPanel();
        sideBar.setBackground(sidebarColor);
        sideBar.setPreferredSize(new Dimension(220 , 0));
        sideBar.setLayout(new BoxLayout(sideBar , BoxLayout.Y_AXIS));
        sideBar.setBorder(BorderFactory.createEmptyBorder(80 , 15, 20 , 15));
        //course system
        JLabel sideTitle = new JLabel("Course System");
        sideTitle.setForeground(Color.WHITE);
        sideTitle.setFont(new Font("Arial" , Font.BOLD , 22));
        sideTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        //sidebar button
        JButton login = new JButton("Login");
        JButton register = new JButton("Registration");
        styleButton(login);
        styleButton(register);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);
        sideBar.add(sideTitle);
        sideBar.add(Box.createVerticalStrut(30));
        sideBar.add(login);
        sideBar.add(Box.createVerticalStrut(15));
        sideBar.add(register);
        // Footer Section
        JPanel footer = new JPanel();
        footer.setBackground(darkBlue);
        footer.setPreferredSize(new Dimension(700 , 50));
        JLabel footerLabel = new JLabel("© 2026 Online Course Registration System");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial" , Font.PLAIN , 30));
        footer.add(footerLabel);
        // Add to Main Panel
        mainPanel.add(topSection , BorderLayout.NORTH);
        mainPanel.add(centerSection , BorderLayout.CENTER);
        mainPanel.add(footer , BorderLayout.SOUTH);
        add(sideBar , BorderLayout.WEST);
        add(mainPanel , BorderLayout.CENTER);
        login.addActionListener(e -> {
            centerSection.removeAll();
            centerSection.setLayout(new BorderLayout());
            centerSection.add(new LoginPage() , BorderLayout.CENTER);
            centerSection.revalidate();
            centerSection.repaint();
        });
        register.addActionListener(e -> {
            centerSection.removeAll();
            centerSection.setLayout(new BorderLayout());
            centerSection.add(new RegistrationForm() , BorderLayout.CENTER);
            centerSection.revalidate();
            centerSection.repaint();
        });
        // =========================
        // Menu Actions
        // =========================
//        homeMenu.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                showHomeContent();
//            }
//        });
//        aboutMenu.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                showAboutContent();
//            }
//        });
        contact.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showContactContent();
            }
            private void showContactContent() {
                centerSection.removeAll();
                JLabel contacts = new JLabel("Contact Us");
                contacts.setFont(new Font("Arial", Font.BOLD, 24));
                contacts.setForeground(darkBlue);
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
                centerSection.add(contact);
                centerSection.add(Box.createRigidArea(new Dimension(0, 20)));
                centerSection.add(contactText);
                centerSection.revalidate();
                centerSection.repaint();
            }
        });
        setVisible(true);
    }
   //  =========================
    // Home Content
    // =========================
    public void showHomeContent() {
        centerSection.removeAll();
        centerSection.setLayout(new BoxLayout(centerSection , BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Learn & Grow with Our Courses");
        title.setFont(new Font("Arial" , Font.BOLD , 42));
        title.setForeground(darkBlue);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(backgroundColor);
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER , 20 , 10));

        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setOpaque(false);
        heroPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        heroPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 160));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        JTextField searchField = new JTextField("Search for courses...", 25);
        searchField.setPreferredSize(new Dimension(280, 38));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton btnSearch = new JButton("🔍");
        btnSearch.setBackground(lightBlue);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setPreferredSize(new Dimension(100, 40));
        btnSearch.setFocusPainted(false);
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        heroPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        heroPanel.add(title);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        heroPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        heroPanel.add(searchPanel);
        JPanel cardContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        cardContainer.setOpaque(false);
        cardContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        centerSection.add(heroPanel, BorderLayout.NORTH);
        centerSection.add(cardContainer, BorderLayout.CENTER);

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search courses, teachers, notes...")) {
                    searchField.setText("");
                    searchField.setForeground(java.awt.Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // customer cannot enter this backto text
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(java.awt.Color.GRAY);
                    searchField.setText("Search courses, teachers, notes...");
                }
            }
        });
//        searchField.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                search();
//            }
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                search();
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//
//            }
//            public void search() {
//                String text = searchField.getText();
//                if (text.trim().length() == 0) {
//                    sort.setRowFilter(null);
//                } else {
//                      sort.setRowFilter(RowFilter.regexFilter("(?i)"+java.util.regex.Pattern.quote(text)));
//                }
//            }
//        });
           /// //////////////////////////////cardCntainers sample////////////////////////////////
            cardContainer.add(createModernCard("Java Programming", "C:\\Users\\HP\\Pictures\\Screenshots\\photo.png", "Master Java from basics to advanced."));
            cardContainer.add(createModernCard("Web Development", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 131303.png", "Build responsive websites using React."));
            cardContainer.add(createModernCard("UI/UX Design", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 131406.png", "Learn modern design principles."));
          cardContainer.add(createModernCard("C++ Programming", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 225230.png", "Master C++ from basics to advanced."));
          cardContainer.add(createModernCard("Database System", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 225945.png", "Master database from basics to advanced."));
         cardContainer.add(createModernCard("NestJs Programming", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-11 225345.png", "Master nestjs from basic to advanced"));
        cardContainer.add(createModernCard("software design", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-26 213750.png", "design from basic to advanced"));
        cardContainer.add(createModernCard("computer architecture", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-26 213750.png", "Master computer architecturesfrom basic to advanced"));
        cardContainer.add(createModernCard("Node Backend", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-26 213750.png", "Master nodebackend from basic to advanced"));
        cardContainer.add(createModernCard("ReactJs", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-26 214315.png", "Master ReactJs from basic to advanced"));
        cardContainer.add(createModernCard("C#", "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-26 214315.png", "MasterC# from basic to advanced"));

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
    // Button Style
    // =========================
    public void styleButton(JButton button) {
        button.setBackground(new Color(52 , 152 , 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI" , Font.BOLD , 15));
        button.setBorder(BorderFactory.createEmptyBorder(12 , 20 , 12 , 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(180 , 45));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(41 , 128 , 185));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(52 , 152 , 219));
            }
        });
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