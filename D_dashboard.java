import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class D_dashboard extends JFrame implements ActionListener{
    JPanel p, pApmtJPanel;
    JTextArea tArea;
    JLabel lblapmt,jdtitle, jdoctorInfo, lblPatientID, lblSymptoms, lblDiagnosis, lblMedicines, lblAdvice, lblNextVisit;
    JTextField txtPatientID, txtSymptoms, txtDiagnosis, txtMedicines, txtAdvice, txtNextVisit;
    JButton btnSave, btnView, btnLogout;

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
        lblapmt.setFont(new Font("Seouge", Font.BOLD, 22));
        lblapmt.setForeground(Color.GRAY);


        // Doctor info label
        jdoctorInfo = new JLabel("Dr. Ahem Sharma | Cardiology | Available: 10 AM - 3 PM", JLabel.CENTER);
        jdoctorInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jdoctorInfo.setForeground(Color.DARK_GRAY);

        // Labels
        lblPatientID = new JLabel("Patient ID:");
        lblSymptoms = new JLabel("Symptoms:");
        lblDiagnosis = new JLabel("Diagnosis:");
        lblMedicines = new JLabel("Medicines:");
        lblAdvice = new JLabel("Advice:");
        lblNextVisit = new JLabel("Next Visit Date:");
        // patient label font setting
        lblPatientID.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSymptoms.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDiagnosis.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblMedicines.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblAdvice.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNextVisit.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        // Text fields
        txtPatientID = new JTextField();
        txtSymptoms = new JTextField();
        txtDiagnosis = new JTextField();
        txtMedicines = new JTextField();
        txtAdvice = new JTextField();
        txtNextVisit = new JTextField();

        // Buttons
        btnSave = new JButton("💾  Save Prescription");
        btnView = new JButton("📋  View Appointments");
        btnLogout = new JButton("🚪 Logout");
        //backgrund color
        btnSave.setBackground(new Color(0, 153, 76));
        btnView.setBackground(new Color(0, 102, 204));
        btnLogout.setBackground(new Color(204, 0, 0));
        // color
        btnSave.setForeground(Color.WHITE);
        btnView.setForeground(Color.WHITE);
        btnLogout.setForeground(Color.WHITE);

        // Add components to panel
        //label
        p.add(jdtitle);
        p.add(jdoctorInfo);
        p.add(lblPatientID);
        p.add(lblSymptoms);
        p.add(lblDiagnosis);
        p.add(lblMedicines);
        p.add(lblAdvice);
        p.add(lblNextVisit);
        //text field 
        p.add(txtPatientID);
        p.add(txtSymptoms);
        p.add(txtDiagnosis);
        p.add(txtMedicines);
        p.add(txtAdvice);
        p.add(txtNextVisit);
        //button
        p.add(btnSave);
        p.add(btnView);
        p.add(btnLogout);

        add(p);
        p.add(pApmtJPanel);
        pApmtJPanel.add(lblapmt);

        // setBounds (x, y, w, h)
        // Appointment panel
        pApmtJPanel.setBounds(500,150,500,600);
        //label
        lblapmt.setBounds(10,10,500,35);
        
        jdtitle.setBounds(0, 20, 1050, 40);
        jdoctorInfo.setBounds(0, 70, 1050, 30);

        // Left side for patient details
        lblPatientID.setBounds(80, 140, 120, 30);
        txtPatientID.setBounds(210, 140, 250, 30);

        lblSymptoms.setBounds(80, 190, 120, 30);
        txtSymptoms.setBounds(210, 190, 250, 30);

        lblDiagnosis.setBounds(80, 240, 120, 30);
        txtDiagnosis.setBounds(210, 240, 250, 30);

        lblMedicines.setBounds(80, 290, 120, 30);
        txtMedicines.setBounds(210, 290, 250, 30);

        lblAdvice.setBounds(80, 340, 120, 30);
        txtAdvice.setBounds(210, 340, 250, 30);

        lblNextVisit.setBounds(80, 390, 120, 30);
        txtNextVisit.setBounds(210, 390, 250, 30);

    
        // Buttons below form
        btnSave.setBounds(50, 460, 180, 35);
        btnView.setBounds(250, 460, 180, 35);
        btnLogout.setBounds(30, 720, 100, 35);
        

        //Action Listerner
        btnView.addActionListener(this);
        btnLogout.addActionListener(this);
        // Frame 
        setTitle("Doctor Dashboard");
        setSize(1050, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pApmtJPanel.setVisible(false);
    }

    public void actionPerformed(ActionEvent ae){
        if (ae.getSource()==btnView) {
            pApmtJPanel.setVisible(true);
        }
        else if (ae.getSource()==btnLogout) {
            new Login_Page();
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new D_dashboard();
    }
}