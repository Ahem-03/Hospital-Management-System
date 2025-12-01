import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FixAppointmentUI2 extends JFrame {
    // Left panel components (keeps same layout/fields as your previous left panel)
    JLabel lheader, ldoctorId, lpatientName, ldate, ltime, ldesc, lphone;
    JTextField tfDoctorId, tfPatientName, tfDate, tfTime, tfPhone;
    JTextArea taDesc;
    JButton btnSave, btnClear, btnDelete, btnExport, btnBack;

    // Table (no ArrayList) - table model is the single source of truth
    DefaultTableModel model;
    JTable table;

    // Database credentials
    final String url = "jdbc:mysql://localhost:3306/hospital_db";
    final String user = "root";
    final String user_password = "Ahem@0304";

    Connection con;
    Statement stmt;

    public FixAppointmentUI2() {
        setTitle("Fix Appointment - Frontend");
        setSize(1050, 820);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // LEFT panel (same fields/layout)
        JPanel left = new JPanel(null);
        left.setBackground(new Color(230, 240, 255));
        left.setBounds(0, 0, 420, 820);
        add(left);

        lheader = new JLabel("📅 Fix Appointment", JLabel.CENTER);
        lheader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lheader.setBounds(30, 20, 360, 40);
        left.add(lheader);

        ldoctorId = new JLabel("Appointment ID:");
        ldoctorId.setBounds(20, 80, 120, 28);
        left.add(ldoctorId);
        tfDoctorId = new JTextField(generateAppointmentId());
        tfDoctorId.setBounds(150, 80, 240, 28);
        tfDoctorId.setEditable(false);
        left.add(tfDoctorId);

        lpatientName = new JLabel("Patient Name:");
        lpatientName.setBounds(20, 130, 120, 28);
        left.add(lpatientName);
        tfPatientName = new JTextField();
        tfPatientName.setBounds(150, 130, 240, 28);
        left.add(tfPatientName);

        ldate = new JLabel("Date (YYYY-MM-DD):");
        ldate.setBounds(20, 180, 130, 28);
        left.add(ldate);
        tfDate = new JTextField();
        tfDate.setBounds(150, 180, 240, 28);
        left.add(tfDate);

        ltime = new JLabel("Time (e.g. 10:30 AM):");
        ltime.setBounds(20, 230, 130, 28);
        left.add(ltime);
        tfTime = new JTextField();
        tfTime.setBounds(150, 230, 240, 28);
        left.add(tfTime);

        lphone = new JLabel("Phone:");
        lphone.setBounds(20, 280, 120, 28);
        left.add(lphone);
        tfPhone = new JTextField();
        tfPhone.setBounds(150, 280, 240, 28);
        left.add(tfPhone);

        ldesc = new JLabel("Description:");
        ldesc.setBounds(20, 330, 120, 28);
        left.add(ldesc);
        taDesc = new JTextArea();
        // taDesc.setLineWrap(true);
        // taDesc.setWrapStyleWord(true);;
        JScrollPane descScroll = new JScrollPane(taDesc);
        descScroll.setBounds(150, 330, 240, 120);
        left.add(descScroll);

        btnSave = new JButton("Save");
        btnSave.setBackground(new Color(0, 153, 102));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBounds(40, 480, 110, 36);
        left.add(btnSave);

        btnClear = new JButton("Clear");
        btnClear.setBounds(170, 480, 110, 36);
        left.add(btnClear);

        btnDelete = new JButton("Delete Selected");
        btnDelete.setBounds(40, 530, 240, 36);
        btnDelete.setBackground(new Color(244, 67, 54));
        btnDelete.setForeground(Color.WHITE);
        left.add(btnDelete);

        btnExport = new JButton("Export CSV");
        btnExport.setBounds(40, 590, 240, 36);
        left.add(btnExport);

        btnBack = new JButton("Back");
        btnBack.setBounds(300, 720, 90, 36);
        btnBack.setBackground(new Color(52, 105, 158));
        btnBack.setForeground(Color.WHITE);
        left.add(btnBack);

        // RIGHT panel - table only 
        JPanel right = new JPanel(null);
        right.setBackground(new Color(250, 250, 250));
        right.setBounds(420, 0, 630, 820);
        add(right);

        JLabel listLabel = new JLabel("Scheduled Appointments");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        listLabel.setBounds(20, 20, 400, 30);
        right.add(listLabel);

        String[] data =  new String[]{"ID", "Patient", "Date", "Time", "Phone", "Description"};
        
        model = new DefaultTableModel(data,0);

        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBounds(20, 60, 590, 700);
        right.add(tableScroll);

        // Anonymous listeners (left panel behavior + direct model updates)
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToTable();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(FixAppointmentUI2.this, "Select a row to delete.", "Delete", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(FixAppointmentUI2.this, "Delete selected appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) model.removeRow(row);
            }
        });

        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportCsvFromModel();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try { new Main_Menu().setVisible(true); } catch (Throwable t) { /* ignore */ }
                FixAppointmentUI2.this.dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void saveToTable() {
        String id = tfDoctorId.getText().trim();
        String patient = tfPatientName.getText().trim();
        String date = tfDate.getText().trim();
        String time = tfTime.getText().trim();
        String phone = tfPhone.getText().trim();
        String desc = taDesc.getText().trim();

        if (patient.isEmpty() || date.isEmpty() || time.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields (patient, date, time, phone).", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone must be 10 digits.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //======== query for fixing the appointment=====================
        try {
            con = DriverManager.getConnection(url, user, user_password);
            con.createStatement();
            String query = "insert into appointments(doctor_id, patient_name, appointment_date, appointment_time, description, phone) values (?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, id);
            pst.setString(2, patient);
            pst.setString(3, date);
            pst.setString(4, time);
            pst.setString(4, phone);
            pst.setString(4, desc);

            JOptionPane.showMessageDialog(this, "Record Inserted..");
            
             model.addRow(new String[]{id, patient, date, time, phone, desc});
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        tfPatientName.setText("");
        tfDate.setText("");
        tfTime.setText("");
        tfPhone.setText("");
        taDesc.setText("");
    }

    //================= exportCsv  ==================\\
    private void exportCsvFromModel() {
        int rows = model.getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(this, "No appointments to export.", "Export", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("id,patient,date,time,phone,description\n");
        for (int r = 0; r < rows; r++) {
            sb.append(escapeCsv(String.valueOf(model.getValueAt(r, 0)))).append(",")
              .append(escapeCsv(String.valueOf(model.getValueAt(r, 1)))).append(",")
              .append(escapeCsv(String.valueOf(model.getValueAt(r, 2)))).append(",")
              .append(escapeCsv(String.valueOf(model.getValueAt(r, 3)))).append(",")
              .append(escapeCsv(String.valueOf(model.getValueAt(r, 4)))).append(",")
              .append(escapeCsv(String.valueOf(model.getValueAt(r, 5)))).append("\n");
        }
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(700, 400));
        JOptionPane.showMessageDialog(this, sp, "Export CSV (copy and save)", JOptionPane.INFORMATION_MESSAGE);
    }

    private String escapeCsv(String s) {
        if (s == null) return "";
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }

    private String generateAppointmentId() {
        long t = System.currentTimeMillis() % 1000000;
        return "A" + String.format("%06d", t);
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver connnected");
        } catch (Exception e) {
            // TODO: handle exception
        }
        SwingUtilities.invokeLater(() -> new FixAppointmentUI2());
    }
}

