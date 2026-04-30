import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
//import StudentPanel;

public class FullAdminDashboard extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private Color sidebarColor = new Color(28, 36, 43);

    private DefaultTableModel courseModel;
    private DefaultTableModel dashCourseModel;
    private DefaultTableModel dashStudentModel;

    public FullAdminDashboard() {

        setTitle("University Admin Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initUI();
        setVisible(true);
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

        JButton logoutBtn = new JButton("Logout");
        styleSidebarButton(logoutBtn);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            dispose();
            new index();
        });

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalGlue());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createDashboard(), "Dash");
        cardPanel.add(createCoursePanel(), "Course");
        cardPanel.add(new StudentPanel(), "Student");


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
        b.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
    }

    private JPanel createDashboard() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 246, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ================= COURSE TABLE =================
        dashCourseModel = new DefaultTableModel(
                new String[]{"ID", "Course", "Duration", "Price"}, 0);

        JTable courseTable = new JTable(dashCourseModel);
        JScrollPane courseScroll = new JScrollPane(courseTable);

        // ================= STUDENT TABLE =================
        dashStudentModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Department"}, 0);

        JTable studentTable = new JTable(dashStudentModel);
        JScrollPane studentScroll = new JScrollPane(studentTable);

        // ================= TOP TITLE =================
        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(panel.getBackground());
        top.add(title);

        // ================= CENTER SPLIT =================
        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                courseScroll,
                studentScroll
        );

        split.setResizeWeight(0.5);

        panel.add(top, BorderLayout.NORTH);
        panel.add(split, BorderLayout.CENTER);

        // LOAD DATA
        loadCoursesDash();
        loadStudentsDash();

        return panel;
    }


    // ======================================================
    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 246, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        courseModel = new DefaultTableModel(
                new String[]{"ID", "Course", "Duration", "Price"}, 0);

        JTable table = new JTable(courseModel);
        JScrollPane scroll = new JScrollPane(table);

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
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

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

        // ADD
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

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Course added successfully!");
                refreshAll();
                ps.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // EDIT
        edit.addActionListener(e -> {
            try {
                if (id.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a course first!");
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
        // DELETE
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
                    JOptionPane.showMessageDialog(null, "Do you wanna delete this course ?");
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
                        rs.getString("department")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createMenuButton(String text, String card) {

        JButton b = new JButton(text);

        // STYLE
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);

        b.setBackground(sidebarColor);
        b.setForeground(Color.WHITE);

        b.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);

        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        b.setBorder(new EmptyBorder(10, 20, 10, 10));

        // HOVER EFFECT
        Color normal = sidebarColor;
        Color hover = new Color(45, 58, 68);
        Color active = new Color(70, 90, 110);

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
    public static void main(String[] args) {
        new FullAdminDashboard();
    }
}