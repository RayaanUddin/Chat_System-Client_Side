import java.io.*;
import java.net.*;

public class Server extends Thread {

    private final Socket serverSocket;

    public Server(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public boolean sendToServer(String msg) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(serverSocket.getOutputStream(), true);
            outputStream.println(msg);
            return true;
        } catch (Exception e) {
            System.out.println("Error occurred sending to server");
            return false;
        }
    }

    // Receive input from server (waiting)
    private boolean awaitingResponse(ObjectInputStream inputStream) throws ClassNotFoundException {
        Packet packet;
        try {
            System.out.println("Waiting for packet..");
            packet = (Packet) inputStream.readObject();
            System.out.println(packet);

            if (packet.isMessage()) {
                System.out.println("Message received");
                // For UI
                Main.main.addMessage(packet);
            }
            System.out.println("Packet received successfully");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void run() {
        ObjectInputStream inputStream = null;
        try {
            while (true) {
                try {
                    inputStream = new ObjectInputStream(serverSocket.getInputStream());
                    if (!awaitingResponse(inputStream)) {
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
