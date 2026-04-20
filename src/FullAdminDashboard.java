import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;
public class FullAdminDashboard extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel headerpanel;
    private Color sidebarColor = new Color(44 , 62 , 80);

    public FullAdminDashboard() {
        setTitle("University Admin");
        setSize(1100 , 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        headerpanel = new JPanel(new BorderLayout());
        headerpanel.setBackground(new Color(44 , 62 , 80));
        headerpanel.setPreferredSize(new Dimension(0 , 70));
        //admine profile or logo left side logo

        ImageIcon originalIcon = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-20 113447.png");
///  include circle
        JLabel profileLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth(), getHeight());
                g2.setClip(new java.awt.geom.Ellipse2D.Double(0, 0, size, size));
                g2.drawImage(originalIcon.getImage(), 0, 0, size, size, this);
                g2.dispose();
            }
        };
        profileLabel.setPreferredSize(new Dimension(50, 50));
        headerpanel.add(profileLabel, BorderLayout.WEST);

        //right side logo
        ImageIcon logo1 = new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2026-04-20 113447.png");
///  include circle
        JLabel profiles = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth(), getHeight());
                g2.setClip(new java.awt.geom.Ellipse2D.Double(0, 0, size, size));
                g2.drawImage(originalIcon.getImage(), 0, 0, size, size, this);
                g2.dispose();
            }
        };
        profiles.setPreferredSize(new Dimension(50, 50));
        headerpanel.add(profiles, BorderLayout.EAST);
        //header name
        JLabel title = new JLabel("Welcome To Admin Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI" , Font.BOLD , 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        headerpanel.add(title , BorderLayout.CENTER);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar , BoxLayout.Y_AXIS));
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(230 , 0));
        sidebar.setBorder(new EmptyBorder(20 , 10 , 20 , 10));

        JLabel lo = new JLabel("  ADMIN ");
        lo.setForeground(Color.WHITE);
        lo.setFont(new Font("Arial" , Font.BOLD , 18));
        lo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lo.setBorder(new EmptyBorder(10 , 0 , 30 , 0));
        sidebar.add(lo);

        sidebar.add(createMenuButton("Dashboard" , "Dash"));
        sidebar.add(Box.createRigidArea(new Dimension(0 , 10)));
        sidebar.add(createMenuButton("Manage Courses" , "Course"));
        sidebar.add(Box.createRigidArea(new Dimension(0 , 10)));
        sidebar.add(createMenuButton("Manage Students" , "Student"));
        sidebar.add(Box.createRigidArea(new Dimension(0 , 10)));
        sidebar.add(createMenuButton("Enrollment Reports" , "Report"));

        sidebar.add(Box.createVerticalGlue());
        /// =========================///
        /// ====Logout action====
        /// /========================///
        JButton logout = createMenuButton("Logout" , "Logout");
        logout.addActionListener(e ->{
            int confirm = JOptionPane.showConfirmDialog(null ,
                "Are you sure you want to logout?" , "Logout Confirmation" ,
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginPage().setVisible(true);
            }
        });
        sidebar.add(logout);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.add(createDashboardHome() , "Dash");
        cardPanel.add(createCourseManagement() , "Course");
        cardPanel.add(createStudentManagement() , "Student");
        cardPanel.add(createEnrollmentReport() , "Report");
        add(headerpanel , BorderLayout.NORTH);
        add(sidebar , BorderLayout.WEST);
        add(cardPanel , BorderLayout.CENTER);
    }
    private JPanel createDashboardHome() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        panel.setBackground(new Color(245, 246, 250));

        JLabel title = new JLabel("System Overview");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.CENTER);
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.add(createStatBox("Total Students", "1,240", new Color(46, 204, 113)));
        statsPanel.add(createStatBox("Active Courses", "12", new Color(155, 89, 182)));
        statsPanel.add(createStatBox("New Requests", "8", new Color(241, 196, 15)));
        panel.add(statsPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCourseManagement() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        String[] cols = {"ID", "Course Name", "Duration", "Price"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"C1", "Java Programming", "3 Months", "2500 ETB"});
        model.addRow(new Object[]{"C2", "React JS", "2 Months", "3000 ETB"});
        model.addRow(new Object[]{"C3", "C++ Programming", "5 Months", "2500 ETB"});
        model.addRow(new Object[]{"C5", "Nest JS", "2 Months", "3000 ETB"});
        model.addRow(new Object[]{"C6", "Phytone Programming", "3 Months", "4500 ETB"});
        model.addRow(new Object[]{"C7", "Web Design", "2 Months", "5000 ETB"});

        JTable table = new JTable(model);
        table.setRowHeight(30);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addBtn = new JButton("Add New Course");
        addBtn.setBackground(new Color(39, 174, 96));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton edit = new JButton("Edit Selected");
        edit.setBackground(new Color(41, 128, 185));
        edit.setForeground(Color.WHITE);
        edit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        edit.setFocusPainted(false);
        edit.setBorderPainted(false);
        edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
      JButton delete = new JButton("Delete Course");
        delete.setBackground(new Color(231, 76, 60));
        delete.setForeground(Color.WHITE);
        delete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        delete.setFocusPainted(false);
        delete.setBorderPainted(false);
        delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPanel.add(edit);
        btnPanel.add(addBtn);
        btnPanel.add(delete);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }
    private JPanel createStudentManagement() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.CENTER);
        String[] cols = {"Student ID", "Full Name", "Email", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"S001", "Biruk T.", "biruk@email.com", "Active"});
        model.addRow(new Object[]{"S002", "Kidist A.", "kidu@email.com", "Active"});
        model.addRow(new Object[]{"S003", "Abebe C.", "abebe@email.com", "Blocked"});

        JTable table = new JTable(model);
        table.setRowHeight(30);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton blockBtn = new JButton("Toggle Block Status");
        blockBtn.setBackground(new Color(231, 76, 60));
        blockBtn.setForeground(Color.WHITE);

        blockBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1) {
                String currentStatus = model.getValueAt(row, 3).toString();
                String newStatus = currentStatus.equals("Active") ? "Blocked" : "Active";
                model.setValueAt(newStatus, row, 3);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student first!");
            }
        });
        actionPanel.add(blockBtn);
        panel.add(actionPanel, BorderLayout.SOUTH);
        return panel;
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
            JOptionPane.showMessageDialog(this, "Generating PDF Report... Please Wait.");
        });

        panel.add(downloadBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JButton createMenuButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(210, 45));
        btn.setBackground(sidebarColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        return btn;
    }
    private JPanel createStatBox(String title, String val, Color c) {
        JPanel box = new JPanel(new GridLayout(2, 1));
        box.setBackground(c);
        box.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel t = new JLabel(title); t.setForeground(Color.WHITE);
        JLabel v = new JLabel(val); v.setFont(new Font("Arial", Font.BOLD, 35)); v.setForeground(Color.WHITE);
        box.add(t); box.add(v);
        return box;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FullAdminDashboard frame = new FullAdminDashboard();
            frame.setVisible(true);
        });
    }
}