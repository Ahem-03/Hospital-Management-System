import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddPatient extends JFrame implements ActionListener {

    JTextField txtPatientId, txtName, txtAge, txtDisease, txtContact;
    JRadioButton rbmale, rbfemale;
    JButton btnSave, btnClear, btnBack_2;

    // Database credentials
    final String url = "jdbc:mysql://localhost:3306/hospital_db";
    final String user = "root";
    final String user_password = "Ahem@0304";

    Connection con;
    Statement stmt;

    public AddPatient() {
        setTitle("🏥 Add New Patient");
        setSize(1050, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 250, 255));

        JLabel titleLabel = new JLabel("➕ Add New Patient");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 60, 120));
        titleLabel.setBounds(150, 30, 300, 40);
        add(titleLabel);

        // Patient ID (Auto-generated, read-only)
        JLabel lblPatientId = new JLabel("Patient ID:");
        lblPatientId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPatientId.setBounds(80, 100, 150, 30);
        //add(lblPatientId);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(250, 100, 250, 35);
        txtPatientId.setEditable(false);
        txtPatientId.setBackground(new Color(230, 230, 230));
        txtPatientId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        //add(txtPatientId);

        // patient id auto generate
        txtPatientId.setText(ViewPatient.generateNextPatientId(txtPatientId));

        // Name
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblName.setBounds(80, 160, 150, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(250, 160, 250, 35);
        txtName.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        add(txtName);

        // Age
        JLabel lblAge = new JLabel("Age:");
        lblAge.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblAge.setBounds(80, 220, 150, 30);
        add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(250, 220, 250, 35);
        txtAge.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        add(txtAge);

        // Gender
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblGender.setBounds(80, 280, 150, 30);
        add(lblGender);

        // radio button
        rbmale = new JRadioButton("Male");
        rbmale.setBounds(250, 280, 100, 35);
        rbmale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbmale.setBackground(new Color(240, 250, 255));
        add(rbmale);
        rbmale.setSelected(false);
        // female button
        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(360, 280, 100, 35);
        rbfemale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbfemale.setBackground(new Color(240, 250, 255));
        add(rbfemale);
        rbfemale.setSelected(false);
        // grouping the radiio button
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbmale);
        bg.add(rbfemale);

        // Disease
        JLabel lblDisease = new JLabel("Disease:");
        lblDisease.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDisease.setBounds(80, 340, 150, 30);
        add(lblDisease);

        txtDisease = new JTextField();
        txtDisease.setBounds(250, 340, 250, 35);
        txtDisease.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        add(txtDisease);

        // Contact
        JLabel lblContact = new JLabel("Contact:");
        lblContact.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblContact.setBounds(80, 400, 150, 30);
        add(lblContact);

        txtContact = new JTextField();
        txtContact.setBounds(250, 400, 250, 35);
        txtContact.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        add(txtContact);

        // Save Button
        btnSave = new JButton("💾 Save Patient");
        btnSave.setBounds(80, 480, 180, 45);
        btnSave.setBackground(new Color(0, 153, 102));
        btnSave.setForeground(Color.white);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.addActionListener(this);
        add(btnSave);

        // Clear Button
        btnClear = new JButton("🔄 Clear");
        btnClear.setBounds(280, 480, 100, 45);
        btnClear.setBackground(new Color(255, 165, 0));
        btnClear.setForeground(Color.white);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(btnClear);

        // Back Button
        btnBack_2 = new JButton("⬅ Back");
        btnBack_2.setBounds(400, 480, 100, 45);
        btnBack_2.setBackground(new Color(255, 87, 87));
        btnBack_2.setForeground(Color.white);
        btnBack_2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBack_2.addActionListener(new ViewPatient(){
            public void actionPerformed(ActionEvent e) {
                setLocationRelativeTo(null);
                AddPatient.this.setVisible(false);
            }
        });
        add(btnBack_2);

        setVisible(true);
    }

    // form here we will add the petient
    public void actionPerformed(ActionEvent ae) {
        String patient_id = txtPatientId.getText();
        String name = txtName.getText();
        String age = txtAge.getText();
        String disease = txtDisease.getText();
        String contact = txtContact.getText();

        // ==== in this we will take the radio input ======
        String gender = "";
        if (rbmale.isSelected()) {
            gender = "male";
        } else if (rbfemale.isSelected()) {
            gender = "female";
        } else if (gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select gender!");
            return;
        }
        if (ae.getSource() == btnSave) {

            try {
                con = DriverManager.getConnection(url, user, user_password);
                stmt = con.createStatement();
                System.out.println("database is connected....");
                String query = "insert into patients(ewpatient_id ,name, age, gender, disease, contact) values ('"
                        + patient_id + "','" + name + "','" + age + "','" + gender + "','" + disease + "','" + contact
                        + "' )";

                stmt.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "record inserted.....");

                JOptionPane.showMessageDialog(this,
                        "✅ Patient added successfully!\nPatient ID: " + ViewPatient.generateNextPatientId(txtPatientId),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Generate new ID for next patient
                txtPatientId.setText(ViewPatient.generateNextPatientId(txtPatientId));
            } catch (Exception e) {
                System.out.println("save button problem" + e);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver connnected");
        } catch (Exception e) {
            System.out.println("problem" + e);
        }
        SwingUtilities.invokeLater(() -> new AddPatient());
    }
}
