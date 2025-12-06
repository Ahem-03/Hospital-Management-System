import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class D_dashboard extends JFrame {
    JPanel p, pApmtJPanel;
    JTextArea tArea;
    JLabel lblapmt, jdtitle, jdoctorInfo, lblAppointmentID, lblPatientName, lblSymptoms, lblDiagnosis, lblMedicines,
            lblAdvice,
            lblNextVisit;
    JTextField txtAppointmentID, txtPatientName, txtSymptoms, txtDiagnosis, txtMedicines, txtAdvice, txtNextVisit;
    JButton btnSave, btnView, btnLogout, btnSearch, btnRefresh;

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
        lblAppointmentID = new JLabel("Appointment ID:                              Name");
        lblPatientName = new JLabel("OR");
        lblSymptoms = new JLabel("Symptoms:");
        lblDiagnosis = new JLabel("Diagnosis:");
        lblMedicines = new JLabel("Medicines:");
        lblAdvice = new JLabel("Advice:");
        lblNextVisit = new JLabel("Next Visit Date:");
        // patient label font setting
        lblAppointmentID.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPatientName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSymptoms.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDiagnosis.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblMedicines.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblAdvice.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNextVisit.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Text fields
        txtAppointmentID = new JTextField();
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
        btnRefresh = new JButton("🔄 Refresh");
        btnLogout = new JButton("🚪 Logout");
        // backgrund color
        btnSearch.setBackground(new Color(0, 102, 204));
        btnSave.setBackground(new Color(0, 153, 76));
        btnView.setBackground(new Color(0, 102, 204));
        btnRefresh.setBackground(new Color(0, 102, 204));
        btnLogout.setBackground(new Color(204, 0, 0));
        // color
        btnSearch.setForeground(Color.WHITE);
        btnSave.setForeground(Color.WHITE);
        btnView.setForeground(Color.WHITE);
        btnRefresh.setForeground(Color.WHITE);
        btnLogout.setForeground(Color.WHITE);

        // Add components to panel
        // label
        p.add(jdtitle);
        p.add(jdoctorInfo);
        p.add(lblAppointmentID);
        p.add(lblPatientName);
        p.add(lblSymptoms);
        p.add(lblDiagnosis);
        p.add(lblMedicines);
        p.add(lblAdvice);
        p.add(lblNextVisit);
        // text field
        p.add(txtAppointmentID);
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
        pApmtJPanel.add(btnRefresh);

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
        lblAppointmentID.setBounds(20, 100, 500, 30);
        txtAppointmentID.setBounds(110, 140, 80, 30);

        lblPatientName.setBounds(200, 140, 30, 30);
        txtPatientName.setBounds(230, 140, 80, 30);

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
        btnSearch.setBounds(320, 140, 50, 30);
        btnSave.setBounds(20, 460, 150, 35);
        btnView.setBounds(180, 460, 160, 35);
        btnRefresh.setBounds(550, 10, 100, 30);
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
         btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAppointments();
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
        String appointment_id = txtAppointmentID.getText().trim();
        String patient_name = txtPatientName.getText().trim();
        if (appointment_id.isEmpty() && patient_name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill the ID or NAME ", appointment_id,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Connection con = DriverManager.getConnection(url, user, user_password);

            // =========== Fiil the Patient name area when you enter the appointment_ID
            // ===========
            if (!appointment_id.isEmpty() && patient_name.isEmpty()) {
                String query_1 = "SELECT patient_name FROM appointments where appointment_id like ? ";
                System.out.println("first qury is running ");
                try {
                    PreparedStatement pst = con.prepareStatement(query_1);
                    pst.setString(1, "%"+appointment_id+"%");
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        txtPatientName.setText(rs.getString("patient_name"));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error loading " + e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

            // =========== Fiil the appointment id area when you enter the name ===========
            else if (!patient_name.isEmpty() && appointment_id.isEmpty()) {
                String query_2 = "SELECT appointment_id FROM appointments where patient_name like ?";
                System.out.println("second qury is running ");
                try {
                    PreparedStatement pst = con.prepareStatement(query_2);
                    pst.setString(1, "%" + patient_name + "%");
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        txtAppointmentID.setText(rs.getString("appointment_id"));
                    } else {

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error loading " + e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /// ================also stuck in insrting the data into the table =======
    public void savePrescription() {
        String appointment_ID = txtAppointmentID.getText().trim();
        String patientName = txtPatientName.getText().trim();
        String Symptoms = txtSymptoms.getText().trim();
        String diagnosis = txtDiagnosis.getText().trim();
        String medicine = txtMedicines.getText().trim();
        String advice = txtAdvice.getText().trim();
        String date = txtNextVisit.getText().trim();

        //validation
        if (Symptoms.isEmpty() || diagnosis.isEmpty() || medicine.isEmpty() || advice.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all filds", "validation",JOptionPane.WARNING_MESSAGE);
        return;
        }
        try {
            con = DriverManager.getConnection(url, user, user_password);
            stmt = con.createStatement();

            String qury_2 = "insert into patient_prec(appointment_id, patient_name, Symptoms, Diagnosis, Medicines, Advice, next_visit_date) values(?, ?, ?, ?, ?, ?, ?)";

            System.out.println("second qury is running..... ");
            PreparedStatement pst_2 = con.prepareStatement(qury_2);
            pst_2.setString(1, appointment_ID);
            pst_2.setString(2, patientName);
            pst_2.setString(3, Symptoms);
            pst_2.setString(4, diagnosis);
            pst_2.setString(5, medicine);
            pst_2.setString(6, advice);
            pst_2.setString(7, date);

            pst_2.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record Inseted");
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void clearForm(){
        txtAppointmentID.setText("");
                txtPatientName.setText("");
                txtSymptoms.setText("");
                txtDiagnosis.setText("");
                txtMedicines.setText("");
                txtAdvice.setText("");
                txtNextVisit.setText("");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new D_dashboard());
    }
}