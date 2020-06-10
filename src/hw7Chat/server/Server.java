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

    private void sendListUsers() {
        StringBuilder sb = new StringBuilder("/clientList ");
        for (ClientHandler c : clients) {
            sb.append(c.getNick()).append(" ");
        }
        String msg = sb.toString();
        broadcastMsg(msg);
    }

    public void personalMsg (String recipient, ClientHandler client, String msg) {
        String str = String.format("[%s] %s", client.getNick(), msg);
        if (recipient.equals(client.getNick())) {
            client.sendMsg(str);
            return;
        } else {
            for (ClientHandler c : clients) {
                if (c.checkNick(recipient)) {
                    c.sendMsg(str);
                    client.sendMsg(str);
                    return;
                }
            }
        }
        client.sendMsg("пользователь " + recipient + " не найден.");
    }

    public void subscribe (ClientHandler clientHandler) {
        clients.add(clientHandler);
        sendListUsers();
    }

    public void unsubscribe (ClientHandler clientHandler) {
        clients.remove(clientHandler);
        sendListUsers();
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
