import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FixAppointment extends JFrame {
    // Left panel components (keeps same layout/fields as your previous left panel)
    JLabel lheader, lAppointmet, ldoctorId, lpatientName, ldate, ltime, ldesc, lphone;
    JTextField tfAppointment, tfDoctorId, tfPatientName, tfDate, tfTime, tfPhone, tfSearch;
    JTextArea taDesc;
    JButton btnSave, btnClear, btnDelete, btnExport, btnBack, btnSearch;

    // Table (no ArrayList) - table model is the single source of truth
    DefaultTableModel model;
    JTable table;

    // Database credentials
    final static String url = "jdbc:mysql://localhost:3306/hospital_db";
    final static String user = "root";
    final static String user_password = "Ahem@0304";

    static Connection con,con_1;
    static Statement stmt, stmt_1;

    public FixAppointment() {
        setTitle("Fix Appointment - Frontend");
        setSize(1250, 820);
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

        lAppointmet =  new JLabel("Appointment ID :");
        lAppointmet.setBounds(20,80,120,28);
        left.add(lAppointmet);
        tfAppointment = new JTextField();
        tfAppointment.setBounds(150, 80, 240, 28);
        tfAppointment.setEditable(false);
        left.add(tfAppointment);
        

        ldoctorId = new JLabel("Doctor Id");
        ldoctorId.setBounds(20, 130, 120, 28);
        left.add(ldoctorId);
        tfDoctorId = new JTextField();
        tfDoctorId.setBounds(150, 130, 240, 28);
        tfDoctorId.setEditable(true);
        left.add(tfDoctorId);

        lpatientName = new JLabel("Patient Name:");
        lpatientName.setBounds(20, 180, 120, 28);
        left.add(lpatientName);
        tfPatientName = new JTextField();
        tfPatientName.setBounds(150, 180, 240, 28);
        left.add(tfPatientName);

        ldate = new JLabel("Date (YYYY-MM-DD):");
        ldate.setBounds(20, 230, 130, 28);
        left.add(ldate);
        tfDate = new JTextField();
        tfDate.setBounds(150, 230, 240, 28);
        left.add(tfDate);

        ltime = new JLabel("Time (e.g. 10:30 AM):");
        ltime.setBounds(20, 280, 130, 28);
        left.add(ltime);
        tfTime = new JTextField();
        tfTime.setBounds(150, 280, 240, 28);
        left.add(tfTime);

        lphone = new JLabel("Phone:");
        lphone.setBounds(20, 330, 120, 28);
        left.add(lphone);
        tfPhone = new JTextField();
        tfPhone.setBounds(150, 330, 240, 28);
        left.add(tfPhone);

        ldesc = new JLabel("Description:");
        ldesc.setBounds(20, 380, 120, 28);
        left.add(ldesc);
        taDesc = new JTextArea();
        taDesc.setLineWrap(true);
        taDesc.setWrapStyleWord(true);;
        JScrollPane descScroll = new JScrollPane(taDesc);
        descScroll.setBounds(150, 380, 240, 120);
        left.add(descScroll);

        btnSave = new JButton("Save");
        btnSave.setBackground(new Color(0, 153, 102));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBounds(40, 530, 110, 36);
        left.add(btnSave);

        btnClear = new JButton("Clear");
        btnClear.setBounds(170, 530, 110, 36);
        left.add(btnClear);

        btnDelete = new JButton("Delete Selected");
        btnDelete.setBounds(40, 580, 240, 36);
        btnDelete.setBackground(new Color(244, 67, 54));
        btnDelete.setForeground(Color.WHITE);
        left.add(btnDelete);

        btnExport = new JButton("Export CSV");
        btnExport.setBounds(40, 630, 240, 36);
        left.add(btnExport);

        btnBack = new JButton("Back");
        btnBack.setBounds(300, 720, 90, 36);
        btnBack.setBackground(new Color(52, 105, 158));
        btnBack.setForeground(Color.WHITE);
        left.add(btnBack);

        // RIGHT panel - table only 
        JPanel right = new JPanel(null);
        right.setBackground(new Color(250, 250, 250));
        right.setBounds(420, 0, 830, 820);
        add(right);

        JLabel listLabel = new JLabel("Scheduled Appointments");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        listLabel.setBounds(20, 20, 400, 30);
        right.add(listLabel);

        tfSearch = new JTextField();
         // place search on top-right of the right panel
        tfSearch.setBounds(480, 20, 140, 28);
        right.add(tfSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(630, 20, 80, 28);
        right.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                searchData();
            }
        });

        String[] data =  new String[]{"Appointment ID", "ID", "Patient", "Date", "Time", "Description", "Phone"};
        
        model = new DefaultTableModel(data,0);

        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBounds(20, 60, 790, 700);
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
                    JOptionPane.showMessageDialog(FixAppointment.this, "Select a row to delete.", "Delete", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String appointmentID = model.getValueAt(row ,0).toString();
                int confirm = JOptionPane.showConfirmDialog(FixAppointment.this, "Delete selected appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    rowDelete(appointmentID);
                }
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
                try { new Main_Menu().setVisible(true); } 
                catch (Throwable t) { /* ignore */ }
                FixAppointment.this.dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);

        tfAppointment.setText(generateNextAppointmentId(tfAppointment));
        loadAppointmentData();
    }

    //=============Searching the by ID and NAME===============\\
        public void searchData(){
            model.setRowCount(0);
            String search = tfSearch.getText().trim();
            if (search.isEmpty()) {
            loadAppointmentData();
            return;
        }
            try {
                con = DriverManager.getConnection(url, user, user_password);
                String query = "select * from appointments where  appointment_id LIKE ? OR patient_name LIKE ?";

                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1,  search);
                pst.setString(2,  search);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    Object[] row ={
                        rs.getString("appointment_id"),
                        rs.getString("doctor_id"),
                        rs.getString("patient_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("description"),
                        rs.getString("phone")
                    };
                    model.addRow(row);
                } 
                if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No appointments found.", "Search", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }



    //============= creating a appointment id ==========
    public static  String generateNextAppointmentId(JTextField tfAppointment){
        ResultSet rs;
        String nextId = "A0102";
        String query = "select appointment_id from appointments ORDER BY appointment_id DESC LIMIT 1";
        try {
            con = DriverManager.getConnection(url, user, user_password);
        stmt = con.createStatement();
         rs = stmt.executeQuery(query);

        if(rs.next()){
            String lastId = rs.getString("appointment_id");
           if (lastId != null && lastId.length() > 1) {
                    int numericPart = Integer.parseInt(lastId.substring(1));
                    nextId = "A" + String.format("%04d", numericPart + 1);
                }
        }
        } catch (Exception e) {
            System.out.println(" patient id problem"+ e);
        }
               //return query;
        return nextId;
        }

    //============= For saving the data into the table ===================
    private void saveToTable() {
        String appointment_Id = tfAppointment.getText().trim();

         // Generate ID if empty
       if (appointment_Id.isEmpty()) {
           appointment_Id = generateNextAppointmentId(tfAppointment);
           tfAppointment.setText(appointment_Id);
       }

        String doctor_id = tfDoctorId.getText().trim();
        String patient = tfPatientName.getText().trim();
        String date = tfDate.getText().trim();
        String time = tfTime.getText().trim();
        String phone = tfPhone.getText().trim();
        String desc = taDesc.getText().trim();

        // validation
        if (doctor_id.isEmpty() || patient.isEmpty() || date.isEmpty() || time.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields (doctor id, patient, date, time, phone).", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone must be 10 digits.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //======== query for fixing the appointment=====================
        String query = "INSERT INTO appointments(appointment_id, doctor_id, patient_name, appointment_date, appointment_time, description, phone) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, user_password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, appointment_Id);
            pst.setString(2, doctor_id);
            pst.setString(3, patient);
            pst.setString(4, date);
            pst.setString(5, time);
            pst.setString(6, desc);
            pst.setString(7, phone);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Record Inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAppointmentData();
                clearForm();
                tfAppointment.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Insert failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    //============== used for loading the dta automatically=================
    public void loadAppointmentData(){

    model.setRowCount(0);
    try{
        con = DriverManager.getConnection(url, user, user_password);
        stmt = con.createStatement();
        System.out.println("database is connected....");
        String query  = "select * from appointments";
        ResultSet rs = stmt.executeQuery(query);
        

        while (rs.next()) {
            Object[] row ={
                rs.getString("appointment_id"),
                rs.getString("doctor_id"),
                rs.getString("patient_name"),
                rs.getString("appointment_date"),
                rs.getString("appointment_time"),
                rs.getString("description"),
                rs.getString("phone")
            };
            model.addRow(row);
        }
    }catch(Exception ee){
        System.out.println("problem"+ee);
    }
   }

   //=============cleaning the all fields ===============
    private void clearForm() {
        tfPatientName.setText("");
        tfDate.setText("");
        tfTime.setText("");
        tfPhone.setText("");
        taDesc.setText("");
    }

    //===========Delete the row =================
    public  void rowDelete(String appointmentID){
        try {
            con = DriverManager.getConnection(url, user, user_password);
            stmt = con.createStatement();
            String query = "DELETE FROM appointments WHERE appointment_id = '" + appointmentID + "'";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(this, "appointment cancel  successfully.", "Delete", JOptionPane.INFORMATION_MESSAGE);
            loadAppointmentData();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(this, "Error deleting appointment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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


    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver connnected");
        } catch (Exception e) {
            // TODO: handle exception
        }
        SwingUtilities.invokeLater(() -> new FixAppointment());
    }
}