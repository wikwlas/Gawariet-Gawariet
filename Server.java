package projektSK;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 1234;
    private static Map<String, String> userCredentials = new HashMap<>();
    private static Map<String, PrintWriter> connectedClients = new HashMap<>();
    private static Map<String, Set<String>> friendLists = new HashMap<>();

    public static void main(String[] args) {
        // Dodajmy przykładowe dane użytkowników i ich poświadczenia
        userCredentials.put("zuza", "zuza1");
        userCredentials.put("viki", "viki1");
        userCredentials.put("login", "haslo");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer wystartowal na porcie " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Obsługa klienta w osobnym wątku
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Logowanie klienta
            boolean isLoggedIn = false;
            String username = null;
            do {
                out.println("Wpisz login:");
                username = in.readLine();
                out.println("Wpisz haslo:");
                String password = in.readLine();

                if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
                    isLoggedIn = true;
                    out.println("Logowanie udane. Wpisz '!' aby zobaczyc dostepne opcje.");
                } else {
                    out.println("Nieprawidlowy login lub haslo. Sprobuj ponownie.");
                }
            } while (!isLoggedIn);

            connectedClients.put(username, out);
            displayConnectedUsers();
            friendLists.put(username, new HashSet<>());

            // Komunikacja klienta z innymi klientami
            String clientMessage;
            do {
                clientMessage = in.readLine();
                if (clientMessage != null && clientMessage.startsWith("@")) {
                    // Wysyłanie wiadomości do wybranego znajomego
                    String[] parts = clientMessage.substring(1).split(" ", 2);
                    String friend = parts[0];
                    String message = parts[1];

                    if (friendLists.get(username).contains(friend)) {
                        if (connectedClients.containsKey(friend)) {
                            connectedClients.get(friend).println(username + ": " + message);
                        } else {
                            out.println("Znajomy nie jest online.");
                        }
                    } else {
                        out.println("Nie jestescie znajomymi z " + friend);
                    }
                } else if (clientMessage != null && clientMessage.startsWith("+")) {
                    // Dodawanie znajomego
                    String friendToAdd = clientMessage.substring(1);
                    if (userCredentials.containsKey(friendToAdd)) {
                        friendLists.get(username).add(friendToAdd);
                        out.println("Dodano znajomego: " + friendToAdd);
                    } else {
                        out.println("Nie znaleziono osoby.");
                    }
                } else if (clientMessage != null && clientMessage.equalsIgnoreCase("showfriends")) {
                	Set<String> friends = friendLists.get(username);
                    if (!friends.isEmpty() ) {
                        for (String friend : friends) {
                            String status = connectedClients.containsKey(friend) ? "Online" : "Offline";
                            out.println(friend + " - " + status);
                        }
                    } else {
                        out.println("Nie masz jeszcze zadnych znajomych.");
                    }
                    out.println(); 
                } else if (clientMessage != null && clientMessage.startsWith("!")) {
                	out.println("Wpisz 'showFriends' aby wyswietlic liste znajomych.");
                	out.println("Wpisz '+' oraz imie osoby ktora chcesz dodac do znajomych.");
                	out.println("Wpisz '@' imie oraz wiadomosc aby wyslac wiadomosc do osoby.");
                	out.println("Wpisz 'exit' aby wyjsc.");
                } else {
                    // Wysyłanie wiadomości do wszystkich znajomych
                    for (String friend : friendLists.get(username)) {
                        if (connectedClients.containsKey(friend)) {
                            connectedClients.get(friend).println(username + ": " + clientMessage);
                        }
                    }
                }
            } while (!clientMessage.equals("exit"));

            // Wylogowanie klienta
            connectedClients.remove(username);
            friendLists.remove(username);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    static void displayConnectedUsers() {
        StringBuilder connectedUsers = new StringBuilder("Zalogowani uzytkownicy: ");
        for (String user : connectedClients.keySet()) {
            connectedUsers.append(user).append(", ");
        }
        System.out.println(connectedUsers.toString());
    }
}
