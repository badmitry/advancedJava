package hw7Chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private String nick;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            Thread t1 = new Thread(() -> {
                try {
                    //authentification
                    while (true) {
                        String str = inputStream.readUTF();

                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");

                            if (token.length < 2) {
                                continue;
                            }
                            String newNick = server.getAuthService().getNickName(token[1], token[2]);
                            if (server.checkNick(newNick)) {
                                sendMsg("Пользователь " + newNick + " уже зарегестрирован в системе.");
                            } else if (newNick != null) {
                                sendMsg("/authok " + newNick);
                                nick = newNick;
                                login = token[1];
                                server.subscribe(this);
                                server.broadcastMsg("Клиент: " + nick + " подключился");
                                break;
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                    }

                    //chat
                    while (true) {
                        String str = inputStream.readUTF();
                        if (str.equals("/end")) {
                            sendMsg("/end");
                            server.broadcastMsg(nick + " отключился");
                            break;
                        }
                        if (str.startsWith("/w ")) {
                            String[] arr = str.split(" ", 3);
                            if (server.checkNick(arr[1])) {
                                server.personalMsg(arr[1], nick +" пишет приватно Вам: " + arr[2]);
                            } else {
                                sendMsg("Клиент " + arr[1] + " не подключен");
                            }
                        } else {
                            server.broadcastMsg(nick + ": " + str);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Клиент отключился");
                    server.unsubscribe(this);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t1.setDaemon(true);
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg(String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkNick(String nick) {
        if (this.nick.equals(nick)) {
            return true;
        } else {
            return false;
        }
    }
}
