import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener {
    //making textfield so that user can write a msg in it
    JTextField text;
    // Making panel for text area
    static JPanel a1;
    //Box vertical make it possible to set the messages vertically one after the other
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server() {
        f.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setPreferredSize(new Dimension(400, 60));
        p1.setLayout(null);
        f.add(p1, BorderLayout.NORTH);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons\\3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons\\1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 5, 50, 50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons\\video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(230, 15, 30, 30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons\\phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(280, 15, 30, 30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons\\3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(330, 20, 10, 20);
        p1.add(morevert);

        JLabel name = new JLabel("Sugar");
        name.setBounds(100, 10, 100, 18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(100, 35, 100, 10);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        p1.add(status);

        a1 = new JPanel();
        a1.setLayout(new BoxLayout(a1, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(a1);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        f.add(scrollPane, BorderLayout.CENTER);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        f.add(textPanel, BorderLayout.SOUTH);

        text = new JTextField();
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        textPanel.add(text, BorderLayout.CENTER);

        JButton send = new JButton("Send");
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        textPanel.add(send, BorderLayout.EAST);

        // code to change the default image icon
        ImageIcon icon = new ImageIcon("D:\\Java practice\\Chatting Application\\src\\icons\\SUGAR.PNG");
        f.setIconImage(icon.getImage());
        f.setSize(400, 600);
        f.setLocation(200, 50);
        f.setTitle("Chatify");

        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
        f.setResizable(false);
    }
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
            if (out.equals("")) {
                return;
            }
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            // VerticalStrut is used to align the msg vertically one after the other
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            //writeUTF() method is used to send the message from dataOutputStream class
            dout.writeUTF(out);

            // the text box will be empty after sending a message
            text.setText("");

            SwingUtilities.invokeLater(() -> {
                //In Java, "repaint" refers to the process of updating the appearance of a component.
                // Validate and invalitade function do the same
                f.repaint();
                f.invalidate();
                f.validate();
                JScrollBar verticalScrollBar = ((JScrollPane) a1.getParent().getParent()).getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to add functionality so that when the text is send it will appear on the text area
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        // green color at the background of he text
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        // set border class used to add padding between two messages
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        // Calender class is used to add time functionality in java
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Server();
        try {
            ServerSocket skt = new ServerSocket(20);
            while (true) {
                // to accept the messages I used .accept() method
                // the incoming are stored in socket s
                Socket s = skt.accept();
                // DataInputStream is used to receive msgs
                DataInputStream din = new DataInputStream(s.getInputStream());
                //DataOutputStream class is used to send msgs
                dout = new DataOutputStream(s.getOutputStream());

                new Thread(() -> {
                    try {
                        // To run the run infinitely I used while loop so that I can accept all the messages
                        while (true) {
                            //readUTF() is used to read the received msgs from the dataInputStream class
                            // readUTF() method reads the messages in the form of String
                            String msg = din.readUTF();
                            // I am calling my buildin function formatLabel() which add the msg on the panel
                            // and add background and timer to the message
                            JPanel panel = formatLabel(msg);

                            JPanel left = new JPanel(new BorderLayout());
                            left.add(panel, BorderLayout.LINE_START);

                            SwingUtilities.invokeLater(() -> {
                                vertical.add(left);
                                vertical.add(Box.createVerticalStrut(15));
                                // again validate() function is used to refresh the frame like repaint() method
                                f.validate();
                                JScrollBar verticalScrollBar = ((JScrollPane) a1.getParent().getParent()).getVerticalScrollBar();
                                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
