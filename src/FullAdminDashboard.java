import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
//import StudentPanel;

public class FullAdminDashboard extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // MODERN PALETTE
    private Color sidebarColor = new Color(25, 42, 86);
    private Color backgroundColor = new Color(236, 240, 241);
    private Color cardColor = Color.WHITE;
    private Color accentBlue = new Color(52, 152, 219);
    private Color accentGreen = new Color(46, 204, 113);
    private Color accentRed = new Color(231, 76, 60);
    private Color textColor = new Color(44, 62, 80);
    private Color mutedTextColor = new Color(127, 140, 141);

    // Models and Tables
    private DefaultTableModel courseModel;
    private DefaultTableModel pending;
    private JTable pendingTable;
    private DefaultTableModel dashCourseModel;
    private DefaultTableModel dashStudentModel;
    private DefaultTableModel reportModel;

    // Sorter for Search functionality
    private TableRowSorter<DefaultTableModel> studentSorter;

    // Labels to update dynamically
    private JLabel lblTotalStudents;
    private JLabel lblActiveCourses;
    private JLabel lblNotifications;

    public FullAdminDashboard() {
        setTitle("University Admin Dashboard");
        setSize(1200 , 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        initHeader();
        initUI();
        loadPendingToAdmin();
        loadDashboardStats(); // Load stats immediately
        setVisible(true);
    }

    // ================= HEADER =================
    private void initHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1200 , 70));
        header.setBorder(new MatteBorder(0, 0, 1, 0, new Color(189, 195, 199)));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Left: Logo
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-20 113447.png");
        JLabel profileLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth() , getHeight());
                g2.setColor(new Color(220, 220, 220));
                g2.fillOval(0,0,size,size);
                g2.setClip(new java.awt.geom.Ellipse2D.Double(0 , 0 , size , size));
                g2.drawImage(originalIcon.getImage() , 0 , 0 , size , size , this);
                g2.dispose();
            }
        };
        profileLabel.setPreferredSize(new Dimension(45 , 45));
        header.add(profileLabel , BorderLayout.WEST);

        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setForeground(textColor);
        title.setFont(new Font("Segoe UI" , Font.BOLD , 24));
        header.add(title , BorderLayout.CENTER);

        ImageIcon logo1 = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\birukk.png");
        JLabel profiles = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                if(logo1.getImage() != null){
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
                    int size = Math.min(getWidth() , getHeight());
                    g2.setClip(new java.awt.geom.Ellipse2D.Double(0 , 0 , size , size));
                    g2.drawImage(logo1.getImage() , 0 , 0 , size , size , this);
                    g2.dispose();
                }
            }
        };
        profiles.setPreferredSize(new Dimension(40 , 40));
        profiles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        header.add(profiles , BorderLayout.EAST);

        JPopupMenu men = new JPopupMenu();
        men.setBorder(new LineBorder(new Color(220,220,220)));
        JMenuItem profile = new JMenuItem("Profile");
        JMenuItem setting = new JMenuItem("Settings");
        JMenuItem Year = new JMenuItem("Academic Year");
        styleMenuItem(profile);
        styleMenuItem(setting);
        styleMenuItem(Year);

        men.add(profile);
        men.add(setting);
        men.add(Year);

        profiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                men.show(profiles , 0 , profiles.getHeight());
            }
        });

        Year.addActionListener(e -> {
            String newYear = JOptionPane.showInputDialog(this ,
                    "Enter New Academic Year (e.g. 2018 E.C):" ,
                    "Academic Year Update" ,
                    JOptionPane.PLAIN_MESSAGE);
            if (newYear != null && !newYear.trim().isEmpty()) {
                title.setText("Admin Dashboard - " + newYear);
                JOptionPane.showMessageDialog(this , "Academic Year updated to: " + newYear);
            }
        });

        setting.addActionListener(e -> {
            JDialog settingsDialog = new JDialog(this , "System Settings" , true);
            settingsDialog.setSize(350 , 250);
            settingsDialog.setLayout(new GridLayout(4 , 1 , 10 , 10));
            settingsDialog.setLocationRelativeTo(this);
            settingsDialog.getContentPane().setBackground(Color.WHITE);

            JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            namePanel.setBackground(Color.WHITE);
            namePanel.add(new JLabel("Institution Name: "));
            JTextField nameField = new JTextField("Admin" , 15);
            namePanel.add(nameField);

            JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            themePanel.setBackground(Color.WHITE);
            themePanel.add(new JLabel("System Theme: "));
            themePanel.add(new JComboBox<>(new String[]{"Dark Blue" , "Light Gray" , "Classic"}));

            JButton saveBtn = new JButton("Save Changes");
            saveBtn.setBackground(accentBlue);
            saveBtn.setForeground(Color.WHITE);
            saveBtn.setFocusPainted(false);

            saveBtn.addActionListener(ev -> {
                title.setText("Admin Dashboard - " + nameField.getText());
                JOptionPane.showMessageDialog(settingsDialog , "Settings Saved!");
                settingsDialog.dispose();
            });

            settingsDialog.add(namePanel);
            settingsDialog.add(themePanel);
            settingsDialog.add(new JLabel("  System Version: 1.0.4"));
            settingsDialog.add(saveBtn);
            settingsDialog.setVisible(true);
        });

        add(header , BorderLayout.NORTH);
    }

    private void styleMenuItem(JMenuItem item) {
        item.setBackground(Color.WHITE);
        item.setForeground(textColor);
    }

    // ================= SIDEBAR =================
    private void initUI() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(240, 800));

        JLabel logo = new JLabel(" ADMIN PANEL");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setBorder(new EmptyBorder(30, 20, 30, 20));
        sidebar.add(logo);

        sidebar.add(createMenuButton("Dashboard", "Dash"));
        sidebar.add(createMenuButton("Courses", "Course"));
        sidebar.add(createMenuButton("Students", "Student"));
        sidebar.add(createMenuButton("Reports","Report"));

        sidebar.add(Box.createVerticalGlue());

        JButton logoutBtn = new JButton(" Logout");
        logoutBtn.setForeground(new Color(231, 76, 60));
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        styleSidebarButton(logoutBtn);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setBorder(new EmptyBorder(15, 20, 15, 20));

        logoutBtn.addActionListener(e -> {
            dispose();
            new index("Admin");
        });

        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(20));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(backgroundColor);

        cardPanel.add(createDashboard(), "Dash");
        cardPanel.add(createCoursePanel(), "Course");
        cardPanel.add(new StudentPanel(), "Student");
        cardPanel.add(createEnrollmentReport() , "Report");

        add(sidebar, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
    }

    private void styleSidebarButton(JButton b) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setBackground(sidebarColor);
        b.setForeground(Color.WHITE);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        b.setHorizontalAlignment(SwingConstants.LEFT);
    }

    private JButton createMenuButton(String text, String card) {
        JButton b = new JButton("   " + text);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setBackground(sidebarColor);
        b.setForeground(new Color(189, 195, 199));
        b.setFont(new Font("Segoe UI" , Font.PLAIN , 15));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE , 45));
        b.setBorder(new EmptyBorder(12, 25, 12, 10));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(new Color(52, 73, 94));
                b.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if(b.getBackground() != accentBlue) {
                    b.setBackground(sidebarColor);
                    b.setForeground(new Color(189, 195, 199));
                }
            }
        });

        b.addActionListener(e -> {
            Container parent = b.getParent();
            for (Component c : parent.getComponents()) {
                if (c instanceof JButton) {
                    ((JButton)c).setBackground(sidebarColor);
                    ((JButton)c).setForeground(new Color(189, 195, 199));
                }
            }
            b.setBackground(accentBlue);
            b.setForeground(Color.WHITE);
            cardLayout.show(cardPanel, card);

            if(card.equals("Dash")) {
                loadDashboardStats();
            }
        });
        return b;
    }

    // ================= DASHBOARD PANEL =================
    private JPanel createDashboard()  {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setOpaque(false);

        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(textColor);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        // STATS CARDS
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        lblTotalStudents = createStatCard("Total Students", "0", accentGreen);
        lblActiveCourses = createStatCard("Active Courses", "0", accentBlue);
        lblNotifications = createStatCard("Notifications", "0", accentRed);

        statsPanel.add((Component) lblTotalStudents.getClientProperty("card"));
        statsPanel.add((Component) lblActiveCourses.getClientProperty("card"));
        statsPanel.add((Component) lblNotifications.getClientProperty("card"));

        // SEARCH & ACTION BAR
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        actionPanel.setOpaque(false);
        actionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JTextField searchField = new JTextField("Search student..." , 20);
        styleTextField(searchField);

        JButton btnSearch = new JButton("Search");
        styleButton(btnSearch, accentBlue);

        JButton approve = new JButton("Approve Registration");
        styleButton(approve, accentGreen);

        actionPanel.add(searchField);
        actionPanel.add(btnSearch);
        actionPanel.add(approve);

        // Search Logic
        btnSearch.addActionListener(e -> {
            String text = searchField.getText();
            if (text.trim().length() == 0) {
                studentSorter.setRowFilter(null);
            } else {
                studentSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search student...")) searchField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) searchField.setText("Search student...");
            }
        });

        approve.addActionListener(e -> {
            int row = pendingTable.getSelectedRow();
            if (row != -1) {
                String refNo = pendingTable.getValueAt(row, 2).toString();
                try (Connection con = DBConnection.getConnection()) {
                    String sql = "UPDATE pending_registrations SET status = 'Approved' WHERE reference_no = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, refNo);
                    int res = pst.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "Registration Approved!");
                        loadPendingToAdmin();
                        loadDashboardStats();
                    }
                } catch (Exception ex) { ex.printStackTrace(); }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a student!");
            }
        });

        topContainer.add(title);
        topContainer.add(Box.createVerticalStrut(20));
        topContainer.add(statsPanel);
        topContainer.add(Box.createVerticalStrut(25));
        topContainer.add(actionPanel);

        // TABLES
        dashCourseModel = new DefaultTableModel(new String[]{"ID", "Course", "Duration", "Price"}, 0);
        JTable courseTable = new JTable(dashCourseModel);
        styleTable(courseTable);
        JScrollPane courseScroll = new JScrollPane(courseTable);
        courseScroll.setBorder(BorderFactory.createEmptyBorder());
        courseScroll.getViewport().setBackground(Color.WHITE);

        JPanel courseWrapper = wrapInCard("Recent Courses", courseScroll);

        dashStudentModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Department"}, 0);
        JTable studentTable = new JTable(dashStudentModel);
        styleTable(studentTable);

        studentSorter = new TableRowSorter<>(dashStudentModel);
        studentTable.setRowSorter(studentSorter);

        JScrollPane studentScroll = new JScrollPane(studentTable);
        studentScroll.setBorder(BorderFactory.createEmptyBorder());
        studentScroll.getViewport().setBackground(Color.WHITE);

        JPanel studentWrapper = wrapInCard("Registered Students", studentScroll);

        pending = new DefaultTableModel(new String[]{"Student Id", "Course", "Ref_No", "Amont","Status"},0);
        pendingTable = new JTable(pending);
        styleTable(pendingTable);
        JScrollPane pendingScroll = new JScrollPane(pendingTable);
        pendingScroll.setBorder(BorderFactory.createEmptyBorder());
        pendingScroll.getViewport().setBackground(Color.WHITE);

        JPanel pendingWrapper = wrapInCard("Pending Registrations", pendingScroll);

        // GAP IMPLEMENTATION
        JPanel gapCourse = new JPanel(new BorderLayout());
        gapCourse.setOpaque(false);
        gapCourse.setBorder(new EmptyBorder(0, 0, 20, 0));
        gapCourse.add(courseWrapper, BorderLayout.CENTER);

        JPanel gapStudent = new JPanel(new BorderLayout());
        gapStudent.setOpaque(false);
        gapStudent.setBorder(new EmptyBorder(0, 0, 20, 0));
        gapStudent.add(studentWrapper, BorderLayout.CENTER);

        JPanel gapPending = new JPanel(new BorderLayout());
        gapPending.setOpaque(false);
        gapPending.add(pendingWrapper, BorderLayout.CENTER);

        JSplitPane bottomSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, gapStudent, gapPending);
        bottomSplit.setDividerLocation(250);
        bottomSplit.setBorder(null);
        bottomSplit.setBackground(backgroundColor);

        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, gapCourse, bottomSplit);
        mainSplit.setDividerLocation(280);
        mainSplit.setBorder(null);
        mainSplit.setBackground(backgroundColor);

        panel.add(topContainer, BorderLayout.NORTH);
        panel.add(mainSplit, BorderLayout.CENTER);

        loadPendingToAdmin();
        loadCoursesDash();
        loadStudentsDash();
        return panel;
    }

    // ================= HELPER METHODS =================

    private JPanel wrapInCard(String title, Component content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(0, 5, 10, 5));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JLabel createStatCard(String titleText, String countText, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(220, 100));
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220), 1, true),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(new Color(90, 90, 90));

        JLabel count = new JLabel(countText);
        count.setFont(new Font("Segoe UI", Font.BOLD, 28));
        count.setForeground(color);

        card.add(title, BorderLayout.NORTH);
        card.add(count, BorderLayout.CENTER);

        // IMPORTANT: put card inside parent panel
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card, BorderLayout.CENTER);

        // hack: save wrapper inside label parent
        count.putClientProperty("card", wrapper);

        return count;
    }

    private void loadDashboardStats() {
        new Thread(() -> {
            try {
                Connection con = DBConnection.getConnection();
                Statement stStu = con.createStatement();
                ResultSet rsStu = stStu.executeQuery("SELECT COUNT(*) FROM students");
                if(rsStu.next()) lblTotalStudents.setText(rsStu.getString(1));

                Statement stCourse = con.createStatement();
                ResultSet rsCourse = stCourse.executeQuery("SELECT COUNT(*) FROM courses");
                if(rsCourse.next()) lblActiveCourses.setText(rsCourse.getString(1));

                Statement stPend = con.createStatement();
                ResultSet rsPend = stPend.executeQuery("SELECT COUNT(*) FROM pending_registrations WHERE status ILIKE 'Pending'");
                if(rsPend.next()) lblNotifications.setText(rsPend.getString(1) + " New");

                con.close();
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(235, 245, 255));
        table.setSelectionForeground(textColor);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(245, 247, 250));
        header.setForeground(textColor);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 42));

        btn.setBorder(new CompoundBorder(
                new LineBorder(bg.darker(), 1, true),
                new EmptyBorder(10, 20, 10, 20)
        ));
    }

    private void styleTextField(JTextField field) {
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220)),
                new EmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
    }

    // ================= COURSES PANEL (FIXED ALIGNMENT & VISIBILITY) =================
    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        courseModel = new DefaultTableModel(new String[]{"ID", "Course", "Duration", "Price"}, 0);
        JTable table = new JTable(courseModel);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);

        // --- LEFT PANEL (FORM) ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(cardColor);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // FIX 1: Explicitly set width to prevent collapsing, height is flexible
        leftPanel.setPreferredSize(new Dimension(320, 500));

        JLabel formTitle = new JLabel("Manage Courses");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Form Inputs
        JPanel formInputs = new JPanel();
        formInputs.setLayout(new BoxLayout(formInputs, BoxLayout.Y_AXIS));
        formInputs.setOpaque(false);

        JTextField id = smallField();
        JTextField name = smallField();
        JTextField duration = smallField();
        JTextField price = smallField();

        formInputs.add(label("Course ID")); formInputs.add(id);
        formInputs.add(Box.createVerticalStrut(10));
        formInputs.add(label("Course Name")); formInputs.add(name);
        formInputs.add(Box.createVerticalStrut(10));
        formInputs.add(label("Duration")); formInputs.add(duration);
        formInputs.add(Box.createVerticalStrut(10));
        formInputs.add(label("Price")); formInputs.add(price);
        formInputs.add(Box.createVerticalStrut(20));

        // --- BUTTONS (FIXED ALIGNMENT) ---
        // FIX 2: Using GridLayout(2,2) for perfect alignment
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        btnPanel.setOpaque(false);

        JButton add = new JButton("Add Course");
        styleButton(add, accentGreen);
        JButton edit = new JButton("Edit Course");
        styleButton(edit, accentBlue);
        JButton del = new JButton("Delete");
        styleButton(del, accentRed);
        JButton pdf = new JButton("Download PDF");
        styleButton(pdf, new Color(44, 62, 80));

        btnPanel.add(add);
        btnPanel.add(edit);
        btnPanel.add(del);
        btnPanel.add(pdf);

        // Assemble Left Panel
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setOpaque(false);
        formWrapper.add(formInputs, BorderLayout.CENTER);
        formWrapper.add(btnPanel, BorderLayout.SOUTH);

        leftPanel.add(formTitle, BorderLayout.NORTH);
        leftPanel.add(formWrapper, BorderLayout.CENTER);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);

        loadCourses();

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r != -1) {
                id.setText(courseModel.getValueAt(r, 0).toString());
                name.setText(courseModel.getValueAt(r, 1).toString());
                duration.setText(courseModel.getValueAt(r, 2).toString());
                price.setText(courseModel.getValueAt(r, 3).toString());
            }
        });

        add.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO courses(id, name, duration, price) VALUES (?, ?, ?, ?)"
                );
                ps.setString(1, id.getText().trim());
                ps.setString(2, name.getText().trim());
                ps.setString(3, duration.getText().trim());
                ps.setString(4, price.getText().trim());
                int result=  ps.executeUpdate();
                if(result>0){
                    JOptionPane.showMessageDialog(this, "Course added successfully!");
                    id.setText(""); name.setText(""); duration.setText(""); price.setText("");
                    refreshAll();
                    ps.close(); con.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"ERROR: "+ ex.getMessage());
            }
        });

        edit.addActionListener(e -> {
            try {
                if (id.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Select a course!"); return; }
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE courses SET name=?, duration=?, price=? WHERE id=?"
                );
                ps.setString(1, name.getText().trim());
                ps.setString(2, duration.getText().trim());
                ps.setString(3, price.getText().trim());
                ps.setString(4, id.getText().trim());
                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Updated!");
                    id.setText(""); name.setText(""); duration.setText(""); price.setText("");
                    refreshAll();
                }
                ps.close(); con.close();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        del.addActionListener(e -> {
            try {
                if (id.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(null, "Select a course!"); return; }
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM courses WHERE id = ?");
                ps.setString(1, id.getText().trim());
                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Deleted!");
                    id.setText(""); name.setText(""); duration.setText(""); price.setText("");
                    refreshAll();
                }
                ps.close(); con.close();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        pdf.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "PDF feature coming soon!");
        });

        return panel;
    }

    private JTextField smallField() {
        JTextField f = new JTextField();
        f.setMaximumSize(new Dimension(250, 35));
        styleTextField(f);
        return f;
    }
    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(mutedTextColor);
        return l;
    }
    private void refreshAll() { loadCourses(); loadCoursesDash(); loadStudentsDash(); loadDashboardStats(); }

    private void loadCourses() {
        try {
            courseModel.setRowCount(0);
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM courses");
            while (rs.next()) {
                courseModel.addRow(new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("duration"), rs.getString("price")});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadCoursesDash() {
        try {
            Connection con = DBConnection.getConnection();
            dashCourseModel.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM courses");
            while (rs.next()) {
                dashCourseModel.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadStudentsDash() {
        try {
            Connection con = DBConnection.getConnection();
            dashStudentModel.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM students");
            while (rs.next()) {
                dashStudentModel.addRow(new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("gender")});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void loadPendingToAdmin(){
        pending.setRowCount(0);
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT student_id,course_name,reference_no,amount,status FROM pending_registrations WHERE status ILIKE 'pending'";
            PreparedStatement state = con.prepareStatement(sql);
            ResultSet resultSet = state.executeQuery();
            while (resultSet.next()) {
                pending.addRow(new Object[]{resultSet.getString(1) , resultSet.getString(2) , resultSet.getString(3) , resultSet.getString(4) , resultSet.getString(5)});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ================= REPORT PANEL =================
    private JPanel createEnrollmentReport() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel title = new JLabel("Enrollment Reports");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(textColor);
        title.setBorder(new EmptyBorder(0,0,20,0));

        String[] cols = {"Registration Date", "Student Name", "Course Name", "Payment Status"};
        reportModel = new DefaultTableModel(cols, 0);
        loadReports();

        JTable table = new JTable(reportModel);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);

        JButton downloadBtn = new JButton("Download PDF Report");
        styleButton(downloadBtn, accentBlue);
        downloadBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Generating PDF Report..."));

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.add(title, BorderLayout.NORTH);
        content.add(scroll, BorderLayout.CENTER);

        panel.add(content, BorderLayout.CENTER);
        panel.add(downloadBtn, BorderLayout.SOUTH);
        return panel;
    }

    private void loadReports() {
        reportModel.setRowCount(0);
        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT current_date as date, student_id, course_name, status FROM pending_registrations");
            while (rs.next()) {
                reportModel.addRow(new Object[]{
                        rs.getString("date"),
                        rs.getString("student_id"),
                        rs.getString("course_name"),
                        rs.getString("status")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        new FullAdminDashboard();
    }
}