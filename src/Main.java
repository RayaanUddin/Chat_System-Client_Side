import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static JFrame frameMain;
    static ClientToServer serverConnection;
    private JTextField textMessage;
    private JButton buttonSend;
    private JPanel panelMain;
    private JLabel labelMessages;

    public void newMessage(String message) {
        labelMessages.setText(labelMessages.getText() + "\n" + message);
    }

    public Main() {
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverConnection.sendToServer(textMessage.getText());
                textMessage.setText("");
            }
        });
    }

    public static void main(String[] args) {
        Socket serverSocket;
        Scanner scanner = new Scanner(System.in);
        try {
            serverSocket = new Socket("127.0.0.1", 5004);
        } catch (IOException e){
            System.out.println("Unable to connect to server");
            return;
        }
        serverConnection = new ClientToServer(serverSocket);
        serverConnection.start();
        frameMain = new JFrame("Main");
        frameMain.setVisible(true);
        Main main = new Main();
        frameMain.setContentPane(main.panelMain);
        main.labelMessages.setText("Hello World!");
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //while (true) {
            //serverConnection.sendToServer(scanner.nextLine());
        //}
    }
}