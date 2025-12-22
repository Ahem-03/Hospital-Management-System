import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login_Page extends JFrame {
    JPanel p1;
    JLabel lh, lId, lpf;
    JTextField tfId;
    JPasswordField pwField;
    JButton bDoctor, bAdmin;

    // Database credentials
    final static String url = "jdbc:mysql://localhost:3306/hospital_db";
    final static String user = "root";
    final static String user_password = "Ahem@0304";

    Connection con;
    Statement stmt;

    public Login_Page() {
        // initializing
        p1 = new JPanel();
        p1.setLayout(null);

        ImageIcon bgIcon = new ImageIcon("image_1.png");
        Image img = bgIcon.getImage().getScaledInstance(1250, 820, Image.SCALE_SMOOTH);
        bgIcon = new ImageIcon(img);

        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setBounds(0, 0, 1250, 820);
        backgroundLabel.setLayout(null); // Allow adding components over it
        // set bounds
        // background image

        // label
        lh = new JLabel("-: City Hospital :-");
        lh.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lId = new JLabel("User Id: ");
        lpf = new JLabel("Password: ");
        // textfield
        tfId = new JTextField();
        pwField = new JPasswordField();
        // button
        bDoctor = new JButton("Doctor Login");
        bAdmin = new JButton("Admin Login");

        // set bounds
        // label
        lh.setBounds(425, 80, 400, 40);
        lId.setBounds(425, 200, 150, 30);
        lpf.setBounds(425, 260, 150, 30);
        // text field
        tfId.setBounds(575, 200, 250, 30);
        pwField.setBounds(575, 260, 250, 30);
        // button
        bDoctor.setBounds(475, 350, 150, 40);
        bAdmin.setBounds(675, 350, 150, 40);

        // fonts
        // label
        lId.setFont(new Font("Arial", Font.BOLD, 18));
        lpf.setFont(new Font("Arial", Font.BOLD, 18));
        // Text Fields
        tfId.setFont(new Font("Arial", Font.PLAIN, 16));
        tfId.setBackground(Color.WHITE);
        pwField.setFont(new Font("Arial", Font.PLAIN, 16));
        pwField.setBackground(Color.WHITE);
        // button
        bDoctor.setFont(new Font("Arial", Font.BOLD, 15));
        bAdmin.setFont(new Font("Arial", Font.BOLD, 15));

        // background
        // button
        bDoctor.setBackground(new Color(0, 102, 204));
        bAdmin.setBackground(new Color(0, 153, 76));

        // foreground
        // Header Label
        lh.setForeground(new Color(0, 102, 102));
        // User ID Label
        lId.setForeground(new Color(0, 51, 51));
        // Password Label
        lpf.setForeground(new Color(0, 51, 51));
        // Buttons
        bDoctor.setForeground(Color.WHITE);
        bAdmin.setForeground(Color.WHITE);
        // focus painted
        bDoctor.setFocusPainted(false);
        bAdmin.setFocusPainted(false);

        // adding the componenet to the panel
        add(p1);
        // p1.add(lh);
        p1.add(lId);
        p1.add(lpf);
        p1.add(tfId);
        p1.add(pwField);
        p1.add(bDoctor);
        p1.add(bAdmin);
        p1.add(backgroundLabel);

        bDoctor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginDoctor();
            }
        });
        bAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginAdmin();
            }
        });

        setTitle("Login Page");
        setSize(1250, 820);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void loginDoctor() {
        String id = tfId.getText().trim();
        String password = new String(pwField.getPassword());
        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter User ID and Password!", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            con = DriverManager.getConnection(url, user, user_password);
            String query = "select * from doctor_password where doctor_id = ? and BINARY password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, id);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome Dr. ");
                new D_dashboard();
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User ID or Password!", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                tfId.setText("");
                pwField.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Something is went wrong", "password", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void loginAdmin() {
        String id = tfId.getText().trim();
        String password = new String(pwField.getPassword());

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter User ID and Password!", "Empty Fields",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            con = DriverManager.getConnection(url, user, user_password);
            String query = "select * from admin where admin_id = ? and BINARY password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, id);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome Admin member ");
                new Main_Menu();
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User ID or Password!", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                tfId.setText("");
                pwField.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Something is went wrong", "password", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login_Page());
    }
}  