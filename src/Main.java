import java.io.*;
import java.net.*;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Socket serverSocket;
        Scanner scanner = new Scanner(System.in);
        try {
            serverSocket = new Socket("127.0.0.1", 5004);
        } catch (IOException e){
            System.out.println("Unable to connect to server");
            return;
        }
        ClientToServer serverConnection = new ClientToServer(serverSocket);
        serverConnection.start();
        while (true) {
            serverConnection.sendToServer(scanner.nextLine());
        }
    }
}