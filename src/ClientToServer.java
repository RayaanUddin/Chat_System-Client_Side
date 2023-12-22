import java.awt.*;
import java.io.*;
import java.net.*;

public class ClientToServer extends Thread {

    final private boolean debug = true;
    final private Socket serverSocket;

    public ClientToServer(Socket sSocket) {
        serverSocket = sSocket;
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
            packet = (Packet) inputStream.readObject();

            if (packet.isMessage()) {
                System.out.println(packet.getClientDetails().getName() + "[" + packet.getClientDetails().getConnectionId() + "]: " + packet.getMessage() + "    " + packet.getDateTime());
            }
            if (debug) {
                System.out.println(packet.getMessage());
            }
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
                } catch (Exception e) {
                    System.out.println("Error occurred receiving from server");
                    return;
                }
                if (!awaitingResponse(inputStream)) {
                    return;
                }
            }
        } catch (Exception e){
            System.out.println("Error occurred with Packets");
        }
    }
}
