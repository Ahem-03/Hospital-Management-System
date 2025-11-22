import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class DoctorDashboard extends JFrame implements ActionListener {

    JLabel lblTitle, lblDoctorInfo;
    JTable tableAppointments;
    JTextField txtPatientID, txtSymptoms, txtDiagnosis, txtMedicines, txtAdvice, txtNextVisit;
    JButton btnSave, btnView, btnLogout;
    DefaultTableModel model;

    public DoctorDashboard() {
        setTitle("Doctor Dashboard - Hospital Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // ===== Header =====
        lblTitle = new JLabel("🏥 Doctor Dashboard", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(0, 10, 900, 40);
        add(lblTitle);

        // ===== Doctor Info Panel =====
        lblDoctorInfo = new JLabel("Dr. Ahem Sharma | Cardiology | Available: 10 AM - 3 PM", JLabel.CENTER);
        lblDoctorInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDoctorInfo.setBounds(0, 60, 900, 30);
        lblDoctorInfo.setForeground(Color.DARK_GRAY);
        add(lblDoctorInfo);

        // ===== Appointments Table =====
        JLabel lblApp = new JLabel("Today's Appointments:");
        lblApp.setFont(new Font("Arial", Font.BOLD, 16));
        lblApp.setBounds(30, 100, 250, 30);
        add(lblApp);

        String[] columns = {"Appointment ID", "Patient Name", "Time", "Status"};
        model = new DefaultTableModel(columns, 0);
        tableAppointments = new JTable(model);
        JScrollPane sp = new JScrollPane(tableAppointments);
        sp.setBounds(30, 140, 400, 200);
        add(sp);

        // Dummy data
        model.addRow(new Object[]{"A101", "Rohan Mehta", "10:30 AM", "Pending"});
        model.addRow(new Object[]{"A102", "Simran Kaur", "11:15 AM", "Pending"});
        model.addRow(new Object[]{"A103", "Amit Verma", "12:00 PM", "Completed"});

        // ===== Prescription Panel =====
        JLabel lblPresc = new JLabel("Add Prescription:");
        lblPresc.setFont(new Font("Arial", Font.BOLD, 16));
        lblPresc.setBounds(470, 100, 200, 30);
        add(lblPresc);

        int x = 470, y = 140, w = 150, h = 25, gap = 40;

        add(new JLabel("Patient ID:")).setBounds(x, y, w, h);
        txtPatientID = new JTextField();
        txtPatientID.setBounds(x + 120, y, 250, h);
        add(txtPatientID);

        add(new JLabel("Symptoms:")).setBounds(x, y + gap, w, h);
        txtSymptoms = new JTextField();
        txtSymptoms.setBounds(x + 120, y + gap, 250, h);
        add(txtSymptoms);

        add(new JLabel("Diagnosis:")).setBounds(x, y + 2 * gap, w, h);
        txtDiagnosis = new JTextField();
        txtDiagnosis.setBounds(x + 120, y + 2 * gap, 250, h);
        add(txtDiagnosis);

        add(new JLabel("Medicines:")).setBounds(x, y + 3 * gap, w, h);
        txtMedicines = new JTextField();
        txtMedicines.setBounds(x + 120, y + 3 * gap, 250, h);
        add(txtMedicines);

        add(new JLabel("Advice:")).setBounds(x, y + 4 * gap, w, h);
        txtAdvice = new JTextField();
        txtAdvice.setBounds(x + 120, y + 4 * gap, 250, h);
        add(txtAdvice);

        add(new JLabel("Next Visit:")).setBounds(x, y + 5 * gap, w, h);
        txtNextVisit = new JTextField();
        txtNextVisit.setBounds(x + 120, y + 5 * gap, 250, h);
        add(txtNextVisit);

        // ===== Buttons =====
        btnSave = new JButton("💾 Save Prescription");
        btnView = new JButton("📋 View Records");
        btnLogout = new JButton("🚪 Logout");

        btnSave.setBounds(470, 400, 180, 35);
        btnView.setBounds(670, 400, 150, 35);
        btnLogout.setBounds(390, 470, 120, 35);

        add(btnSave);
        add(btnView);
        add(btnLogout);

        btnSave.addActionListener(this);
        btnView.addActionListener(this);
        btnLogout.addActionListener(this);

        // Background
        getContentPane().setBackground(new Color(230, 245, 255));

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            String patientID = txtPatientID.getText();
            String diagnosis = txtDiagnosis.getText();
            if (patientID.isEmpty() || diagnosis.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            } else {
                JOptionPane.showMessageDialog(this, "Prescription saved for Patient ID: " + patientID);
            }
        } else if (e.getSource() == btnView) {
            JOptionPane.showMessageDialog(this, "Viewing all patient records...");
        } else if (e.getSource() == btnLogout) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?");
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                // new LoginPage();
            }
        }
    }

    public static void main(String[] args) {
        new DoctorDashboard();
    }
}
