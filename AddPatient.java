import javax.swing.*;
import java.awt.event.*;

public class AddPatient extends  JFrame implements ActionListener
{
    JPanel p1;
    JLabel lname, lage, lgender, lphone, laddress;
    JTextField tfname, tfage, tfgender, tfphone, tfaddress;
    JButton bsave, bmenu;
    public AddPatient()
    {
        //initializing
        p1 = new JPanel();
        p1.setLayout(null);
    // label
        lname = new JLabel("Name");
        lage = new JLabel("Age");
        lgender = new JLabel("Gender");
        lphone = new JLabel("Phone number");
        laddress = new JLabel("Address");
    // text field
        tfname = new JTextField();
        tfage = new JTextField();
        tfgender = new JTextField();
        tfphone = new JTextField();
        tfaddress = new JTextField();
    //button
        bsave = new JButton("save");
        bmenu = new JButton("Menu");

    //adding to the panel
        add(p1);
    // label
        p1.add(lname);
        p1.add(lage);
        p1.add(lgender);
        p1.add(lphone);
        p1.add(laddress);
    // text field
        p1.add(tfname);
        p1.add(tfage);
        p1.add(tfgender);
        p1.add(tfphone);
        p1.add(tfaddress);
    //button
        p1.add(bsave);
        p1.add(bmenu);
    // set bounds (x,y,w,h)
        setTitle("Add Patient");
        // Labels - left aligned
        lname.setBounds(200, 150, 150, 30);
        lage.setBounds(200, 200, 150, 30);
        lgender.setBounds(200, 250, 150, 30);
        lphone.setBounds(200, 300, 150, 30);
        laddress.setBounds(200, 350, 150, 30);
    // TextFields - aligned to the right of labels
        tfname.setBounds(360, 150, 300, 30);
        tfage.setBounds(360, 200, 300, 30);
        tfgender.setBounds(360, 250, 300, 30);
        tfphone.setBounds(360, 300, 300, 30);
        tfaddress.setBounds(360, 350, 300, 30);
    //button
        bsave.setBounds(375, 420, 100, 40);
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
        public static void main (String[]args)
        {
            AddPatient obj = new AddPatient();
            obj.setSize(850, 800);
            obj.setLocationRelativeTo(null);
            obj.setVisible(true);
        }

}