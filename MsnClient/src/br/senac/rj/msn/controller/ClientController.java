package br.senac.rj.msn.controller;

import br.senac.rj.msn.model.User;
import br.senac.rj.msn.model.Message;
import br.senac.rj.msn.view.ClientChatView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientController {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Object lock = new Object();
    private final User userClient;
    private String ip;
    private int PORT;

    public ClientController(String ip, int PORT, User userClient) {
        this.ip = ip;
        this.PORT = PORT;
        this.userClient = userClient;
    }
    public void requestConnection(ClientChatView clientView) throws IOException {
        client = new Socket(this.ip, this.PORT);
        startChat(clientView);
    }

    public void startChat(ClientChatView clientView) throws IOException {
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());

        Thread threadReceiver = new Thread(new Receiver(clientView));

        threadReceiver.start();
    }

    private class Receiver implements Runnable {
        private final ClientChatView clientView;

        public Receiver(ClientChatView clientView) {
            this.clientView = clientView;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Message message = (Message) in.readObject();
                    String textMessage = message.toString();
                    SwingUtilities.invokeLater(() -> clientView.showMessage(textMessage));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(String textMessage, LocalDateTime timestamp, ClientChatView clientView) throws IOException {
        synchronized (lock) {
            Message message = new Message(userClient, textMessage, timestamp);
            clientView.showMessage(message.toString());
            out.writeObject(message);
            out.flush();
        }
    }
}
