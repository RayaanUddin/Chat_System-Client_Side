import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private JTextArea textAreaMessages;
    private JTextPane textPaneConnectedClients;
    public static Main main;

    public Main() {
        frameMain.setSize(500, 600);
        frameMain.setTitle("Chat App");
        textAreaMessages.setText("");

        // Menu bar
        JMenu file =  new JMenu("File");
        JMenuItem setName = new JMenuItem("Set Name");
        file.add(setName);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        frameMain.setJMenuBar(menuBar);
        frameMain.setVisible(true);
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Action listeners

        // Send message to server
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverConnection.sendMessageToServer(textMessage.getText());
                textMessage.setText("");
            }
        });

        // Set name
        setName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == setName) {
                    String newName = JOptionPane.showInputDialog("What do you want to set your name too?");
                    if (newName != null) {
                        if (serverConnection.changeName(newName)) {
                            System.out.println("Name changed");
                        } else {
                            System.out.println("Name change failed");
                        }
                    }
                }
            }
        });

        // Send on enter
        textMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonSend.getActionListeners()[0].actionPerformed(null);
                }
            }
        });
    }

    // Add message to UI
    public void addMessage(Object message) {
        textAreaMessages.setText(textAreaMessages.getText() + message.toString() + "\n");
        System.out.println("Message added to UI");
    }

    // Set connection list
    public void setConnClientList(String clientList) {
        textPaneConnectedClients.setText("");
        appendToPane(textPaneConnectedClients, clientList);
    }

    // send html to pane
    private void appendToPane(JTextPane tp, String msg){
        HTMLDocument doc = (HTMLDocument)tp.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
        try {
            editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
            tp.setCaretPosition(doc.getLength());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Use Mac OS X menu bar
        if (System.getProperty("os.name").startsWith("Mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        // Create main frame
        frameMain = new JFrame("Main");
        main = new Main();
        frameMain.setContentPane(main.panelMain);

        // Connect to server
        try {
            serverConnection = new Server(new Socket("127.0.0.1", 5004));
            serverConnection.start();
            System.out.println("Connected to server");
        } catch (IOException e){
            System.out.println("Unable to connect to server");
        }
    }
}