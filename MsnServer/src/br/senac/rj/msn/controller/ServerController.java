package br.senac.rj.msn.controller;

import br.senac.rj.msn.model.Message;
import br.senac.rj.msn.model.User;
import br.senac.rj.msn.view.ServerChatView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class ServerController {
    private ServerSocket serverSocket;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Object lock = new Object();
    private User userServer;
    private String ip;
    private final int PORT = 10000;

    public ServerController(User userServer) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server initialized at port " + PORT);
            this.userServer = userServer;
        } catch (IOException e) {
            System.err.println("Error initializing server at port " + PORT);
            e.printStackTrace();
        }
    }
    public int getPORT() {
        return PORT;
    }

    public String getIp() {
        this.ip = "Unknown";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            this.ip = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return this.ip;
    }

    public void waitConnection(ServerChatView serverView) throws IOException {
        client = serverSocket.accept();
        serverView.enableChat();
    }

    public void connectClient(ServerChatView serverView) throws IOException {
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        System.out.println("Chat initialized" + PORT);


        Thread threadReceiver = new Thread(new Receiver(serverView));

        threadReceiver.start();
    }

    private class Receiver implements Runnable {
        private final ServerChatView serverView;

        public Receiver(ServerChatView serverView) {
            this.serverView = serverView;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Message message = (Message) in.readObject();
                    String textMessage = message.toString();
                    SwingUtilities.invokeLater(() -> serverView.showMessage(textMessage));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(String textMessage, LocalDateTime timestamp, ServerChatView serverView) throws IOException {
        synchronized (lock) {
            Message message = new Message(userServer, textMessage, timestamp);
            serverView.showMessage(message.toString());
            out.writeObject(message);
            out.flush();
        }
    }
}