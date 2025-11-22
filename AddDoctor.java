import javax.swing.*;
import java.awt.event.*;

public class AddDoctor extends  JFrame  implements ActionListener {
    JPanel p1;
    JLabel lh, lname, lgender, lspecialization, lqualification, lexperience, lphone, lemail, laddress, lavailability ;
    JTextField tfname, tfgender, tfspecialization, tfqualification, tfexperience, tfphone, tfemail, tfaddress, tfavailability ;
    JButton bsave, bmenu;
    public AddDoctor(){
//initialization
        p1 = new JPanel();
        p1.setLayout(null);

        // Header Label
        lh = new JLabel("-: City Hospital :-");
        // Labels
        lname = new JLabel("Name");
        lgender = new JLabel("Gender");
        lspecialization = new JLabel("Specialization");
        lqualification = new JLabel("Qualification");
        lexperience = new JLabel("Experience");
        lphone = new JLabel("Phone");
        lemail = new JLabel("Email");
        laddress = new JLabel("Address");
        lavailability = new JLabel("Availability");

        // TextFields
        tfname = new JTextField();
        tfgender = new JTextField();
        tfspecialization = new JTextField();
        tfqualification = new JTextField();
        tfexperience = new JTextField();
        tfphone = new JTextField();
        tfemail = new JTextField();
        tfaddress = new JTextField();
        tfavailability = new JTextField();

        // Button
        bsave = new JButton("Save");
        bmenu = new JButton("Menu");
        // Adding to panel
        add(p1);
        p1.add(lh);
        p1.add(lname);
        p1.add(lgender);
        p1.add(lspecialization);
        p1.add(lqualification);
        p1.add(lexperience);
        p1.add(lphone);
        p1.add(lemail);
        p1.add(laddress);
        p1.add(lavailability);
        p1.add(tfname);
        p1.add(tfgender);
        p1.add(tfspecialization);
        p1.add(tfqualification);
        p1.add(tfexperience);
        p1.add(tfphone);
        p1.add(tfemail);
        p1.add(tfaddress);
        p1.add(tfavailability);
        p1.add(bsave);
        p1.add(bmenu);

        // Set bounds (x, y, width, height)
        setTitle("Add Doctor");
        // Header
        lh.setBounds(325, 30, 200, 40);
        // Labels - left column
        lname.setBounds(150, 100, 150, 30);
        lgender.setBounds(150, 145, 150, 30);
        lspecialization.setBounds(150, 190, 150, 30);
        lqualification.setBounds(150, 235, 150, 30);
        lexperience.setBounds(150, 280, 150, 30);
        lphone.setBounds(150, 325, 150, 30);
        lemail.setBounds(150, 370, 150, 30);
        laddress.setBounds(150, 415, 150, 30);
        lavailability.setBounds(150, 460, 150, 30);

        // TextFields - right column
        tfname.setBounds(310, 100, 400, 30);
        tfgender.setBounds(310, 145, 400, 30);
        tfspecialization.setBounds(310, 190, 400, 30);
        tfqualification.setBounds(310, 235, 400, 30);
        tfexperience.setBounds(310, 280, 400, 30);
        tfphone.setBounds(310, 325, 400, 30);
        tfemail.setBounds(310, 370, 400, 30);
        tfaddress.setBounds(310, 415, 400, 30);
        tfavailability.setBounds(310, 460, 400, 30);

        // Button - centered below form
        bsave.setBounds(375, 520, 100, 40);
        bmenu.setBounds(700, 700, 100, 40);

        bmenu.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }



    public  void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bmenu) {     // add patient page will open
            Main_Menu m = new Main_Menu();
            m.setVisible(true);
            m.setSize(850, 800);
            m.setLocationRelativeTo(null);
            this.setVisible(false);
        }
    }
    public static void main(String[] args) {
        AddDoctor obj = new AddDoctor();
        obj.setSize(850,800);
        obj.setLocationRelativeTo(null);
        obj.setVisible(true);
    }
}
