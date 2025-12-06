import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PrescriptionDetail extends JFrame {
    JPanel pPrescJPanel;
    DefaultTableModel modelPresc;
    JTable tablePresc;
    
    // Database credentials
    final static String url = "jdbc:mysql://localhost:3306/hospital_db";
    final static String user = "root";
    final static String user_password = "Ahem@0304";

    public PrescriptionDetail() {
        // Prescription panel (full frame)
        pPrescJPanel = new JPanel();
        pPrescJPanel.setLayout(null);
        pPrescJPanel.setBackground(new Color(245, 250, 255));

        // Create table for prescriptions
        modelPresc = new DefaultTableModel();
        modelPresc.addColumn("Appointment ID");
        modelPresc.addColumn("Patient Name");
        modelPresc.addColumn("Symptoms");
        modelPresc.addColumn("Diagnosis");
        modelPresc.addColumn("Medicines");
        modelPresc.addColumn("Advice");
        modelPresc.addColumn("Next Visit Date");
        
        tablePresc = new JTable(modelPresc) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // non-editable
            }
        };
        tablePresc.setRowHeight(24);
        tablePresc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane tablePrescScroll = new JScrollPane(tablePresc);
        tablePrescScroll.setBounds(20, 80, 1010, 680);
        pPrescJPanel.add(tablePrescScroll);
        
        // Title label
        JLabel lblPrescTitle = new JLabel("📋 All Prescriptions", JLabel.CENTER);
        lblPrescTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPrescTitle.setBounds(0, 20, 1050, 30);
        pPrescJPanel.add(lblPrescTitle);
        
        
        // Back button
        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(52, 105, 158));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBounds(950, 20, 80, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new D_dashboard().setVisible(true);
                PrescriptionDetail.this.setVisible(false);
            }
        });
        pPrescJPanel.add(btnBack);

        add(pPrescJPanel);

        // Frame setup
        setTitle("Prescription Details");
        setSize(1050, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
        // Load prescriptions on open
        loadPrescriptions();
    }

    private void loadPrescriptions() {
        modelPresc.setRowCount(0);
        try (Connection con = DriverManager.getConnection(url, user, user_password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT  appointment_id, patient_name, Symptoms, Diagnosis, Medicines, Advice, next_visit_date FROM patient_prec")) {

            while (rs.next()) {
                Object[] row = {
                        rs.getString("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("Symptoms"),
                        rs.getString("Diagnosis"),
                        rs.getString("Medicines"),
                        rs.getString("Advice"),
                        rs.getString("next_visit_date")
                };
                modelPresc.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading prescriptions: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrescriptionDetail());
    }
}
