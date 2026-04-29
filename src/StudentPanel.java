import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentPanel extends JPanel {

    private DefaultTableModel model;
    private JTable table;

    public StudentPanel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 246, 250));

        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Department", "Status"}, 0
        );

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JButton blockBtn = new JButton("Block");
        JButton unblockBtn = new JButton("Unblock");
        JButton deleteBtn = new JButton("Delete");

        styleButton(blockBtn, new Color(231, 76, 60));
        styleButton(unblockBtn, new Color(46, 204, 113));
        styleButton(deleteBtn, new Color(52, 73, 94));

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(getBackground());
        btnPanel.add(blockBtn);
        btnPanel.add(unblockBtn);
        btnPanel.add(deleteBtn);

        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadStudents();

        blockBtn.addActionListener(e -> updateStatus("BLOCKED"));
        unblockBtn.addActionListener(e -> updateStatus("ACTIVE"));
        deleteBtn.addActionListener(e -> deleteStudent());
    }

    // ======================================================
    private void updateStatus(String status) {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first!");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE students SET status=? WHERE id=?"
            );

            ps.setString(1, status);
            ps.setInt(2, id);   // ✅ FIXED HERE

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Student " + status);
            } else {
                JOptionPane.showMessageDialog(this, "No update happened!");
            }

            loadStudents();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ======================================================
    private void deleteStudent() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first!");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM students WHERE id=?"
            );

            ps.setInt(1, id);

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed!");
            }

            loadStudents();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ======================================================
    private void loadStudents() {

        try {
            Connection con = DBConnection.getConnection();

            model.setRowCount(0);

            ResultSet rs = con.createStatement()
                    .executeQuery("SELECT * FROM students ORDER BY id DESC");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ======================================================
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 35));
    }
}
