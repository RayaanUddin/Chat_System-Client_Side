import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static JFrame frameMain;
    static Server serverConnection;
    private JTextField textMessage;
    private JButton buttonSend;
    private JPanel panelMain;
    private JLabel labelMessages;
    private JPanel clientsConnected_Panel;
    private JLabel clientsConnected_Label;
    public static Main main;

    public void newMessage(String message) {
        labelMessages.setText(labelMessages.getText() + "\n" + message);
    }

    public Main() {
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverConnection.sendMessageToServer(textMessage.getText());
                textMessage.setText("");
            }
        });
    }

    public void addMessage(Object message) {
        labelMessages.setText(labelMessages.getText() + "<br>" + message.toString());
    }

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        try {
            serverConnection = new Server(new Socket("127.0.0.1", 5004));
            serverConnection.start();
            System.out.println("Connected to server");
        } catch (IOException e){
            System.out.println("Unable to connect to server");
            return;
        }


        frameMain = new JFrame("Main");
        main = new Main();
        frameMain.setContentPane(main.panelMain);
        frameMain.setSize(500,600);
        main.labelMessages.setText("<html>");


        JMenu file =  new JMenu("File");
        JMenuItem setName = new JMenuItem("Set Name");
        file.add(setName);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        frameMain.setJMenuBar(menuBar);

        frameMain.setVisible(true);
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == setName) {
                    String newName = JOptionPane.showInputDialog("What do you want to set your name too?");
                    if (newName != null) {
                        System.out.println("Setting name to: " + newName);
                    }
                }
            }
        });
    }
}