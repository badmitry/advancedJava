package hw7Chat.client;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetConnection {
    Controller controller;

    public NetConnection(Controller controller) {
        this.controller = controller;
    }

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private final String IP = "127.0.0.1";
    private final int PORT = 10000;


    private void checkConnection() {
        if (socket == null || socket.isClosed()) {
            connect();
        }
    }

    private void connect() {
        try {
            socket = new Socket(IP, PORT);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            Thread t1 = new Thread(() -> {

                try {
                    //authentification
                    while (true) {
                        String str = inputStream.readUTF();
                        if (str.startsWith("/authok ")) {
                            controller.authOk(str.split(" ")[1]);
                            break;
                        } else {
                            controller.notAuth(str);
                        }
                    }
                    //chat
                    while (true) {
                        String str = inputStream.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                break;
                            }
                            if (str.startsWith("/clientList ")) {
                                String [] arr = str.split(" ");
                                Platform.runLater(() -> {
                                    controller.writeClientList(arr);
                                });
                            }
                        } else {
                            controller.addText(str);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Клиент отключился");
                    controller.notAuth("Вы вышли из системы");
                    controller.setAuthenticated(false);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
            t1.setDaemon(true);
            t1.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            controller.notAuth("Сервер не отвечает");
        }
    }

    public void sendMsg(String str) {
        checkConnection();
        try {
            outputStream.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(String login, String password) {
        checkConnection();
        try {
            outputStream.writeUTF("/auth " + login + " " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryRegistration(String login, String password, String nick){
        String msg = String.format("/reg %s %s %s", login, password, nick);
        try {
            checkConnection();
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
