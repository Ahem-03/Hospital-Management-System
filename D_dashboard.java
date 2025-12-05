import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class D_dashboard extends JFrame {
    JPanel p, pApmtJPanel;
    JTextArea tArea;
    JLabel lblapmt, jdtitle, jdoctorInfo, lblPatientID, lblPatientName, lblSymptoms, lblDiagnosis, lblMedicines,
            lblAdvice,
            lblNextVisit;
    JTextField txtPatientID, txtPatientName, txtSymptoms, txtDiagnosis, txtMedicines, txtAdvice, txtNextVisit;
    JButton btnSave, btnView, btnLogout, btnSearch;

    // Table for appointments
    DefaultTableModel model;
    JTable table;

    // Database credentials
    final static String url = "jdbc:mysql://localhost:3306/hospital_db";
    final static String user = "root";
    final static String user_password = "Ahem@0304";

    Connection con;
    Statement stmt;

    public D_dashboard() {
        // Panel
        p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(230, 245, 255)); // soft blue background
        // second panel
        pApmtJPanel = new JPanel();
        pApmtJPanel.setLayout(null);
        pApmtJPanel.setBackground(Color.WHITE);

        // Title
        jdtitle = new JLabel("🏥 Doctor Dashboard", JLabel.CENTER);
        jdtitle.setFont(new Font("Segoe UI", Font.BOLD, 28));

        // appointment label
        lblapmt = new JLabel("Today's Appointment ");
        lblapmt.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblapmt.setForeground(Color.GRAY);

        // Doctor info label
        jdoctorInfo = new JLabel("Dr. Ahem Sharma | Cardiology | Available: 10 AM - 3 PM", JLabel.CENTER);
        jdoctorInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jdoctorInfo.setForeground(Color.DARK_GRAY);

        // Labels
        lblPatientID = new JLabel("Appointment ID:");
        lblPatientName = new JLabel("OR");
        lblSymptoms = new JLabel("Symptoms:");
        lblDiagnosis = new JLabel("Diagnosis:");
        lblMedicines = new JLabel("Medicines:");
        lblAdvice = new JLabel("Advice:");
        lblNextVisit = new JLabel("Next Visit Date:");
        // patient label font setting
        lblPatientID.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPatientName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSymptoms.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDiagnosis.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblMedicines.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblAdvice.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNextVisit.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Text fields
        txtPatientID = new JTextField();
        txtPatientName = new JTextField();
        txtSymptoms = new JTextField();
        txtDiagnosis = new JTextField();
        txtMedicines = new JTextField();
        txtAdvice = new JTextField();
        txtNextVisit = new JTextField();

        // Buttons
        btnSearch = new JButton("🔍");
        btnSave = new JButton("💾  Save Prescription");
        btnView = new JButton("📋  View Appointments");
        btnLogout = new JButton("🚪 Logout");
        // backgrund color
        btnSearch.setBackground(new Color(0, 102, 204));
        btnSave.setBackground(new Color(0, 153, 76));
        btnView.setBackground(new Color(0, 102, 204));
        btnLogout.setBackground(new Color(204, 0, 0));
        // color
        btnSearch.setForeground(Color.WHITE);
        btnSave.setForeground(Color.WHITE);
        btnView.setForeground(Color.WHITE);
        btnLogout.setForeground(Color.WHITE);

        // Add components to panel
        // label
        p.add(jdtitle);
        p.add(jdoctorInfo);
        p.add(lblPatientID);
        p.add(lblPatientName);
        p.add(lblSymptoms);
        p.add(lblDiagnosis);
        p.add(lblMedicines);
        p.add(lblAdvice);
        p.add(lblNextVisit);
        // text field
        p.add(txtPatientID);
        p.add(txtPatientName);
        p.add(txtSymptoms);
        p.add(txtDiagnosis);
        p.add(txtMedicines);
        p.add(txtAdvice);
        p.add(txtNextVisit);
        // button
        p.add(btnSearch);
        p.add(btnSave);
        p.add(btnView);
        p.add(btnLogout);

        add(p);
        p.add(pApmtJPanel);
        pApmtJPanel.add(lblapmt);

        // Create table for appointments
        model = new DefaultTableModel();
        model.addColumn("Appointment ID");
        model.addColumn("Patient Name");
        model.addColumn("Date");
        model.addColumn("Time");
        model.addColumn("Description");
        model.addColumn("Phone");

        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane tableScroll = new JScrollPane(table);

        // setBounds (x, y, w, h)
        // Appointment panel
        pApmtJPanel.setBounds(370, 140, 650, 610);
        // label
        lblapmt.setBounds(10, 10, 500, 35);
        tableScroll.setBounds(20, 50, 610, 530);
        pApmtJPanel.add(tableScroll);

        jdtitle.setBounds(0, 20, 1050, 40);
        jdoctorInfo.setBounds(0, 70, 1050, 30);

        // Left side for patient details
        lblPatientID.setBounds(20, 140, 120, 30);
        txtPatientID.setBounds(140, 140, 80, 30);

        lblPatientName.setBounds(225, 140, 30, 30);
        txtPatientName.setBounds(260, 140, 80, 30);

        lblSymptoms.setBounds(20, 190, 120, 30);
        txtSymptoms.setBounds(140, 190, 200, 30);

        lblDiagnosis.setBounds(20, 240, 120, 30);
        txtDiagnosis.setBounds(140, 240, 200, 30);

        lblMedicines.setBounds(20, 290, 120, 30);
        txtMedicines.setBounds(140, 290, 200, 30);

        lblAdvice.setBounds(20, 340, 120, 30);
        txtAdvice.setBounds(140, 340, 200, 30);

        lblNextVisit.setBounds(20, 390, 120, 30);
        txtNextVisit.setBounds(140, 390, 200, 30);

        // Buttons below form
        btnSearch.setBounds(350, 140, 80, 30);
        btnSave.setBounds(20, 460, 150, 35);
        btnView.setBounds(180, 460, 160, 35);
        btnLogout.setBounds(30, 720, 100, 35);

        // Action Listerner
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searching();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savePrescription();
            }
        });
        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAppointments();
                pApmtJPanel.setVisible(true);
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login_Page().setVisible(true);
                D_dashboard.this.setVisible(false);
            }
        });

        // Frame
        setTitle("Doctor Dashboard");
        setSize(1050, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pApmtJPanel.setVisible(false);
    }

    private void loadAppointments() {
        model.setRowCount(0);
        try {
            Connection con = DriverManager.getConnection(url, user, user_password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");

            while (rs.next()) {
                Object[] row = {
                        rs.getString("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("description"),
                        rs.getString("phone")
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    //// ===========stuck in to if condition
    public void searching() {
        String patient_ID = "%" + txtPatientID.getText().trim() + "%";
        if (patient_ID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill the ID or NAME ", patient_ID, JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Connection con = DriverManager.getConnection(url, user, user_password);
            stmt = con.createStatement();
            String query_1 = "SELECT patient_name FROM appointments where appointment_id like ?";
            System.out.println("first qury is running ");
            PreparedStatement pst = con.prepareStatement(query_1);
            pst.setString(1, patient_ID);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txtPatientName.setText(rs.getString("patient_name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

/// ================also stuck in insrting the data into the table =======
    public void savePrescription() {
        try {
            con =DriverManager.getConnection(url, user, user_password);

            String patient_ID = txtPatientID.getText().trim();
            String patientName = txtPatientName.getText().trim();
            String Symptoms = txtSymptoms.getText().trim();
            String diagnosis = txtDiagnosis.getText().trim();
            String medicine = txtMedicines.getText().trim();
            String advice = txtAdvice.getText().trim();
            String date = txtNextVisit.getText().trim();

            String qury_2 = "insert into patient_prec(patient_id, patient_name, Symptoms, Diagnosis, Medicines, Advice, next_visit_date) values(?, ?, ?, ?, ?, ?, ?)";

            System.out.println("second qury is running..... ");
            PreparedStatement pst_2 = con.prepareStatement(qury_2);
            pst_2.setString(1, patient_ID);
            pst_2.setString(2, patientName);
            pst_2.setString(3, Symptoms);
            pst_2.setString(4, diagnosis);
            pst_2.setString(5, medicine);
            pst_2.setString(6, advice);
            pst_2.setString(7, date);

            pst_2.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record Inseted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new D_dashboard());
    }
}