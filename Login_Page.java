import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login_Page extends JFrame implements ActionListener
{
    JPanel p1;
    JLabel lh, lId, lpf;
    JTextField tfId, tfpf;
    JButton bDoctor, bAdmin;

    public Login_Page()
    {
        //initializing
        p1 = new JPanel();
        p1.setLayout(null);

        //  label
        lh  = new JLabel("-: City Hospital :-");
        lh.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lId = new JLabel("User Id: ");
        lpf = new JLabel("Password: ");
        //textfield
        tfId = new JTextField();
        tfpf = new JPasswordField();
        //button
        bDoctor = new JButton("Doctor Login");
        bAdmin  = new JButton("Admin Login");

//set bounds
//label
lh.setBounds(380, 80, 400, 40);
lId.setBounds(320, 200, 150, 30);
lpf.setBounds(320, 260, 150, 30);
//text field
tfId.setBounds(450, 200, 250, 30);
tfpf.setBounds(450, 260, 250, 30);
//button
bDoctor.setBounds(350, 350, 150, 40);
bAdmin.setBounds(550, 350, 150, 40);

//fonts
//label
lId.setFont(new Font("Arial", Font.BOLD, 18));
lpf.setFont(new Font("Arial", Font.BOLD, 18));
// Text Fields
tfId.setFont(new Font("Arial", Font.PLAIN, 16));
tfpf.setFont(new Font("Arial", Font.PLAIN, 16));
//button
bDoctor.setFont(new Font("Arial", Font.BOLD, 15));
bAdmin.setFont(new Font("Arial", Font.BOLD, 15));


//background
//button
bDoctor.setBackground(new Color(0, 153, 153));
bAdmin.setBackground(new Color(0, 153, 76));

// foreground
// Header Label
        lh.setForeground(new Color(0, 102, 102));
        // User ID Label
        lId.setForeground(new Color(0, 51, 51));
        // Password Label
        lpf.setForeground(new Color(0, 51, 51));
        // Buttons
        bDoctor.setForeground(Color.WHITE);
        bAdmin.setForeground(Color.WHITE);
        //focus painted
        bDoctor.setFocusPainted(false);
        bAdmin.setFocusPainted(false);

//adding the componenet to the panel
        add(p1);
        p1.add(lh);
        p1.add(lId);
        p1.add(lpf);
        p1.add(tfId);
        p1.add(tfpf);
        p1.add(bDoctor);
        p1.add(bAdmin);




        bDoctor.addActionListener(this);
        bAdmin.addActionListener(this);

        setTitle("Home Page");
        setSize(1050, 820);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae)
    {
        // String id = tfId.getText(); 
        // String password = tfpf.getText(); 
        
        if (ae.getSource() == bDoctor) {
            new D_dashboard();
            setVisible(true);
            this.setVisible(false);
            // if (id.isEmpty() || password.isEmpty()) {
            //     JOptionPane.showMessageDialog(this, "Please enter User ID and Password!", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            // } else {
                
            // }
        }
         else if (ae.getSource() == bAdmin) {
           new Main_Menu();
            setVisible(true);
            this.setVisible(false);
            // if (id.isEmpty() || password.isEmpty()) {
            //     JOptionPane.showMessageDialog(this, "Please enter User ID and Password!", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            // } else {
            //     // Database connection for admin
            // }
        }
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Login_Page());
    }
}