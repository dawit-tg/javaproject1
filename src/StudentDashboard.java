

//    private JPanel createMyCoursesPanel() {
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        panel.add(new JLabel(""));
//        return panel;
//    }
//    private JScrollPane createHelpPanel() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
//        panel.setBackground(Color.WHITE);
//        // Header
//        JLabel title = new JLabel("Help center");
//        title.setFont(new Font("Arial", Font.BOLD, 24));
//        title.setAlignmentX(Component.LEFT_ALIGNMENT);
//        panel.add(title);
//        panel.add(Box.createRigidArea(new Dimension(0, 20)));
//        String[][] faqs = {
//            {"how to course register?", "Course Catalog page choose interest።"},
//            {"can you change password?", "yes፣ Settings page change ።"},
//            {"dont'see register course?", "please Logout try login or Admin contact።"}
//        };
//        for (String[] faq : faqs) {
//            JLabel question = new JLabel("Quastion፡ " + faq[0]);
//            question.setFont(new Font("Arial", Font.BOLD, 14));
//            JLabel answer = new JLabel("Response፡ " + faq[1]);
//            answer.setFont(new Font("Arial", Font.PLAIN, 13));
//            answer.setForeground(Color.GRAY);
//
//            panel.add(question);
//            panel.add(answer);
//            panel.add(Box.createRigidArea(new Dimension(0, 15)));
//        }
//        return new JScrollPane(panel);
//    }

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

class StudentDashboard extends JFrame {
    private String studentId;
    JPanel sidebar, headerpanel, mainContent;
    CardLayout cardLayout;
    private DefaultTableModel model;

