package projektSK;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            // Logowanie klienta
            String serverMessage = in.readLine();
            System.out.println(serverMessage);

            String userInput = consoleReader.readLine();
            out.println(userInput);

            serverMessage = in.readLine();
            System.out.println(serverMessage);
            
            userInput = consoleReader.readLine();
            out.println(userInput);


            // Komunikacja klienta
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            String message;
            do {
                message = consoleReader.readLine();
                out.println(message);
            } while (!message.equals("exit"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
