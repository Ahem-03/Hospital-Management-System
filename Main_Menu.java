import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main_Menu extends JFrame {
    JPanel p1;
    JLabel lh;
    JButton baddp, baddD, baptmnt, blogout;

    public Main_Menu() {
        // initializing
        p1 = new JPanel();
        p1.setLayout(null);

        ImageIcon bgIcon = new ImageIcon("image_1.png");
        Image img = bgIcon.getImage().getScaledInstance(1250, 820, Image.SCALE_SMOOTH);
        bgIcon = new ImageIcon(img);

        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setBounds(0, 0, 1250, 820);
        backgroundLabel.setLayout(null); // Allow adding components over background

        p1.setBackground(new Color(235, 245, 255));
        lh = new JLabel(" -: City Hospital :- ", JLabel.CENTER);
        baddp = new JButton(" 🧑‍🤝‍🧑 Manage patient");
        baddD = new JButton("👨‍⚕️ Manage Doctors");
        baptmnt = new JButton(" 📅 Fix appointment");
        blogout = new JButton("🚪 Logout");

        // set the font for all component
        lh.setFont(new Font("Segoe UI", Font.BOLD, 28));
        baddp.setFont(new Font("Segoe UI", Font.BOLD, 18));
        baddD.setFont(new Font("Segoe UI", Font.BOLD, 18));
        baptmnt.setFont(new Font("Segoe UI", Font.BOLD, 18));
        blogout.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // set background for the button
        baddp.setBackground(new Color(0, 153, 153));
        baddD.setBackground(new Color(51, 153, 255));
        baptmnt.setBackground(new Color(102, 102, 255));
        blogout.setBackground(new Color(220, 53, 69));
        
        // set foreground to white for better contrast
        lh.setForeground(Color.WHITE);
        baddp.setForeground(Color.WHITE);
        baddD.setForeground(Color.WHITE);
        baptmnt.setForeground(Color.WHITE);
        blogout.setForeground(Color.WHITE);
        
        // Add background first
        p1.add(backgroundLabel);
        
        // Add components to background label (not panel)
        //backgroundLabel.add(lh);
        backgroundLabel.add(baddp);
        backgroundLabel.add(baddD);
        backgroundLabel.add(baptmnt);
        backgroundLabel.add(blogout);

        add(p1);

        // set bounds (x,y,w,h)
        setTitle("Main Menu");
        lh.setBounds(0, 20, 1250, 50);
        baddp.setBounds(30, 250, 280, 60); // Add Patient
        baddD.setBounds(30, 380, 280, 60); // Add Doctor
        baptmnt.setBounds(30, 510, 280, 60); // appointment
        blogout.setBounds(30, 690, 140, 50); // Logout

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // action listener
        baddp.addActionListener(new ActionListener() {
            // add patient page will open
            public void actionPerformed(ActionEvent e) {
                ViewPatient vp = new ViewPatient();
                vp.setLocationRelativeTo(null);
                Main_Menu.this.setVisible(false);
            }
        });

        baddD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewDoctor();
                setLocationRelativeTo(null);
                Main_Menu.this.setVisible(false);
            }
        });

        baptmnt.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new FixAppointment().setVisible(true);
                Main_Menu.this.setVisible(false);
            }
        });

        blogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login_Page();
                setLocationRelativeTo(null);
                Main_Menu.this.setVisible(false);
            }
        });

        // framing
        setSize(1250, 820);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main_Menu());
    }
}