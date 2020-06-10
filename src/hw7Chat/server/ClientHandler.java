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
                    //authentification/registration/
                    while (true) {
                        String str = inputStream.readUTF();

                        if (str.startsWith("/auth ")) {
                            socket.setSoTimeout(120000);
                            String[] token = str.split(" ");

                            if (token.length < 3) {
                                sendMsg("Вы не заполнили логин/пароль");
                                continue;
                            }
                            String newNick = server.getAuthService().getNickName(token[1], token[2]);
                            if (server.checkNick(newNick)) {
                                sendMsg("Пользователь " + newNick + " уже зарегестрирован в системе.");
                            } else if (newNick != null) {
                                sendMsg("/authok " + newNick);
                                socket.setSoTimeout(0);
                                nick = newNick;
                                login = token[1];
                                server.subscribe(this);
                                server.broadcastMsg("Клиент: " + nick + " подключился");
                                break;
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        } else if (str.startsWith("/reg ")) {
                            String[] token = str.split(" ");

                            if (token.length < 4) {
                                sendMsg("Вы не заполнили все поля");
                                continue;
                            }
                            boolean succeed = server.getAuthService().registration(token[1], token[2], token[3]);
                            if (succeed) {
                                sendMsg("Регистрация успешно прошла.");
                            } else {
                                sendMsg("Login или nickName заняты. \nПопробуйте снова!");
                            }
                        }
                    }

                    //chat
                    while (true) {
                        String str = inputStream.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                sendMsg("/end");
                                server.broadcastMsg(nick + " отключился");
                                break;
                            }
                            if (str.startsWith("/w ")) {
                                String[] arr = str.split(" ", 3);
                                if (server.checkNick(arr[1])) {
                                    server.personalMsg(arr[1], this, arr[2]);
                                } else {
                                    sendMsg("Клиент " + arr[1] + " не подключен");
                                }
                            }
                        } else {
                            server.broadcastMsg(nick + ": " + str);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Клиент отключился");
                    server.broadcastMsg(nick + " отключился");
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

    public String getNick() {
        return nick;
    }

}
