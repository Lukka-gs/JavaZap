package br.senac.rj.msn.view;

import br.senac.rj.msn.controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class ClientChatView extends JFrame {
    private final JTextArea chat;
    private final JTextArea inputField;
    private final JButton sendButton;
    private final ClientController controller;
    public ClientChatView(ClientController controller) {

        this.controller = controller;

        setTitle("Client side");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLayout(null);

        chat = new JTextArea();
        chat.setEditable(false);
        chat.setBackground(Color.WHITE);
        add(chat);
        JScrollPane scrollChat = new JScrollPane(chat);
        scrollChat.setBounds(45, 50, 700, 500);
        add(scrollChat);

        inputField = new JTextArea();
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        JScrollPane scrollMessage = new JScrollPane(inputField);
        scrollMessage.setBounds(35, 575, 610, 50);
        add(scrollMessage);

        sendButton = new JButton("Send");
        sendButton.setBounds(655, 575, 100, 50);
        add(sendButton);
        sendButton.addActionListener(this::actionPerformed);

        setVisible(true);
    }

    public void showMessage(String textMessage) {
        chat.append(textMessage + "\n");
    }

    public String getInputField() {
        return inputField.getText();
    }

    public void cleanField() {
        inputField.setText("");
    }

    public void actionPerformed(ActionEvent event) {
        try {
            String textMessage = inputField.getText();
            if (!textMessage.isEmpty()) {
                LocalDateTime timestamp = LocalDateTime.now();
                inputField.setText("");
                controller.sendMessage(textMessage, timestamp, this);
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Error: " + erro);
        }

    }
}
