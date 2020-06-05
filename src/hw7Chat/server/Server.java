package hw7Chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Server {
    private List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();
        ServerSocket serverSocket = null;
        Socket socket;
        final int PORT = 10000;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен");

            while (true) {
                socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void broadcastMsg(String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

    public void personalMsg (String nick, String msg) {
        for (ClientHandler c : clients) {
            if (c.checkNick(nick)) {
                c.sendMsg(msg);
                return;
            }
        }

    }

    public void subscribe (ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe (ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean checkNick (String nick) {
        for (ClientHandler c: clients) {
            if (c.checkNick(nick)) {
                return true;
            }
        }
        return false;
    }
}
