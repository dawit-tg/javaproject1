
import javax.swing.RowFilter;
import java.util.ArrayList;
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
        setSize(1100 , 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ========================= Header =========================
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(new Color(44, 62, 80));
        topSection.setPreferredSize(new Dimension(0 , 80));
        add(topSection, BorderLayout.NORTH);
        JLabel welcomeLabel = new JLabel("Welcome to Online Course Registration System");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI" , Font.BOLD , 26));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topSection.add(welcomeLabel, BorderLayout.CENTER);

        // Logo (Left)
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
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new index().setVisible(true);
            }
        });
        // Profile (Right)
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS));
        rightSidePanel.setOpaque(false);
        rightSidePanel.setBorder(new EmptyBorder(10, 0, 0, 20));

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

        JLabel pro = new JLabel("Biruk");
        pro.setForeground(Color.WHITE);
        pro.setFont(new Font("SansSerif", Font.BOLD, 12));
        pro.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightSidePanel.add(profiles);
        rightSidePanel.add(Box.createVerticalStrut(5));
        rightSidePanel.add(pro);
        topSection.add(rightSidePanel, BorderLayout.EAST);
        // ========================= Sidebar=========================
        JPanel sideBar = new JPanel();
        sideBar.setBackground(sidebarColor);
        sideBar.setPreferredSize(new Dimension(220 , 0));
        sideBar.setLayout(new BoxLayout(sideBar , BoxLayout.Y_AXIS));
        sideBar.setBorder(BorderFactory.createEmptyBorder(40 , 15, 20 , 15));
        add(sideBar , BorderLayout.WEST);

        JLabel sideTitle = new JLabel("Course System");
        sideTitle.setForeground(Color.WHITE);
        sideTitle.setFont(new Font("Arial" , Font.BOLD , 22));
        sideTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        // ========================= MAIN PANEL =========================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        centerSection = new JPanel();
        centerSection.setBackground(backgroundColor);
        centerSection.setLayout(new BoxLayout(centerSection , BoxLayout.Y_AXIS));
        centerSection.setBorder(new EmptyBorder(30 , 40 , 30 , 40));

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(darkBlue);
        footer.setPreferredSize(new Dimension(0 , 60));
        JLabel footerLabel = new JLabel("© 2026 Online Course Registration System");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial" , Font.PLAIN , 24));
        footer.add(footerLabel);
        mainPanel.add(footer, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        // Dropdown Logic
        JPopupMenu dropdown = new JPopupMenu();
        JMenuItem contactItem = new JMenuItem("Contact Us");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem profileItem = new JMenuItem("Profiles");
        dropdown.add(contactItem);
        dropdown.add(helpItem);
        dropdown.add(profileItem);
        profiles.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                dropdown.show(profiles , 0 , profiles.getHeight());
            }
        });
        showHomeContent();
        setVisible(true);
    }
    public void showHomeContent() {
        centerSection.removeAll();
        JLabel title = new JLabel("Learn & Grow with Our Courses");
        title.setFont(new Font("Arial" , Font.BOLD , 42));
        title.setForeground(darkBlue);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel , BoxLayout.Y_AXIS));
        heroPanel.setOpaque(false);
        heroPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE , 160));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        JTextField searchField = new JTextField("Search for courses..." , 25);
        searchField.setPreferredSize(new Dimension(280 , 38));
        JButton btnSearch = new JButton("🔍");
        btnSearch.setBackground(lightBlue);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setPreferredSize(new Dimension(100 , 40));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);

        heroPanel.add(Box.createRigidArea(new Dimension(0 , 5)));
        heroPanel.add(title);
        heroPanel.add(Box.createRigidArea(new Dimension(0 , 5)));
        heroPanel.add(searchPanel);

        /// /////////////////////search bar///////////////////////////
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search for courses...")) {
                    searchField.setText("");
                }
            }
        });
//        searchField.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                String query = searchField.getText().toLowerCase().trim();
//                if (query.equals("search for courses...")) {
//                    displayCourses(allcourse);
//                    return;
//                }
//
//                List<Course> filtered = new ArrayList<>();
//                for (Course cou : allcourse) {
//                    if (cou.getTitle().toLowerCase().contains(query)) {
//                        filtered.add(cou);
//                    }
//                }
//                displayCourses(filtered);
//            }
//        });
//        private void displayCourses(List<Course> courses) {
//            cardContainer.removeAll();
//            for (Course cou : courses) {
//                JPanel card = createCourseCard(cou);
//                cardContainer.add(card);
//            }
//            cardContainer.revalidate();
//            cardContainer.repaint();
//        }

        JPanel cardContainer = new JPanel();
        centerSection.add(cardContainer, BorderLayout.CENTER);
        cardContainer.setOpaque(false);
        cardContainer.setLayout(new GridLayout(0, 4, 10, 10));

        JScrollPane scrollPane = new JScrollPane(centerSection);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);
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
        centerSection.add(heroPanel,BorderLayout.CENTER);
        centerSection.add(cardContainer,BorderLayout.CENTER);
        centerSection.revalidate();
        centerSection.repaint();
    }
    private JPanel createModernCard(String title, String imagePath, String description) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(220, 300));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(img));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel textContent = new JPanel(new GridLayout(3, 1));
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
        textContent.add(lblTitle);
        textContent.add(lblDesc);
        textContent.add(btnEnroll);
        card.add(imgLabel, BorderLayout.NORTH);
        card.add(textContent, BorderLayout.CENTER);
        return card;
    }
    public void styleButton(JButton button) {
        button.setBackground(lightBlue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI" , Font.BOLD , 15));
        button.setMaximumSize(new Dimension(180 , 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new index());
    }
}