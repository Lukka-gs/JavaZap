package br.senac.rj.msn.view;

import br.senac.rj.msn.controller.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;

public class ServerChatView extends JFrame {
    private final JTextArea chat;
    private final JTextArea inputField;
    private final JButton sendButton;
    private final ServerController controller;
    public ServerChatView(ServerController controller) throws IOException {

        this.controller = controller;

        setTitle("Server side");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLayout(null);

        JLabel title = new JLabel("IP: " + controller.getIp() + "    Porta: " + controller.getPORT());
        title.setBounds(45, 20, 700, 20);
        add(title);

        chat = new JTextArea();
        chat.setEditable(false);
        chat.setBackground(Color.WHITE);
        add(chat);
        JScrollPane scrollChat = new JScrollPane(chat);
        scrollChat.setBounds(45, 50, 700, 500);
        add(scrollChat);
        chat.append("Wait for client conection\n");

        inputField = new JTextArea();
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        inputField.setEditable(false);
        inputField.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollMessage = new JScrollPane(inputField);
        scrollMessage.setBounds(35, 575, 610, 50);
        add(scrollMessage);

        sendButton = new JButton("Send");
        sendButton.setBounds(655, 575, 100, 50);
        sendButton.setEnabled(false);
        add(sendButton);
        sendButton.addActionListener(this::actionPerformed);

        setVisible(true);

        controller.waitConnection(this);
    }

    public void enableChat() {
        inputField.setEditable(true);
        inputField.setBackground(Color.WHITE);
        sendButton.setEnabled(true);
        cleanField();
    }

    public void showMessage(String textMessage) {
        chat.append(textMessage + "\n");
    }

    public String getInputField() {
        return inputField.getText();
    }

    public void cleanField() {
        chat.setText("");
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
