import java.io.*;
import java.net.*;

public class Server extends Thread {

    private final Socket serverSocket;
    private ClientDetails clientDetails;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public Server(Socket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
        inputStream = new ObjectInputStream(serverSocket.getInputStream());
        outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
    }

    // Send object to server
    public boolean sendToServer(Object obj) {
        try {
            outputStream.writeObject(obj);
            return true;
        } catch (Exception e) {
            System.out.println("Error occurred sending to server");
            return false;
        }
    }

    // Send message to server
    public boolean sendMessageToServer(String input) {
        return sendToServer(new Packet(clientDetails, input.split("#")));
    }

    // Receive input from server (waiting)
    private boolean awaitingResponse() throws ClassNotFoundException {
        try {
            Object obj = inputStream.readObject();
            if (obj instanceof Packet packet) {
                if (packet.type("message")) {
                    System.out.println("Message received");
                    System.out.println(packet);
                    // For UI
                    Main.main.addMessage(packet);
                } else if (packet.type("command")) {
                    System.out.println("Command received");
                    switch (packet.getArguments()[0].toLowerCase()) {
                        case "newclient":
                            System.out.println("New client connected");
                            break;
                        case "clientdisconnect":
                            System.out.println("Client disconnected");
                            break;
                        case "clientlist":
                            System.out.println("Client list received");
                            break;
                        case "clientdetails":
                            System.out.println("Client details received");
                            this.clientDetails = packet.getClientDetails();
                            break;
                        case "clientmessage":
                            System.out.println("Client message received");
                            break;
                        default:
                            System.out.println("Unknown command received");
                            break;
                    }
                }
            } else {
                System.out.println("Received object is not a packet: " + obj);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void run() {
        try {
            while (true) {
                try {
                    if (!awaitingResponse()) {
                        return;
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e){
            System.out.println("Error occurred with Packets");
        }
    }
}
