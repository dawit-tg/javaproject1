import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends JFrame {
    private String studentId;
    private List<Course> myEnrolledCourses = new ArrayList<>();
    JPanel sidebar, headerpanel, mainContent;
    CardLayout cardLayout;
    private String currentStudent;

    // Course Inner Class
    static class Course {
        String title;
        int progress;
        Course(String title, int progress) {
            this.title = title;
            this.progress = progress;
        }
    }
    public StudentDashboard(String name) {
        this.studentId = name;
        this.currentStudent = name;

        ImageIcon mm = new ImageIcon("D:\\istockphoto-1757344400-1024x1024.jpg");
        setIconImage(mm.getImage());
        this.studentId = name;
        setTitle("Student Dashboard");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //  Header Panel
        headerpanel = new JPanel(new BorderLayout());
        headerpanel.setBackground(Color.WHITE);
        headerpanel.setPreferredSize(new Dimension(800, 85));
        headerpanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        //logo
        ImageIcon logo = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-21 233222.png");
        JLabel profile = new JLabel() {
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
        profile.setPreferredSize(new Dimension(50, 50));
        headerpanel.add(profile, BorderLayout.WEST);
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try { new index("studentName").setVisible(true); } catch (Exception e) {}
            }
        });

        //center
        JPanel titleSearchPanel = new JPanel(new GridLayout(2, 1));
        titleSearchPanel.setOpaque(false);
        JLabel title = new JLabel("Welcome to Student Dashboard " + studentId);
        title.setForeground(new Color(33, 37, 41));
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField searchBar = new JTextField("Search courses, teachers, notes...");
        searchBar.setPreferredSize(new Dimension(350, 30));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
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
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(java.awt.Color.GRAY);
                    searchBar.setText("Search courses, teachers, notes...");
                }
            }
        });

        JPanel rightInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightInfoPanel.setOpaque(false);
        JPanel idGpaPanel = new JPanel(new GridLayout(2, 1));
        idGpaPanel.setOpaque(false);
        JLabel idLabel = new JLabel("ID: BD2024  ");
        idLabel.setForeground(new Color(100, 100, 100));
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel gpaLabel = new JLabel("GPA: 3.85  ");
        gpaLabel.setForeground(new Color(46, 204, 113));
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
        sidebar.setBackground(Color.WHITE);
        sidebar.setPreferredSize(new Dimension(200, 700));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        String[] navItems = {"🏠 Dashboard", "📚 Course Catalog", "📖 My Courses", "⚙️ Settings"};
        for (String item : navItems) {
            JButton btn = new JButton(item);
            btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            btn.setMaximumSize(new Dimension(200, 50));
            btn.setForeground(new Color(60, 60, 60));
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            String cardName = item.contains(" ") ? item.substring(3) : item;

            btn.addActionListener(e -> {
                // Refresh My Courses when clicking
                if (cardName.equals("My Courses")) {
                    mainContent.add(createMyCoursesPanel(), "My Courses");
                }
                cardLayout.show(mainContent, cardName);
            });

            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setBackground(new Color(230, 240, 250));
                    btn.setForeground(new Color(41, 128, 185));
                }
                public void mouseExited(MouseEvent evt) {
                    btn.setBackground(Color.WHITE);
                    btn.setForeground(new Color(60, 60, 60));
                }
            });
            sidebar.add(btn);
        }
        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setBackground(new Color(240, 242, 245));
        // FIX: Margin from Header and Sidebar (Top, Left, Bottom, Right)
        mainContent.setBorder(new EmptyBorder(25, 25, 25, 25));

        mainContent.add(createDashboardPanel(), "Dashboard");
        mainContent.add(createCourseCatalogPanel(), "Course Catalog");
        mainContent.add(createMyCoursesPanel(), "My Courses");
        mainContent.add(createSettingsPanel(), "Settings");
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        content.setBackground(Color.WHITE);
        content.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel("Account Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(title);
        content.add(Box.createRigidArea(new Dimension(0, 30)));
        content.add(createSubTitle("Profile Information"));
        content.add(new JLabel("Full Name: " + studentId));
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(new JLabel("Email: " + studentId.toLowerCase() + "@university.edu"));
        content.add(Box.createRigidArea(new Dimension(0, 25)));
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
        content.add(createSubTitle("Preferences"));
        JCheckBox emailNotify = new JCheckBox("Receive Email Notifications");
        emailNotify.setBackground(Color.WHITE);
        content.add(emailNotify);
        JCheckBox darkMode = new JCheckBox("Enable Dark Mode (Beta)");
        darkMode.setBackground(Color.WHITE);
        content.add(darkMode);
        content.add(Box.createRigidArea(new Dimension(0, 30)));
        JButton saveBtn = new JButton("Save All Changes");
        saveBtn.setPreferredSize(new Dimension(150, 40));
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE));
        content.add(saveBtn);
        panel.add(new JScrollPane(content), BorderLayout.CENTER);
        return panel;
    }

    private JLabel createSubTitle(String text) {
        JLabel sub = new JLabel(text);
        sub.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sub.setForeground(new Color(52, 73, 94));
        sub.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sub;
    }

    private JButton imageIcon() {
        JButton btn = new JButton("Biruk ▼");
        btn.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\HP\\Pictures\\Biruk.jpg").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        btn.setForeground(new Color(60, 60, 60));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JPopupMenu menu = new JPopupMenu();
        JMenuItem account = new JMenuItem("Account");
        menu.add(account);
        JMenuItem help = new JMenuItem("Help");
        menu.add(help);
        menu.addSeparator();
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            int a = JOptionPane.showConfirmDialog(this, "Logout?");
            if (a == JOptionPane.YES_OPTION) {
                this.dispose();
                try { new index("Student").setVisible(true); } catch (Exception ex) {}
            }
        });
        account.addActionListener(e -> {
            mainContent.removeAll();
            mainContent.setLayout(new GridBagLayout());
            mainContent.setBackground(new Color(245, 247, 250));
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1), BorderFactory.createEmptyBorder(30, 40, 30, 40)));
            JLabel title = new JLabel("Account Settings");
            title.setFont(new Font("Segoe UI", Font.BOLD, 22));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            String nameStr = (currentStudent != null) ? currentStudent : "Guest";
            JLabel nameInfo = new JLabel("Full Name: " + nameStr);
            nameInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            nameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel emailInfo = new JLabel("Email: " + nameStr.toLowerCase() + "@onlinecourse.com");
            emailInfo.setForeground(Color.GRAY);
            emailInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            JButton update = new JButton("Update Profile");
            update.setAlignmentX(Component.CENTER_ALIGNMENT);
            update.setFocusPainted(false);
            update.setBackground(new Color(41, 128, 185));
            update.setForeground(Color.WHITE);
            update.setCursor(new Cursor(Cursor.HAND_CURSOR));
            card.add(title); card.add(Box.createVerticalStrut(20)); card.add(nameInfo); card.add(Box.createVerticalStrut(10)); card.add(emailInfo); card.add(Box.createVerticalStrut(25)); card.add(update);
            mainContent.add(card);
            mainContent.revalidate(); mainContent.repaint();
            update.addActionListener(updateEvt -> {
                String newName = JOptionPane.showInputDialog(this, "Update Username:", currentStudent);
                if (newName != null && !newName.trim().isEmpty()) {
                    currentStudent = newName; nameInfo.setText(currentStudent); title.setText("Welcome to Student Dashboard " + currentStudent);
                    JOptionPane.showMessageDialog(this, "Profile updated successfully!"); account.doClick();
                }
            });
        });
        help.addActionListener(e -> {
            mainContent.removeAll(); mainContent.setLayout(new GridBagLayout()); mainContent.setBackground(new Color(245, 247, 250));
            JPanel helpCard = new JPanel(); helpCard.setLayout(new BoxLayout(helpCard, BoxLayout.Y_AXIS)); helpCard.setBackground(Color.WHITE);
            helpCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1), BorderFactory.createEmptyBorder(30, 40, 30, 40)));
            JLabel hTitle = new JLabel("Help & Support Center"); hTitle.setFont(new Font("Segoe UI", Font.BOLD, 24)); hTitle.setForeground(new Color(44, 62, 80)); hTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            String helpText = "<html><body style='width: 320px; font-family: Segoe UI;'><p style='margin-bottom:15px;'>Have questions? Our support team is here to help you succeed in your learning journey.</p><hr style='border: 0; border-top: 1px solid #eee; margin-bottom: 15px;'><b>Customer Support:</b><br>📞 +251 911 22 33 44<br><br><b>Email Address:</b><br>📧 support@elearning.com<br><br><b>Office Hours:</b><br>Monday - Saturday (08:00 AM - 06:00 PM)</body></html>";
            JLabel content = new JLabel(helpText); content.setFont(new Font("Segoe UI", Font.PLAIN, 15)); content.setAlignmentX(Component.CENTER_ALIGNMENT);
            JButton contactBtn = new JButton("Open Support Ticket"); contactBtn.setBackground(new Color(41, 128, 185)); contactBtn.setForeground(Color.WHITE); contactBtn.setFocusPainted(false); contactBtn.setAlignmentX(Component.CENTER_ALIGNMENT); contactBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            helpCard.add(hTitle); helpCard.add(Box.createVerticalStrut(20)); helpCard.add(content); helpCard.add(Box.createVerticalStrut(25)); helpCard.add(contactBtn);
            mainContent.add(helpCard); mainContent.revalidate(); mainContent.repaint();
        });
        menu.add(logout);
        btn.addActionListener(e -> menu.show(btn, 0, btn.getHeight()));
        return btn;
    }

    private JScrollPane createDashboardPanel() {
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15)); // FIX: Zik yibelu
        panel.setBackground(new Color(240, 242, 245));
        scrollPane.setBorder(null);
        panel.setLayout(new GridLayout(0, 3, 15, 15)); // FIX: Zik yibelu gaps
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        panel.add(createStatCard("Total Courses", "12", new Color(52, 152, 219)));
        panel.add(createStatCard("My Courses", String.valueOf(myEnrolledCourses.size()), new Color(46, 204, 113)));
        panel.add(createStatCard("New Messages", "5", new Color(231, 76, 60)));
        panel.add(createStatCard("Pending Registrations", "10", new Color(155, 89, 182)));
        panel.add(createStatCard("List", "5", new Color(220, 76, 30)));
        panel.add(createStatCard("Complete certificate", "5", new Color(231, 166, 80)));

        scrollPane.setViewportView(panel);
        return scrollPane;
    }

    // FIX: Compact Cards (Zk Yibelu) with Animation
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(140, 60)); // Short Size
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, color), // Thin left border
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Animation
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                card.setBackground(new Color(245, 248, 255));
                // Scale effect imitation by changing border size slightly
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 5, 0, 0, color.darker()),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
            public void mouseExited(MouseEvent evt) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 3, 0, 0, color),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        });

        JLabel lblTitle = new JLabel(title, JLabel.LEFT);
        lblTitle.setForeground(new Color(130, 130, 130));
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JLabel lblValue = new JLabel(value, JLabel.LEFT);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValue.setForeground(new Color(33, 37, 41));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    // FIX: Fetch Course Catalog from Database using DBConnection
    private Object[][] fetchCoursesFromDatabase() {
        // USE DBConnection HERE
        Connection connect = DBConnection.getConnection();
        if (connect == null) {
            JOptionPane.showMessageDialog(null, "Could not connect to Database!");
            return new Object[0][];
        }

        String query = "SELECT id,name, duration, price FROM courses";

        List<Object[]> rows = new ArrayList<>();
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String duration = rs.getString("duration");
                String price = rs.getString("price");
                rows.add(new Object[]{id, name, duration, price + " ETB"});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Fetch Error: " + e.getMessage());
        } finally {
            try { connect.close(); } catch (Exception e) {}
        }

        return rows.toArray(new Object[0][]);
    }

    private JPanel createCourseCatalogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lbl = new JLabel("(Available Courses)");
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lbl, BorderLayout.NORTH);

        String[] columns = {"Course ID", "Course Name", "Duration", "Price"};
        // Fetching Data from Database Instead of Hardcoding
        Object[][] data = fetchCoursesFromDatabase();

        JTable table = new JTable(data, columns);
        table.setRowHeight(40);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(50, 50, 50));
        table.setSelectionBackground(new Color(230, 240, 250)); // Highlight selected row

        JScrollPane tableScroll = new JScrollPane(table);
        panel.add(tableScroll, BorderLayout.CENTER);

        JButton regBtn = new JButton("now register (Register)");
        regBtn.setBackground(new Color(46, 204, 113));
        regBtn.setForeground(Color.WHITE);
        regBtn.setFocusPainted(false);
        regBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(regBtn, BorderLayout.SOUTH);

        // FIX: Correct Enrollment Logic
        regBtn.addActionListener(event -> {
            try {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String id = table.getValueAt(row, 0).toString();
                    String courseName = table.getValueAt(row, 1).toString();
                    String duration = table.getValueAt(row, 2).toString();
                    String pricRaw = table.getValueAt(row, 3).toString();
                    double price = Double.parseDouble(pricRaw.replace(" ETB", "").trim());
                    String currentStudentId = studentId;

                    // Check if already enrolled
                    boolean alreadyEnrolled = false;
                    for (Course c : myEnrolledCourses) {
                        if (c.title.equals(courseName)) {
                            alreadyEnrolled = true;
                            break;
                        }
                    }
                    if (!alreadyEnrolled) {
                        //myEnrolledCourses.add(new Course(courseName, 0)); // Add safely
                        // Update UI
                        mainContent.add(createDashboardPanel(), "Dashboard");
                        mainContent.add(createMyCoursesPanel(), "My Courses");

                        try {
                            // Open Payment
                            Payment.showPaymentGateway(currentStudentId, courseName, price);

                            // Insert into DB after payment
                            insertToDatabase(id, courseName, duration, String.valueOf(price));

                            // Add to local list to show immediately
                            myEnrolledCourses.add(new Course(courseName, 0));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Payment error: " + ex.getMessage());
                        }

                        JOptionPane.showMessageDialog(null, "Successfully Enrolled in: " + courseName);
                        cardLayout.show(mainContent, "My Courses"); // Redirect immediately
                    } else {
                        JOptionPane.showMessageDialog(null, "You are already enrolled in this course!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "First select a course");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
        });
        return panel;
    }

    void insertToDatabase(String id, String name, String duration, String price) {
        // USE DBConnection HERE
        Connection connect = DBConnection.getConnection();
        if (connect == null) return;

        String query = "INSERT INTO registered_courses(course_id, course_name, duration, price) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement pro = connect.prepareStatement(query);
            pro.setString(1, id);
            pro.setString(2, name);
            pro.setString(3, duration);
            pro.setString(4, price);
            int result = pro.executeUpdate();
            if (result > 0) {
                System.out.println("Registered successfully in DB");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "DB ERROR: " + e.getMessage());
        } finally {
            try { connect.close(); } catch (Exception e) {}
        }
    }

    // ============================================
    // NEW FUNCTIONALITY: Load My Courses from DB
    // ============================================
    private void loadMyEnrolledCoursesFromDB() {
        myEnrolledCourses.clear(); // Clear current list

        // USE DBConnection HERE
        Connection connect = DBConnection.getConnection();
        if (connect == null) return;

        // Load from pending_registrations (where Payment class saves data)
        String query = "SELECT course_name FROM pending_registrations WHERE student_id = ?";

        try {
            PreparedStatement pst = connect.prepareStatement(query);
            pst.setString(1, studentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String courseName = rs.getString("course_name");
                myEnrolledCourses.add(new Course(courseName, 0)); // 0 progress
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { connect.close(); } catch (Exception e) {}
        }
    }

    private JPanel createMyCoursesPanel() {
        // 1. Load Data from Database first
        loadMyEnrolledCoursesFromDB();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel title = new JLabel("My Enrolled Courses (" + myEnrolledCourses.size() + ")");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(title);

        JPanel courseGrid = new JPanel();
        courseGrid.setLayout(new BoxLayout(courseGrid, BoxLayout.Y_AXIS));
        courseGrid.setOpaque(false);
        courseGrid.setBorder(new EmptyBorder(5, 5, 5, 5));

        if (myEnrolledCourses.isEmpty()) {
            JPanel emptyCard = new JPanel(new BorderLayout());
            emptyCard.setBackground(Color.WHITE);
            emptyCard.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
            emptyCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            JLabel emptyText = new JLabel("You are not enrolled in any courses yet. Go to Course Catalog to register.", JLabel.CENTER);
            emptyText.setForeground(Color.GRAY);
            emptyCard.add(emptyText, BorderLayout.CENTER);
            courseGrid.add(emptyCard);
        } else {
            for (Course c : myEnrolledCourses) {
                JPanel courseCard = new JPanel(new BorderLayout(15, 0));
                courseCard.setBackground(Color.WHITE);
                courseCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Zik/Short card
                courseCard.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)
                ));

                courseCard.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        courseCard.setBackground(new Color(240, 248, 255));
                    }
                    public void mouseExited(MouseEvent evt) {
                        courseCard.setBackground(Color.WHITE);
                    }
                });

                JLabel cTitle = new JLabel(c.title);
                cTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

                JProgressBar progress = new JProgressBar(0, 100);
                progress.setValue(c.progress);
                progress.setStringPainted(true);
                progress.setForeground(new Color(46, 204, 113));
                progress.setPreferredSize(new Dimension(120, 15));

                JButton btnContinue = new JButton("Continue");
                btnContinue.setBackground(new Color(41, 128, 185));
                btnContinue.setForeground(Color.WHITE);
                btnContinue.setFocusPainted(false);
                btnContinue.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                btnContinue.setCursor(new Cursor(Cursor.HAND_CURSOR));

                courseCard.add(cTitle, BorderLayout.WEST);
                courseCard.add(progress, BorderLayout.CENTER);
                courseCard.add(btnContinue, BorderLayout.EAST);

                courseGrid.add(courseCard);
                courseGrid.add(Box.createVerticalStrut(5));
            }
        }

        JScrollPane scrollPane = new JScrollPane(courseGrid);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(240, 242, 245));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard("Biruk").setVisible(true));
    }
}