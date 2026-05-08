import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class index extends JFrame {
    public static String currentStudent;
    public static String studentName;
    JPanel cardContainer;
    java.util.List<JPanel> allCards = new ArrayList<>();
    JTextField searchField;
    JPanel centerSection;

    // Colors
    Color darkBlue = new Color(33, 45, 62);
    Color lightBlue = new Color(52, 152, 219);
    Color sidebarColor = new Color(255, 255, 255);
    Color backgroundColor = new Color(241, 245, 249);

    // Login State
    public boolean isLoggedIn = false;

    // Variables to remember which course user clicked before login
    public String pendingEnrollTitle = null;
    public double pendingEnrollPrice = 0.0;

    public index(String userName) {
        centerSection = new JPanel();
        currentStudent = userName;

        // Setup Frame
        try {
            ImageIcon mm = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\regi.png");
            setIconImage(mm.getImage());
        } catch (Exception e) {
            // Handle missing image gracefully
        }

        setTitle("Online Course Registration System");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ========================= Header =========================
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(new Color(255, 255, 255));
        topSection.setPreferredSize(new Dimension(0, 80));
        topSection.setBackground(new Color(241, 245, 249));
        topSection.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));
        add(topSection, BorderLayout.NORTH);

        JLabel welcomeLabel = new JLabel("Welcome to Online Course Registration System");
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topSection.add(welcomeLabel, BorderLayout.CENTER);

        // Logo (Left)
        JLabel profile = new JLabel();
        try {
            ImageIcon logo = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-21 233222.png");
            profile = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int size = Math.min(getWidth(), getHeight());
                    g2.setClip(new java.awt.geom.Ellipse2D.Double(0, 0, size, size));
                    g2.drawImage(logo.getImage(), 0, 0, size, size, this);
                    g2.dispose();
                }
            };
        } catch (Exception e) {
            profile.setText("LOGO");
            profile.setHorizontalAlignment(SwingConstants.CENTER);
        }
        profile.setPreferredSize(new Dimension(50, 50));
        topSection.add(profile, BorderLayout.WEST);
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showHomeContent();
            }
        });

        // Profile (Right)
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS));
        rightSidePanel.setOpaque(false);
        rightSidePanel.setBorder(new EmptyBorder(10, 0, 0, 20));

        JLabel profiles = new JLabel();
        try {
            ImageIcon logo1 = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-20 113447.png");
            profiles = new JLabel() {
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
        } catch(Exception e) {
            profiles.setText("USR");
            profiles.setHorizontalAlignment(SwingConstants.CENTER);
        }
        profiles.setPreferredSize(new Dimension(50, 50));
        profiles.setMaximumSize(new Dimension(50, 50));
        profiles.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Dropdown Menu
        JPopupMenu profileMenu = new JPopupMenu();
        profileMenu.setBackground(Color.WHITE);
        profileMenu.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        JMenuItem profile12 = new JMenuItem("MyProfile");
        JMenuItem myc = new JMenuItem("MyCourses");
        JMenuItem Logout = new JMenuItem("Logout");
        Font menuFont = new Font("Segoe UI", Font.PLAIN, 14);
        profile12.setFont(menuFont);
        myc.setFont(menuFont);
        Logout.setFont(menuFont);
        Logout.setForeground(Color.RED);

        profileMenu.add(profile12);
        profileMenu.add(myc);
        profileMenu.addSeparator();
        profileMenu.add(Logout);

        profile12.addActionListener(e -> {
            showProfileContent();
        });

        myc.addActionListener(e -> {
            showMyCoursesContent();
        });

        Logout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "are you sure?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                isLoggedIn = false;
                currentStudent = "Guest";
                showHomeContent();
            }
        });

        profiles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel finalProfiles = profiles;
        JLabel finalProfiles1 = profiles;
        profiles.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                profileMenu.show(finalProfiles, 0, finalProfiles1.getHeight());
            }
        });

        JLabel pro = new JLabel(currentStudent);
        pro.setForeground(Color.BLACK);
        pro.setFont(new Font("SansSerif", Font.BOLD, 12));
        pro.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightSidePanel.add(profiles);
        rightSidePanel.add(Box.createVerticalStrut(5));
        rightSidePanel.add(pro);
        topSection.add(rightSidePanel, BorderLayout.EAST);

        // ========================= Sidebar =========================
        JPanel sideBar = new JPanel();
        sideBar.setBackground(sidebarColor);
        sideBar.setPreferredSize(new Dimension(220, 0));
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(226, 232, 240)));
        add(sideBar, BorderLayout.WEST);

        JLabel sideTitle = new JLabel("Course System");
        sideTitle.setForeground(Color.BLACK);
        sideTitle.setFont(new Font("Arial", Font.BOLD, 22));
        sideTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginBtn = new JButton("\uD83D\uDD11 Login");
        JButton register = new JButton("\uD83C\uDD94 Registration");
        styleButton(loginBtn);
        styleButton(register);
        Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 14);
        loginBtn.setFont(emojiFont);
        register.setFont(emojiFont);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        sideBar.add(sideTitle);
        sideBar.add(Box.createVerticalStrut(30));
        sideBar.add(loginBtn);
        sideBar.add(Box.createVerticalStrut(15));
        sideBar.add(register);

        loginBtn.addActionListener(e -> {
            showLoginContent();
        });

        register.addActionListener(e -> {
            centerSection.removeAll();
            centerSection.setLayout(new BorderLayout());
            centerSection.add(new RegistrationForm(), BorderLayout.CENTER);
            centerSection.revalidate();
            centerSection.repaint();
        });

        // ========================= MAIN CENTER PANEL =========================
        centerSection = new JPanel();
        centerSection.setBackground(backgroundColor);
        centerSection.setLayout(new BoxLayout(centerSection, BoxLayout.Y_AXIS));
        centerSection.setBorder(new EmptyBorder(30, 40, 30, 40));

        JScrollPane scrollPane = new JScrollPane(centerSection);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getViewport().setBackground(new Color(249, 250, 251));
        add(scrollPane, BorderLayout.CENTER);

        // ========================= FOOTER =========================
        JPanel footer = new JPanel(new BorderLayout(30, 0));
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)));

        JPanel leftFooter = new JPanel(new GridLayout(2, 1, 0, 5));
        leftFooter.setOpaque(false);
        JLabel footerBrand = new JLabel("Online Course System");
        footerBrand.setFont(new Font("Segoe UI", Font.BOLD, 16));
        footerBrand.setForeground(new Color(30, 41, 59));

        JLabel copyRight = new JLabel("© 2026 All Rights Reserved.");
        copyRight.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        copyRight.setForeground(new Color(100, 116, 139));

        leftFooter.add(footerBrand);
        leftFooter.add(copyRight);

        JPanel centerFooter = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        centerFooter.setOpaque(false);

        String[] footerLinks = {"Privacy Policy", "Terms of Service", "Help Center", "Contact Us"};
        for (String linkText : footerLinks) {
            JLabel link = new JLabel(linkText);
            Font baseFont = new Font("Segoe UI", Font.PLAIN, 14);
            Map<TextAttribute, Object> attributes = new HashMap<>(baseFont.getAttributes());
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_MEDIUM);
            link.setFont(baseFont.deriveFont(attributes));
            link.setForeground(new Color(71, 85, 105));
            link.setCursor(new Cursor(Cursor.HAND_CURSOR));

            link.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    link.setForeground(new Color(37, 99, 235));
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    link.setForeground(new Color(71, 85, 105));
                }
            });
            centerFooter.add(link);
        }
        footer.add(leftFooter, BorderLayout.WEST);
        footer.add(centerFooter, BorderLayout.CENTER);
        JPanel mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.add(scrollPane, BorderLayout.CENTER);
        mainWrapper.add(footer, BorderLayout.SOUTH);
        add(mainWrapper, BorderLayout.CENTER);

        showHomeContent();
        setVisible(true);
    }

    public void showHomeContent() {
        // Clear pending enrollment when just browsing
        pendingEnrollTitle = null;
        pendingEnrollPrice = 0.0;

        centerSection.removeAll();
        JLabel title = new JLabel("Learn & Grow with Our Courses");
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(darkBlue);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setOpaque(false);
        heroPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        JTextField searchField = new JTextField("Search for courses...", 25);
        searchField.setPreferredSize(new Dimension(280, 38));
        JButton btnSearch = new JButton("🔍");
        btnSearch.setBackground(lightBlue);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setPreferredSize(new Dimension(100, 40));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        btnSearch.addActionListener(e -> {
            performSearch(searchField.getText());
        });
        heroPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        heroPanel.add(title);
        heroPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        heroPanel.add(searchPanel);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search for courses...")) {
                    searchField.setText("");
                }
            }
        });

        JPanel cardContainer = new JPanel();
        cardContainer.setOpaque(false);
        cardContainer.setLayout(new GridLayout(0, 4, 10, 10));

        allCards.clear();

        java.util.function.BiConsumer<String, Double> addCard = (name, price) -> {
            JPanel c = createModernCard(name, "path/to/image.png", "Description for " + name, price);
            allCards.add(c);
            cardContainer.add(c);
        };

        addCard.accept("Java Programming", 2000.0);
        addCard.accept("Web Development", 2500.0);
        addCard.accept("UI/UX Design", 3300.0);
        addCard.accept("C++ Programming", 4000.0);
        addCard.accept("Database System", 678.0);
        addCard.accept("NestJs Programming", 2000.0);
        addCard.accept("software design", 4000.0);
        addCard.accept("computer architecture", 5000.0);
        addCard.accept("Node Backend", 3000.0);
        addCard.accept("ReactJs", 2000.0);
        addCard.accept("C#", 3500.0);

        centerSection.add(heroPanel);
        centerSection.add(Box.createRigidArea(new Dimension(0, 20)));
        centerSection.add(cardContainer);

        centerSection.revalidate();
        centerSection.repaint();

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterCards(searchField.getText(), cardContainer); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterCards(searchField.getText(), cardContainer); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterCards(searchField.getText(), cardContainer); }
        });
    }

    private void performSearch(String query) {
        filterCards(query, (JPanel) centerSection.getComponent(2));
    }

    private void filterCards(String query, JPanel cardContainer) {
        if (cardContainer == null) return;
        String q = query.toLowerCase().trim();
        cardContainer.removeAll();
        for (JPanel card : allCards) {
            if (card.getName() != null && card.getName().toLowerCase().contains(q)) {
                cardContainer.add(card);
            }
        }
        cardContainer.revalidate();
        cardContainer.repaint();
    }

    private void showLoginContent() {
        centerSection.removeAll();
        centerSection.setLayout(new BorderLayout());
        centerSection.add(new LoginPage(this), BorderLayout.CENTER);
        centerSection.revalidate();
        centerSection.repaint();
    }

    private void showProfileContent() {
        if (centerSection == null) { System.out.println("centersection is empty!"); return; }
        centerSection.removeAll();
        centerSection.setLayout(new GridBagLayout());
        String displayName = (currentStudent != null) ? currentStudent : "Guest";
        JPanel profileCard = new JPanel();
        profileCard.setBackground(Color.WHITE);
        profileCard.setPreferredSize(new Dimension(450, 350));
        profileCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        JLabel avatar = new JLabel("👤");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pName = new JLabel(displayName);
        pName.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pName.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel pRole = new JLabel("Student Account");
        pRole.setForeground(Color.GRAY);
        pRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel details = new JPanel(new GridLayout(3, 1, 10, 10));
        details.setOpaque(false);
        details.setBorder(new EmptyBorder(20, 40, 20, 40));

        details.add(new JLabel("Username: " + displayName.toLowerCase()));
        details.add(new JLabel("Email: " + displayName.toLowerCase() + "@onlinecourse.com"));
        details.add(new JLabel("Enrolled Since: May 2026"));

        profileCard.add(Box.createVerticalStrut(20));
        profileCard.add(avatar);
        profileCard.add(pName);
        profileCard.add(pRole);
        profileCard.add(details);

        centerSection.add(profileCard);
        centerSection.revalidate();
        centerSection.repaint();
    }

    private void showMyCoursesContent() {
        centerSection.removeAll();
        centerSection.setLayout(new BorderLayout());
        JPanel coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        coursesPanel.setBackground(Color.WHITE);
        coursesPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Your Enrolled Courses");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        coursesPanel.add(header);
        coursesPanel.add(Box.createVerticalStrut(20));
        String[] myEnrolled = {"Java Programming", "Web Development"};
        for (String course : myEnrolled) {
            JLabel cLabel = new JLabel("• " + course);
            cLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            cLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
            coursesPanel.add(cLabel);
        }

        centerSection.add(new JScrollPane(coursesPanel), BorderLayout.CENTER);
        centerSection.revalidate();
        centerSection.repaint();
    }

    private JPanel createModernCard(String title, String imagePath, String description, double price) {
        JPanel card = new JPanel();
        card.setName(title);
        card.setPreferredSize(new Dimension(220, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 233, 237), 1));
        card.setLayout(new BorderLayout(10, 10));

        JLabel imgLabel = new JLabel();
        ImageIcon icon;
        try {
            icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imgLabel.setText("Image not found");
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imgLabel.setPreferredSize(new Dimension(200, 120));
        }

        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel textContent = new JPanel(new GridLayout(3, 1));
        textContent.setOpaque(false);
        textContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblDesc = new JLabel("<html><body style='width: 150px'>" + description + "</body></html>");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDesc.setForeground(Color.GRAY);
        JButton enrollment = new JButton("Enroll Now");
        enrollment.setBackground(new Color(46, 204, 113));
        enrollment.setForeground(Color.WHITE);

        // ========================= CORE LOGIC HERE =========================
        enrollment.addActionListener(e -> {

            // 1. Check if Admin
            if (currentStudent != null && currentStudent.equalsIgnoreCase("Admin")) {
                JOptionPane.showMessageDialog(this, "Admins are not allowed to enroll in courses.");
                return;
            }

            // 2. Check if Logged In
            if (!isLoggedIn) {
                // Save details for after login
                pendingEnrollTitle = title;
                pendingEnrollPrice = price;

                JOptionPane.showMessageDialog(this, "Please login to enroll.");
                showLoginContent();
            } else {
                // 3. If already logged in, go straight to payment
                Payment.showPaymentGateway(currentStudent, title, price);

                // 4. After Payment is done (or cancelled), open Dashboard
                new StudentDashboard(currentStudent).setVisible(true);
                this.dispose();
            }
        });
        // ===============================================================

        textContent.add(lblTitle);
        textContent.add(lblDesc);
        textContent.add(enrollment);
        card.add(imgLabel, BorderLayout.NORTH);
        card.add(textContent, BorderLayout.CENTER);
        return card;
    }

    public void styleButton(JButton button) {
        button.setBackground(lightBlue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setMaximumSize(new Dimension(180, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new index("Guest"));
    }
}