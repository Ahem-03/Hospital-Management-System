import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddPatient extends JFrame implements ActionListener {

    JTextField txtPatientId, txtName, txtAge, txtDisease, txtContact;
    JRadioButton rbmale, rbfemale;
    JComboBox<String> cbWardRange, cbRoomNumber;
    JButton btnSave, btnClear, btnBack_2;

    // Database credentials
    final String url = "jdbc:mysql://localhost:3306/hospital_db";
    final String user = "root";
    final String user_password = "Ahem@0304";

    Connection con;
    Statement stmt;

    public AddPatient() {
        setTitle("🥽 Add New Patient");
        setSize(1250, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 250, 255));

        JLabel titleLabel = new JLabel("➕ Add New Patient");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 60, 120));
        titleLabel.setBounds(475, 30, 300, 40);
        add(titleLabel);

        // Patient ID (Auto-generated, read-only)
        JLabel lblPatientId = new JLabel("Patient ID:");
        lblPatientId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPatientId.setBounds(380, 100, 150, 30);
        //add(lblPatientId);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(575, 100, 250, 35);
        txtPatientId.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPatientId.setEditable(false);
        txtPatientId.setBackground(new Color(230, 230, 230));
        txtPatientId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        //add(txtPatientId);

        // patient id auto generate
        txtPatientId.setText(ViewPatient.generateNextPatientId(txtPatientId));

        // Name
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblName.setBounds(405, 160, 150, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(575, 160, 250, 35);
        txtName.setFont(new Font("Arial", Font.PLAIN, 16));
        txtName.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        add(txtName);

        // Age
        JLabel lblAge = new JLabel("Age:");
        lblAge.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblAge.setBounds(405, 220, 150, 30);
        add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(575, 220, 250, 35);
        txtAge.setFont(new Font("Arial", Font.PLAIN, 16));
        txtAge.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        add(txtAge);

        // Gender
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblGender.setBounds(405, 280, 150, 30);
        add(lblGender);

        // radio button
        rbmale = new JRadioButton("Male");
        rbmale.setBounds(575, 280, 100, 35);
        rbmale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbmale.setBackground(new Color(240, 250, 255));
        add(rbmale);
        rbmale.setSelected(false);
        // female button
        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(685, 280, 100, 35);
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
        lblDisease.setBounds(405, 340, 150, 30);
        add(lblDisease);

        txtDisease = new JTextField();
        txtDisease.setBounds(575, 340, 250, 35);
        txtDisease.setFont(new Font("Arial", Font.PLAIN, 16));
        txtDisease.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        add(txtDisease);

        // Contact
        JLabel lblContact = new JLabel("Contact:");
        lblContact.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblContact.setBounds(405, 400, 150, 30);
        add(lblContact);

        txtContact = new JTextField();
        txtContact.setBounds(575, 400, 250, 35);
        txtContact.setFont(new Font("Arial", Font.PLAIN, 16));
        txtContact.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        add(txtContact);

        // Ward No.
        JLabel lblWardRange = new JLabel("Ward No.:");
        lblWardRange.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblWardRange.setBounds(405, 460, 150, 30);
        add(lblWardRange);

        String[] wardRanges = {"", "A", "B", "C", "D"};
        cbWardRange = new JComboBox<>(wardRanges);
        cbWardRange.setBounds(575, 460, 100, 35);
        cbWardRange.setFont(new Font("Arial", Font.PLAIN, 16));
        cbWardRange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRoomNumbers();
            }
        });
        add(cbWardRange);

        // Room Number
        JLabel lblRoomNumber = new JLabel("Room No.:");
        lblRoomNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRoomNumber.setBounds(700, 460, 150, 30);
        add(lblRoomNumber);

        cbRoomNumber = new JComboBox<>();
        cbRoomNumber.setBounds(850, 460, 100, 35);
        cbRoomNumber.setFont(new Font("Arial", Font.PLAIN, 16));
        add(cbRoomNumber);

        // Save Button
        btnSave = new JButton("💾 Save Patient");
        btnSave.setBounds(405, 540, 180, 45);
        btnSave.setBackground(new Color(0, 153, 102));
        btnSave.setForeground(Color.white);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.addActionListener(this);
        add(btnSave);

        // Clear Button
        btnClear = new JButton("🔄 Clear");
        btnClear.setBounds(605, 540, 100, 45);
        btnClear.setBackground(new Color(255, 165, 0));
        btnClear.setForeground(Color.white);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(btnClear);
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                clearForm();
            }
        });

        // Back Button
        btnBack_2 = new JButton("⬅ Back");
        btnBack_2.setBounds(725, 540, 100, 45);
        btnBack_2.setBackground(new Color(255, 87, 87));
        btnBack_2.setForeground(Color.white);
        btnBack_2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBack_2.addActionListener(new ViewPatient(){
            public void actionPerformed(ActionEvent e) {
                AddPatient.this.dispose();
            }
        });

        add(btnBack_2);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    // Update room numbers based on selected ward
    private void updateRoomNumbers() {
        cbRoomNumber.removeAllItems();
        String selectedWard = (String) cbWardRange.getSelectedItem();
        
        if (selectedWard != null && !selectedWard.isEmpty()) {
            for (int i = 101; i <= 114; i++) {
                cbRoomNumber.addItem(String.valueOf(i));
            }
        }
    }

    // form here we will add the patient
    public void actionPerformed(ActionEvent ae) {
        String patient_id = txtPatientId.getText();
        String name = txtName.getText();
        String age = txtAge.getText();
        String disease = txtDisease.getText();
        String contact = txtContact.getText();
        String wardNo = (String) cbWardRange.getSelectedItem();
        String roomNo = (String) cbRoomNumber.getSelectedItem();

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

        // Validate ward and room selection
        if (wardNo == null || wardNo.isEmpty() || roomNo == null || roomNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select ward and room number!");
            return;
        }

        if (ae.getSource() == btnSave) {

            try {
                con = DriverManager.getConnection(url, user, user_password);
                stmt = con.createStatement();
                System.out.println("database is connected....");
                String query = "insert into patients(patient_id, name, age, gender, disease, contact, ward_number, room_number) values ('"
                        + patient_id + "','" + name + "','" + age + "','" + gender + "','" + disease + "','" + contact
                        + "','Ward " + wardNo + "','" + roomNo + "')";

                stmt.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "record inserted.....");

                JOptionPane.showMessageDialog(this,
                        "✅ Patient added successfully!\nPatient ID: " + ViewPatient.generateNextPatientId(txtPatientId),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Generate new ID for next patient
                txtPatientId.setText(ViewPatient.generateNextPatientId(txtPatientId));
                clearForm();
            } catch (Exception e) {
                System.out.println("save button problem" + e);
            }
        }
    }

    public void clearForm(){
        txtName.setText("");
        txtAge.setText("");
        txtDisease.setText("");
        txtContact.setText("");
        cbWardRange.setSelectedIndex(0);
        cbRoomNumber.removeAllItems();
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