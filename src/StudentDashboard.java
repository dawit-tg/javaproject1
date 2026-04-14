import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
class StudentDashboard extends JFrame {
    private  String studentId;
    JPanel sidebar, headerpanel, mainContent;
    CardLayout cardLayout;
    public StudentDashboard(String user) {
        ImageIcon mm= new ImageIcon("D:\\istockphoto-1757344400-1024x1024.jpg");
        setIconImage(mm.getImage());
       this.studentId=user;
        setTitle("Student Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        headerpanel = new JPanel(new BorderLayout());
        headerpanel.setBackground(new Color(44, 62, 80));
        headerpanel.setPreferredSize(new Dimension(1000, 80));

        JLabel title = new JLabel("Welcome to Student Dashboard "+ studentId);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerpanel.add(title, BorderLayout.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
       // title.setBorder(BorderFactory.createTitledBorder(BorderLayout.CENTER));

        // Profile Button
        JButton profileBtn = imageIcon();
        headerpanel.add(profileBtn, BorderLayout.EAST);
        add(headerpanel, BorderLayout.NORTH);
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(200, 700));

        String[] navItems = {"Dashboard","Course Catalog", "My Courses", "Settings"};
        for (String item : navItems) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(200, 50));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(52, 73, 94));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);

            btn.addActionListener(e -> cardLayout.show(mainContent, item));
            sidebar.add(btn);
        }
        add(sidebar, BorderLayout.WEST);
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.add(createDashboardPanel(), "Dashboard");
        mainContent.add(createCourseCatalogPanel(), "Course Catalog");
        mainContent.add(createMyCoursesPanel(), "My Courses");
        mainContent.add(new JLabel("contact us:zelebiruke@gmail.com") ,"ContactUs");
        mainContent.add(createHelpPanel(), "Help");
        mainContent.add(new JLabel("Account Settings", JLabel.CENTER), "Settings");
        add(mainContent, BorderLayout.CENTER);
    }
    //circle style
    private ImageIcon getEasyCircularImage(String path) {
        ImageIcon icon = new ImageIcon("C:\\Users\\HP\\Pictures\\Biruk.jpg");
        Image img = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setSize(35, 35);
        return (ImageIcon) label.getIcon();
    }
    private JButton imageIcon() {
        JButton btn = new JButton("Biruk ▼");
        btn.setIcon(new ImageIcon(new ImageIcon( "C:\\Users\\HP\\Pictures\\Biruk.jpg").getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBackground(Color.gray);
        btn.setForeground(Color.WHITE);
        // Dropdown Menu
        JPopupMenu menu = new JPopupMenu();
        menu.add(new JMenuItem("Account"));
        menu.add(new JMenuItem("Courses"));
        menu.add(new JMenuItem("Help") );
        menu.add(new JMenuItem("Contact us"));
        menu.addSeparator();
        menu.add(new JMenuItem("Logout"));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        btn.addActionListener(e -> menu.show(btn, 0, btn.getHeight()));
        return btn;
    }
    // course list
    private JPanel createCourseCatalogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel lbl = new JLabel("(Available Courses)");
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lbl, BorderLayout.CENTER);
        //create table
        String[] columns = {"Course ID", "Course Name", "Duration", "Price"};
        Object[][] data = {
            {"CS101", "Java Programming", "3 Months", "2000 ETB"},
            {"DB202", "Database Systems", "2 Months", "1500 ETB"},
            {"WEB303", "React JS", "2.5 Months", "2500 ETB"}
        };
        JTable table = new JTable(data, columns);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JButton regBtn = new JButton("now register (Register)");
        regBtn.setBackground(new Color(46, 204, 113));
        regBtn.setForeground(Color.WHITE);
        panel.add(regBtn, BorderLayout.SOUTH);
        table.setRowHeight(40);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(50, 50, 50));
        //table.setEditingColumn(false);
        return panel;
    }
    // login registerd couse
    private JPanel createMyCoursesPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(""));
        return panel;
    }
    private JScrollPane createHelpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);
        // Header
        JLabel title = new JLabel("Help center");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        String[][] faqs = {
            {"how to course register?", "Course Catalog page choose interest።"},
            {"can you change password?", "yes፣ Settings page change ።"},
            {"dont'see register course?", "please Logout try login or Admin contact።"}
        };
        for (String[] faq : faqs) {
            JLabel question = new JLabel("Quastion፡ " + faq[0]);
            question.setFont(new Font("Arial", Font.BOLD, 14));
            JLabel answer = new JLabel("Response፡ " + faq[1]);
            answer.setFont(new Font("Arial", Font.PLAIN, 13));
            answer.setForeground(Color.GRAY);

            panel.add(question);
            panel.add(answer);
            panel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        return new JScrollPane(panel);
    }
    private JPanel createDashboardPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
            panel.setBackground(Color.WHITE);

            // ምሳሌ፡ የኮርሶች ብዛት ማሳያ ካርድ
            panel.add(createStatCard("Total Courses", "12", new Color(52, 152, 219)));
            panel.add(createStatCard("My Courses", "3", new Color(46, 204, 113)));
            panel.add(createStatCard("New Messages", "5", new Color(231, 76, 60)));
        panel.add(createStatCard("Pending Registrations", "10", new Color(231, 76, 60)));
        panel.add(createStatCard("List", "5", new Color(220, 76, 30)));
        panel.add(createStatCard("Complete certeficate", "5", new Color(231, 166, 80)));
            return panel;
        }
// ካርዶችን ለመስራት የሚያገለግል ትንሽ Method
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard("Biruk").setVisible(true));
      //  new StudentDashboard("user").setVisible(true);
        //new StudentDashboard("admin").setVisible(true);
    }
}
