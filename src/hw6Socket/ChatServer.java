package hw6Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
    ServerSocket server;
    Socket socket;
    DataInputStream input;
    DataOutputStream output;
    Scanner scanner;

    public ChatServer() {
        try {
            scanner = new Scanner(System.in);
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                try {
                    socket = server.accept();
                    System.out.println("Клиент подключился");
                    new Thread(() -> {
                        try {
                            while (true) {
                                input = new DataInputStream(socket.getInputStream());
                                String str = input.readUTF();
                                System.out.printf(str);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {

                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("клиент отключился");
                        }
                    }).start();
                    output = new DataOutputStream(socket.getOutputStream());
                    while (true) {
                        String str = scanner.nextLine();
                        output.writeUTF(str);
                        System.out.println("Server: " + str);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

