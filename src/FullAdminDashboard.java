import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
//import StudentPanel;
public class FullAdminDashboard extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Color sidebarColor = new Color(28 , 36 , 43);
    private DefaultTableModel courseModel;
    private  DefaultTableModel pending;
    private  JTable pendingTable;
    private DefaultTableModel dashCourseModel;
    private DefaultTableModel dashStudentModel;
    public FullAdminDashboard() {
        setTitle("University Admin Dashboard");
        setSize(1100 , 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initHeader();
        initUI();
        loadPendingToAdmin();
        setVisible(true);
    }
    private void initHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(41 , 54 , 64));
        header.setPreferredSize(new Dimension(1100 , 85));
        header.setBorder(new EmptyBorder(5 , 15 , 5 , 15));
        //admine profile or logo left side logo
        ImageIcon originalIcon = new ImageIcon(
            "C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-20 113447.png");
        JLabel profileLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth() , getHeight());
                g2.setClip(new java.awt.geom.Ellipse2D.Double(0 , 0 , size , size));
                g2.drawImage(originalIcon.getImage() , 0 , 0 , size , size , this);
                g2.dispose();
            }
        };
        profileLabel.setPreferredSize(new Dimension(50 , 50));
        header.add(profileLabel , BorderLayout.WEST);
        //header name
        JLabel title = new JLabel("Welcome To Admin Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI" , Font.BOLD , 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title , BorderLayout.CENTER);

        //right side logo
        ImageIcon logo1 = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\birukk.png");
        JLabel profiles = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth() , getHeight());
                g2.setClip(new java.awt.geom.Ellipse2D.Double(0 , 0 , size , size));
                g2.drawImage(originalIcon.getImage() , 0 , 0 , size , size , this);
                g2.dispose();
            }
        };
        profiles.setPreferredSize(new Dimension(50 , 50));
        header.add(profiles , BorderLayout.EAST);
        JPopupMenu men = new JPopupMenu();
        JMenuItem profile = new JMenuItem("Profiles");
        JMenuItem setting = new JMenuItem("Setting");
        JMenuItem Year = new JMenuItem("ACadamic Year");
        men.add(profile);
        men.add(setting);
        men.add(Year);
        profiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                men.show(profiles , 0 , profiles.getHeight());
            }
        });
        //  Academic Year
        Year.addActionListener(e -> {
            String newYear = JOptionPane.showInputDialog(this ,
                "Enter New Academic Year (e.g. 2018 E.C):" ,
                "Academic Year Update" ,
                JOptionPane.QUESTION_MESSAGE);
            if (newYear != null && !newYear.trim().isEmpty()) {
                title.setText("Welcome To Admin Dashboard - " + newYear);
                JOptionPane.showMessageDialog(this , "Academic Year updated to: " + newYear);
            }
        });
        //setting item work
        setting.addActionListener(e -> {
            JDialog settingsDialog = new JDialog(this , "System Settings" , true);
            settingsDialog.setSize(350 , 250);
            settingsDialog.setLayout(new GridLayout(4 , 1 , 10 , 10));
            settingsDialog.setLocationRelativeTo(this);
            //  Name Field
            JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            namePanel.add(new JLabel("Institution Name: "));
            JTextField nameField = new JTextField("Admin" , 15);
            namePanel.add(nameField);
            // Theme Selector (Optional Example)
            JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            themePanel.add(new JLabel("System Theme: "));
            themePanel.add(new JComboBox<>(new String[]{"Dark Blue" , "Light Gray" , "Classic"}));
            // Save Button
            JButton saveBtn = new JButton("Save Changes");
            saveBtn.setBackground(sidebarColor);
            saveBtn.setForeground(Color.WHITE);
            saveBtn.addActionListener(ev -> {
                title.setText("Welcome to " + nameField.getText());
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
    // ======================================================
    private void initUI() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(220, 700));
        JLabel logo = new JLabel(" ADMIN PANEL");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setBorder(new EmptyBorder(25, 10, 25, 10));
        sidebar.add(logo);
        sidebar.add(createMenuButton("Dashboard", "Dash"));
        sidebar.add(createMenuButton("Courses", "Course"));
        sidebar.add(createMenuButton("Students", "Student"));
        sidebar.add(createMenuButton("Reports","Report"));


        JButton logoutBtn = new JButton("\uD83D\uDD34 Logout");
        Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 14);
        logoutBtn.setFont(emojiFont);
        logoutBtn.setFont(emojiFont);
        styleSidebarButton(logoutBtn);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            dispose();
            new index("Admin");
        });
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalGlue());
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
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
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
    }
    private JPanel createDashboard()  {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(245, 246, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField searchField = new JTextField("Search for student by name,id..." , 25);
        searchField.setPreferredSize(new Dimension(280 , 38));
        JButton btnSearch = new JButton("🔍");
        btnSearch.setBackground(Color.BLUE);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setPreferredSize(new Dimension(100 , 40));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search for student by name,id...")) {
                    searchField.setText("");
                }
                     }
        });
        JButton approve=new JButton("Approve Registration");
        approve.setBackground(Color.GREEN);
        approve.setForeground(Color.WHITE);
        approve.setPreferredSize(new Dimension(100 , 40));
        searchPanel.add(approve);

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
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a student from the pending table!");
            }
        });

        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top,BoxLayout.Y_AXIS));

        JPanel statsPanel=new JPanel(new GridLayout(1, 3, 25, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.add(createCard("Total Students", "1200", new Color(46, 204, 113)));
        statsPanel.add(createCard("Active Courses", "12", new Color(52, 152, 219)));
        statsPanel.add(createCard("Notifications", "6 New", new Color(231, 76, 60)));
        top.add(title);
        top.add(Box.createVerticalStrut(20));
        top.add(statsPanel);
        top.add(Box.createVerticalStrut(20));
        top.add(searchPanel);
        top.add(Box.createVerticalStrut(20));
        // ================= COURSE TABLE =================
        dashCourseModel = new DefaultTableModel(new String[]{"ID", "Course", "Duration", "Price"}, 0);
        JTable courseTable = new JTable(dashCourseModel);
        JScrollPane courseScroll = new JScrollPane(courseTable);
        courseScroll.setPreferredSize(new Dimension(1000, 250));
        // ================= STUDENT TABLE =================
        dashStudentModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Department"}, 0);
        JTable studentTable = new JTable(dashStudentModel);
        JScrollPane studentScroll = new JScrollPane(studentTable);
        studentScroll.setPreferredSize(new Dimension(1000, 250));
///  ////////////////////pending price table///////////////////////
       pending = new DefaultTableModel(new String[]{"Student Id", "Course", "Ref_No", "Amont","Status"},0);
         pendingTable = new JTable(pending);
        JScrollPane pendingScroll = new JScrollPane(pendingTable);
        pendingScroll.setBorder(null);
        // ================= CENTER SPLIT =================
//        JSplitPane bottomSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, studentScroll, pendingScroll);
//        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, courseScroll, bottomSplit);
//        mainSplit.setDividerLocation(250);
//        mainSplit.setContinuousLayout(true);
//        panel.add(mainSplit, BorderLayout.CENTER);
//        bottomSplit.setDividerLocation(150);
//        bottomSplit.setOneTouchExpandable(true);
//        bottomSplit.setResizeWeight(0.5);
        panel.add(top, BorderLayout.NORTH);
//        panel.add(bottomSplit, BorderLayout.CENTER);
        JSplitPane bottomSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, studentScroll, pendingScroll);
        bottomSplit.setDividerLocation(200);
        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, courseScroll, bottomSplit);
        mainSplit.setDividerLocation(200);
        mainSplit.setContinuousLayout(true);
        panel.add(mainSplit, BorderLayout.CENTER);
        // LOAD DATA
        loadPendingToAdmin();
        loadCoursesDash();
        loadStudentsDash();
        return panel;
    }
    private JPanel createCard(String title, String count, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBorder(BorderFactory.createLineBorder(color, 2));
        card.setBackground(Color.WHITE);
        JLabel ir = new JLabel(title, SwingConstants.CENTER);
        ir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel lblCount = new JLabel(count, SwingConstants.CENTER);
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblCount.setForeground(color);
        card.add(ir);
        card.add(lblCount);
        return card;
    }
    public void loadPendingToAdmin(){
        pending.setRowCount(0);
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT student_id,course_name,reference_no,amount,status FROM pending_registrations WHERE status ILIKE 'pending'";
            PreparedStatement state = con.prepareStatement(sql);
            ResultSet resultSet = state.executeQuery();
            while (resultSet.next()) {
                pending.addRow(new Object[]{
                    resultSet.getString(1) ,
                    resultSet.getString(2) ,
                    resultSet.getString(3) ,
                    resultSet.getString(4) ,
                    resultSet.getString(5)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // =====================================================
    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 246, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        courseModel = new DefaultTableModel(new String[]{"ID", "Course", "Duration", "Price"}, 0);
        JTable table = new JTable(courseModel);
        JScrollPane scroll = new JScrollPane(table);
        /// ////pending table
        DefaultTableModel pending = new DefaultTableModel(new String[]{"Student Id", "Course", "Ref_No", "Amont","Status"},0);
        JTable pendingTable = new JTable(dashStudentModel);
        JScrollPane pendingScroll = new JScrollPane(pendingTable);
        // FORM
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder("Course Form"));
        JTextField id = smallField();
        JTextField name = smallField();
        JTextField duration = smallField();
        JTextField price = smallField();

        form.add(label("ID")); form.add(id);
        form.add(label("Name")); form.add(name);
        form.add(label("Duration")); form.add(duration);
        form.add(label("Price")); form.add(price);

        JButton add = new JButton("Add");
        add.setBackground(new Color(39, 174, 96));
        add.setForeground(Color.WHITE);
        add.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add.setFocusPainted(false);
        add.setBorderPainted(false);
        add.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton edit = new JButton("Edit");
        edit.setBackground(new Color(41, 128, 185));
        edit.setForeground(Color.WHITE);
        edit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        edit.setFocusPainted(false);
        edit.setBorderPainted(false);
        edit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton del = new JButton("Delete");
        del.setBackground(new Color(231, 76, 60));
        del.setForeground(Color.WHITE);
        del.setFont(new Font("Segoe UI", Font.BOLD, 14));
        del.setFocusPainted(false);
        del.setBorderPainted(false);
        del.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(add);
        btnPanel.add(edit);
        btnPanel.add(del);
        form.add(btnPanel);
        form.setPreferredSize(new Dimension(220, 300));
        panel.add(form, BorderLayout.NORTH);
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
        // ADD course
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
                    id.setText("");
                    name.setText("");
                    duration.setText("");
                    price.setText("");
                refreshAll();
                ps.close();
                con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"ERROR"+ ex.getMessage()!= null ? ex.getMessage() : ex.toString());
            }
        });
        // EDIT course
        edit.addActionListener(e -> {
            try {
                if (id.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select a course first!");
                    return;
                }
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
                    JOptionPane.showMessageDialog(null, "Course updated successfully!");
                    id.setText("");
                    name.setText("");
                    duration.setText("");
                    price.setText("");
                    refreshAll();
                } else {
                    JOptionPane.showMessageDialog(null, "Course not found!");
                }
                ps.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        // DELETE course
        del.addActionListener(e -> {
            try {
                if (id.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a course first!");
                    return;
                }
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM courses WHERE id = ?"
                );
                ps.setString(1, id.getText().trim());
                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "are you sure delete this course ?");
                    id.setText("");
                    name.setText("");
                    duration.setText("");
                    price.setText("");
                    refreshAll();
                } else {
                    JOptionPane.showMessageDialog(null, "Course not found!");
                }
                ps.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        return panel;
    }
    // ======================================================
    public void loadCourses() {
        try {
            courseModel.setRowCount(0); // VERY IMPORTANT
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM courses");

            while (rs.next()) {
                courseModel.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("duration"),
                        rs.getString("price")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ======================================================
    private JTextField smallField() {
        JTextField f = new JTextField();
        f.setMaximumSize(new Dimension(180, 25));
        f.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return f;
    }
    private JLabel label(String t) {
        return new JLabel(t);
    }
    private void refreshAll() {
        loadCourses();
        loadCoursesDash();
        loadStudentsDash();
    }
    private void loadCoursesDash() {
        try {
            Connection con = DBConnection.getConnection();
            dashCourseModel.setRowCount(0);
            ResultSet rs = con.createStatement()
                    .executeQuery("SELECT * FROM courses");

            while (rs.next()) {
                dashCourseModel.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadStudentsDash() {
        try {
            Connection con = DBConnection.getConnection();
            dashStudentModel.setRowCount(0);
            ResultSet rs = con.createStatement()
                    .executeQuery("SELECT * FROM students");
            while (rs.next()) {
                dashStudentModel.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("gender")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private JButton createMenuButton(String text, String card) {

        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setBackground(sidebarColor);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI" , Font.PLAIN , 15));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE , 45));
        b.setBorder(new EmptyBorder(10 , 20 , 10 , 10));
        // HOVER EFFECT
        Color normal = sidebarColor;
        Color hover = new Color(45 , 58 , 68);
        Color active = new Color(70 , 90 , 110);
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (b.getBackground() != active)
                    b.setBackground(hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (b.getBackground() != active)
                    b.setBackground(normal);
            }
        });
        // CLICK ACTION (keeps your logic)
        b.addActionListener(e -> {
            // reset all buttons in sidebar
            Container parent = b.getParent();
            for (Component c : parent.getComponents()) {
                if (c instanceof JButton) {
                    c.setBackground(normal);
                }
            }
            // set active color
            b.setBackground(active);
            cardLayout.show(cardPanel, card);
        });
        return b;
    }
    private JPanel createEnrollmentReport() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Enrollment Reports");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.CENTER);

        String[] cols = {"Registration Date", "Student Name", "Course Name", "Payment Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"2026-04-10", "Biruk T.", "Java Programming", "Paid"});
        model.addRow(new Object[]{"2026-04-12", "Kidist A.", "React JS", "Pending"});
        model.addRow(new Object[]{"2026-04-15", "Abebe C.", "Java Programming", "Paid"});

        JTable table = new JTable(model);
        table.setRowHeight(30);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton downloadBtn = new JButton("Download PDF Report");
        downloadBtn.setBackground(new Color(52, 152, 219));
        downloadBtn.setForeground(Color.WHITE);
        downloadBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "generate PDF Report... Please Wait.");
        });
        panel.add(downloadBtn, BorderLayout.SOUTH);
        return panel;
    }
    public static void main(String[] args) {
        new FullAdminDashboard();
    }
}