import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class FullAdminDashboard extends JFrame {
    private JPanel cardPanel; // ገጾቹን ለመቀያየር
    private CardLayout cardLayout;
    JPanel headerpanel;
    private Color sidebarColor = new Color(44, 62, 80);
    private Color activeColor = new Color(52, 152, 219);

    public FullAdminDashboard() {
        setTitle("University Admin");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        /// header
        headerpanel = new JPanel(new BorderLayout());
        headerpanel.setPreferredSize(new Dimension(1000, 80));
        JLabel title = new JLabel("Welcome to Admin Dashboard ");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerpanel.add(title, BorderLayout.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // --- 1. Sidebar (ሜኑ) ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(220, 700));

        JLabel logo = new JLabel("Admin");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setBorder(new EmptyBorder(30, 20, 30, 20));
        sidebar.add(logo);

        sidebar.add(createMenuButton("Dashboard", "Dash"));
        sidebar.add(createMenuButton("Manage Courses", "Course"));
        sidebar.add(createMenuButton("Manage Students", "Student"));
        sidebar.add(createMenuButton("Enrollment Reports", "Report"));
        sidebar.add(Box.createVerticalGlue()); // ለትርፍ ቦታ
        sidebar.add(createMenuButton("Logout", "Logout"));
        // --- 2. Main Content Area (CardLayout) ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createDashboardHome(), "Dash");
        cardPanel.add(createCourseManagement(), "Course");
        cardPanel.add(createStudentManagement(), "Student");
        cardPanel.add(createEnrollmentReport(), "Report");

        // --- 3. Layout Assembly ---
        add(sidebar, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // --- ገጽ 1: Dashboard Home (Stats) ---
    private JPanel createDashboardHome() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("System Overview");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.add(createStatBox("Total Students", "1,240", new Color(46, 204, 113)));
        statsPanel.add(createStatBox("Active Courses", "12", new Color(155, 89, 182)));
        statsPanel.add(createStatBox("New Requests", "8", new Color(241, 196, 15)));

        panel.add(statsPanel, BorderLayout.CENTER);
        return panel;
    }

    // --- ገጽ 2: Course Management ---
    private JPanel createCourseManagement() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
       // panel.add(new JLabel("Course Management", new JLabel().getFont().deriveFont(20f)), BorderLayout.NORTH);

        String[] cols = {"ID", "Course Name", "Duration", "Price"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"C1", "Java Programming", "3 Months", "2500 ETB"});
        model.addRow(new Object[]{"C2", "React JS", "2 Months", "3000 ETB"});

        panel.add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.add(new JButton("Add New Course"));
        btnPanel.add(new JButton("Edit Selected"));
        btnPanel.add(new JButton("Delete Course"));
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- ገጽ 3: Student Management ---
    private JPanel createStudentManagement() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] cols = {"Student ID", "Full Name", "Email", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"S001", "Biruk T.", "biruk@email.com", "Active"});
        model.addRow(new Object[]{"S002", "Kidist A.", "kidu@email.com", "Pending"});

        panel.add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);

        JButton blockBtn = new JButton("Block / Unblock Student");
        blockBtn.setBackground(new Color(231, 76, 60));
        blockBtn.setForeground(Color.WHITE);
        panel.add(blockBtn, BorderLayout.SOUTH);

        return panel;
    }

    // --- ገጽ 4: Enrollment Reports ---
    private JPanel createEnrollmentReport() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] cols = {"Date", "Student", "Course", "Payment"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"2026-04-01", "Abebe B.", "Java", "Paid"});
        model.addRow(new Object[]{"2026-04-02", "Sara M.", "Web Dev", "Pending"});
        panel.add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
        panel.add(new JButton("Download Report (PDF)"), BorderLayout.SOUTH);
        return panel;
    }
    // --- Helper Methods (ዲዛይን ለማቅለል) ---
    private JButton createMenuButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 45));
        btn.setBackground(sidebarColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        return btn;
    }

    private JPanel createStatBox(String title, String val, Color c) {
        JPanel box = new JPanel(new GridLayout(2, 1));
        box.setBackground(c);
        box.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel t = new JLabel(title); t.setForeground(Color.WHITE);
        JLabel v = new JLabel(val); v.setFont(new Font("Arial", Font.BOLD, 30)); v.setForeground(Color.WHITE);
        box.add(t); box.add(v);
        return box;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FullAdminDashboard());
        new FullAdminDashboard().setVisible(true);
    }
}