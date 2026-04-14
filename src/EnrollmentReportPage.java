import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import javax.swing.border.TitledBorder;

public class EnrollmentReportPage extends JPanel {
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel totalEnrollmentsLabel;

    public EnrollmentReportPage() {
        // ዋናው ሌይአውት
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. Header Section (Title and Statistics) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Student Enrollment Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(44, 62, 80));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        totalEnrollmentsLabel = new JLabel("Total Enrollments: 0");
        totalEnrollmentsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        headerPanel.add(totalEnrollmentsLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- 2. Filter Section (ለመፈለጊያ) ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(false);
        filterPanel.add(new JLabel("Search Student/Course: "));

        searchField = new JTextField(20);
        filterPanel.add(searchField);

        JButton refreshBtn = new JButton("Refresh Data");
        filterPanel.add(refreshBtn);

        // --- 3. Table Section (ሪፖርቱ የሚታይበት) ---
        String[] columns = {"Enrollment ID", "Student Name", "Email", "Course Name", "Registration Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ሰንጠረዡ በቀጥታ እንዳይቀየር
            }
        };

        reportTable = new JTable(tableModel);
        reportTable.setRowHeight(30);
        reportTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // ዳታ ለመፈለግ (Search) የሚረዳ logic
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        reportTable.setRowSorter(sorter);
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(reportTable);
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), "Enrollment List"
        );
        scrollPane.setBorder(border);
        add(scrollPane, BorderLayout.CENTER);

        // --- 4. Bottom Action Section ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);

        JButton exportBtn = new JButton("Export to Excel/PDF");
        JButton blockStudentBtn = new JButton("Block Student Account");
        blockStudentBtn.setBackground(new Color(231, 76, 60));
        blockStudentBtn.setForeground(Color.WHITE);

        actionPanel.add(exportBtn);
        actionPanel.add(blockStudentBtn);
        add(actionPanel, BorderLayout.SOUTH);

        // Sample Data መሙያ
        loadSampleData();
    }

    private void loadSampleData() {
        Object[][] data = {
            {"E-001", "Biruk", "biruk@email.com", "Java Programming", "2026-03-12", "Active"},
            {"E-002", "Kidist", "kidu@email.com", "Web Development", "2026-04-01", "Pending"},
            {"E-003", "Abebe", "abe@email.com", "Database Systems", "2026-04-05", "Active"},
            {"E-004", "Sara", "sara@email.com", "Networking", "2026-04-08", "Blocked"}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
        totalEnrollmentsLabel.setText("Total Enrollments: " + tableModel.getRowCount());
    }

    // ለአድሚን ፍሰቱ ዋናውን Frame ለመሞከር
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin - Enrollment Reports");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.add(new EnrollmentReportPage());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}