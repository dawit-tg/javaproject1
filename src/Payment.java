import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Payment {
    //click button  call methods
    public static void showPaymentGateway(String studentId , String courseName , double price) {
        JDialog payDialog = new JDialog();
        payDialog.setTitle("Secure Payment Gateway");
        payDialog.setSize(380 , 450);
        payDialog.setLayout(new GridLayout(8 , 1 , 10 , 10));
        payDialog.setLocationRelativeTo(null);
        payDialog.setModal(true);
        // UI Elements
        payDialog.add(new JLabel("Enrollment for: " + courseName , SwingConstants.CENTER));
        payDialog.add(new JLabel("Amount to Pay: " + price + " ETB" , SwingConstants.CENTER));
        JLabel bankInfo = new JLabel("CBE: 1000123456789" , SwingConstants.CENTER);
        JLabel bankinfo1=new JLabel("Telebirr:0995878890)",SwingConstants.CENTER);
        bankInfo.setForeground(Color.BLUE);
        payDialog.add(bankInfo);
        bankinfo1.setForeground(Color.GREEN);
        payDialog.add(bankinfo1);

        JTextField txtRef = new JTextField();
        txtRef.setBorder(BorderFactory.createTitledBorder("Paste Transaction Reference No."));
        payDialog.add(txtRef);

        JButton submit = new JButton("Confirm Payment");
        submit.setBackground(new Color(46 , 204 , 113));
        submit.setForeground(Color.WHITE);
        payDialog.add(submit);
        submit.addActionListener(e -> {
            String refNo = txtRef.getText().trim();
            if (refNo.isEmpty()) {
                JOptionPane.showMessageDialog(payDialog , "please enter transaction ID!");
            } else {
                if (saveToDatabase(studentId , courseName , refNo , price)) {
                    JOptionPane.showMessageDialog(payDialog , "payment registered! admin is approved Wait..");
                    payDialog.dispose();
                }
            }
        });
        payDialog.setVisible(true);
    }
//database pendng
    private static boolean saveToDatabase(String stId , String course , String ref , double amt) {
        String sql = "INSERT INTO pending_registrations (student_id, course_name, reference_no, amount, status) VALUES (?, ?, ?, ?, 'Pending')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1 , stId);
            pst.setString(2 , course);
            pst.setString(3 , ref);
            pst.setDouble(4 , amt);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null , "Database Error: " + ex.getMessage());
            return false;
        }
    }
}