    public StudentDashboard(String name) {
        ImageIcon mm = new ImageIcon("D:\\istockphoto-1757344400-1024x1024.jpg");
        setIconImage(mm.getImage());
        this.studentId = name;
        setTitle("Student Dashboard");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //  Header Panel
        headerpanel = new JPanel(new BorderLayout());
        headerpanel.setBackground(new Color(44, 62, 80));
        headerpanel.setPreferredSize(new Dimension(800, 85));
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
        headerpanel.add(profile , BorderLayout.WEST);
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new index().setVisible(true);
            }
        });
        //center
        JPanel titleSearchPanel = new JPanel(new GridLayout(2, 1));
        titleSearchPanel.setOpaque(false);
        JLabel title = new JLabel("Welcome to Student Dashboard " + studentId);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Search Bar
        JTextField searchBar = new JTextField("Search courses, teachers, notes...");
        searchBar.setPreferredSize(new Dimension(350, 25));
        JPanel searchContainer = new JPanel();
        searchContainer.setOpaque(false);
        searchContainer.add(searchBar);
        titleSearchPanel.add(title);
        titleSearchPanel.add(searchContainer);
        headerpanel.add(titleSearchPanel, BorderLayout.CENTER);
        searchBar.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchBar.getText().equals("Search courses, teachers, notes...")) {
                    searchBar.setText("");
                    searchBar.setForeground(java.awt.Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // customer cannot enter this backto text
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(java.awt.Color.GRAY);
                    searchBar.setText("Search courses, teachers, notes...");
                }
            }
        });
        // pfofile id and gpa
        JPanel rightInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightInfoPanel.setOpaque(false);

        JPanel idGpaPanel = new JPanel(new GridLayout(2, 1));
        idGpaPanel.setOpaque(false);
        JLabel idLabel = new JLabel("ID: BD2024  ");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel gpaLabel = new JLabel("GPA: 3.85  ");
        gpaLabel.setForeground(new Color(46, 204, 113)); // አረንጓዴ ከለር
        gpaLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        idGpaPanel.add(idLabel);
        idGpaPanel.add(gpaLabel);

        JButton profileBtn = imageIcon();
        rightInfoPanel.add(idGpaPanel);
        rightInfoPanel.add(profileBtn);

        headerpanel.add(rightInfoPanel, BorderLayout.EAST);
        add(headerpanel, BorderLayout.NORTH);
        // --- Sidebar
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(200, 700));

        String[] navItems = {"🏠 Dashboard", "📚 Course Catalog", "📖 My Courses", "⚙️ Settings"};
        for (String item : navItems) {
            JButton btn = new JButton(item);
            // rfg
            btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            btn.setMaximumSize(new Dimension(200, 50));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(52, 73, 94));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);

            //card name
            String cardName = item.contains(" ") ? item.substring(3) : item;
            btn.addActionListener(e -> cardLayout.show(mainContent, cardName));
            sidebar.add(btn);
        }
        add(sidebar, BorderLayout.WEST);
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.add(createDashboardPanel(), "Dashboard");
        mainContent.add(createCourseCatalogPanel(), "Course Catalog");
        mainContent.add(createMyCoursesPanel(), "My Courses");
        mainContent.add(createSettingsPanel() ,"Settings");
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
//================================================================
// ===setting main components=====
// ==================================================
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        content.setBackground(Color.WHITE);
        content.setAlignmentX(Component.CENTER_ALIGNMENT);
        // 1. title
        JLabel title = new JLabel("Account Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(title);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // 2.profile information
        content.add(createSubTitle("Profile Information"));
        content.add(new JLabel("Full Name: " + studentId));
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(new JLabel("Email: " + studentId.toLowerCase() + "@university.edu"));
        content.add(Box.createRigidArea(new Dimension(0, 25)));

        // 3. Change Password Section
        content.add(createSubTitle("Security & Password"));
        content.add(new JLabel("Current Password"));
        JPasswordField oldPass = new JPasswordField();
        oldPass.setMaximumSize(new Dimension(400, 35));
        content.add(oldPass);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        content.add(new JLabel("New Password"));
        JPasswordField newPass = new JPasswordField();
        newPass.setMaximumSize(new Dimension(400, 35));
        content.add(newPass);
        content.add(Box.createRigidArea(new Dimension(0, 20)));

      /// //////////////////
        content.add(createSubTitle("Preferences"));
        JCheckBox emailNotify = new JCheckBox("Receive Email Notifications");
        emailNotify.setBackground(Color.WHITE);
        content.add(emailNotify);
        JCheckBox darkMode = new JCheckBox("Enable Dark Mode (Beta)");
        darkMode.setBackground(Color.WHITE);
        content.add(darkMode);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // 5. Save Button
        JButton saveBtn = new JButton("Save All Changes");
        saveBtn.setPreferredSize(new Dimension(150, 40));
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Settings saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        content.add(saveBtn);

        panel.add(new JScrollPane(content), BorderLayout.CENTER);
        return panel;
    }
    //
    private JLabel createSubTitle(String text) {
        JLabel sub = new JLabel(text);
        sub.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sub.setForeground(new Color(52, 73, 94));
        sub.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sub;
    }
    //button logo profile
    private JButton imageIcon() {
        JButton btn = new JButton("Biruk ▼");
        btn.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\HP\\Pictures\\Biruk.jpg").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        JPopupMenu menu = new JPopupMenu();
        menu.add(new JMenuItem("Account"));
        menu.add(new JMenuItem("Help"));
        menu.addSeparator();
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            int a = JOptionPane.showConfirmDialog(this, "Logout?");
            if (a == JOptionPane.YES_OPTION) {
                this.dispose();
                new index().setVisible(true);
            }
        });
        menu.add(logout);
        btn.addActionListener(e -> menu.show(btn, 0, btn.getHeight()));
        return btn;
    }
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 70));
        panel.setBackground(Color.WHITE);
        panel.add(createStatCard("Total Courses", "12", new Color(52, 152, 219)));
        panel.add(createStatCard("My Courses", "3", new Color(46, 204, 113)));
        panel.add(createStatCard("New Messages", "5", new Color(231, 76, 60)));
        panel.add(createStatCard("Pending Registrations", "10", new Color(231, 76, 60)));
        panel.add(createStatCard("List", "5", new Color(220, 76, 30)));
        panel.add(createStatCard("Complete certificate", "5", new Color(231, 166, 80)));
        panel.add(createStatCard("Total Courses", "12", new Color(52, 152, 219)));
        panel.add(createStatCard("My Courses", "3", new Color(46, 204, 113)));
        panel.add(createStatCard("New Messages", "5", new Color(231, 76, 60)));
        panel.add(createStatCard("Pending Registrations", "10", new Color(231, 76, 60)));
        panel.add(createStatCard("List", "5", new Color(220, 76, 30)));
        panel.add(createStatCard("Complete certificate", "5", new Color(231, 166, 80)));
        panel.add(createStatCard("Total Courses", "12", new Color(52, 152, 219)));
        panel.add(createStatCard("My Courses", "3", new Color(46, 204, 113)));
        panel.add(createStatCard("New Messages", "5", new Color(231, 76, 60)));
        panel.add(createStatCard("Pending Registrations", "10", new Color(231, 76, 60)));
        panel.add(createStatCard("List", "5", new Color(220, 76, 30)));
        panel.add(createStatCard("Complete certificate", "5", new Color(231, 166, 80)));
        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setPreferredSize(new Dimension(200, 100));
        card.setBackground(color);
        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        JLabel lblValue = new JLabel(value, JLabel.CENTER);
        lblValue.setFont(new Font("Arial", Font.BOLD, 30));
        lblValue.setForeground(Color.WHITE);
        card.add(lblTitle);
        card.add(lblValue);
        return card;
    }

    private JPanel createCourseCatalogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lbl = new JLabel("(Available Courses)");
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lbl, BorderLayout.NORTH);

        String[] columns = {"Course ID", "Course Name", "Duration", "Price"};
        Object[][] data = {
            {"CS101", "Java Programming", "3 Months", "2000 ETB"},
            {"DB202", "Database Systems", "2 Months", "1500 ETB"},
            {"WEB303", "React JS", "2.5 Months", "2500 ETB"},
            {"PH1023", "Phytone proramming", "3 Months", "3000 ETB"},
            {"NJ1000", "NestJs proramming", "4 Months", "4000 ETB"}
        };

        JTable table = new JTable(data, columns);
        table.setRowHeight(40);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(50, 50, 50));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JButton regBtn = new JButton("now register (Register)");
        regBtn.setBackground(new Color(46, 204, 113));
        regBtn.setForeground(Color.WHITE);
        panel.add(regBtn, BorderLayout.SOUTH);
        regBtn.addActionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String id = table.getValueAt(row, 0).toString();
                String name = table.getValueAt(row, 1).toString();
                String duration = table.getValueAt(row, 2).toString();
                String price = table.getValueAt(row, 3).toString();
                insertToDatabase(id, name, duration, price);
            } else {
                JOptionPane.showMessageDialog(null, "First select course");
            }
        });
        return panel;
    }
    void insertToDatabase(String id, String name, String duration, String price) {
        String url = "jdbc:postgresql://localhost:5432/onlinecourse";
        String dbUser = "postgres";
        String dbPass = "1453";
        String query = "INSERT INTO registered_courses(course_id, course_name, duration, price) VALUES(?, ?, ?, ?)";
        try (Connection connect = DriverManager.getConnection(url, dbUser, dbPass)) {
            PreparedStatement pro = connect.prepareStatement(query);
            pro.setString(1, id);
            pro.setString(2, name);
            pro.setString(3, duration);
            pro.setString(4, price);
            int result = pro.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Registered successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }
    }
    private JPanel createMyCoursesPanel() {
        return new JPanel();
    }
        public static void main (String[] args){
            SwingUtilities.invokeLater(() -> new StudentDashboard("Biruk").setVisible(true));
        }
    }
